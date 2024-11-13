/**
 * RUN COMMAND:
 * javac Assignment_2/app/src/main/java/*.java && java Assignment_2.src.Printer
 */

package server;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.PublicKey;
import java.util.HashMap;

import authentication.VerificationResult;
import authentication.Session;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Printer class simulates a server that manages print jobs.
 * It handles operations like adding print jobs, controlling the server, and
 * updating configurations.
 */
public class Printer extends UnicastRemoteObject implements PrinterInterface {

    private Authentication authentication;

    FileWriter out;
    BufferedWriter writeFile;
    String serverInvocationFileName = "src/logs/server_invocation_records.txt";
    HashMap<String, Role[]> rolePermissions = new HashMap<String, Role[]>();

    public Printer() throws RemoteException {
        super();
        this.authentication = new Authentication();
        this.authentication.print();

        rolePermissions.put("print", new Role[] { Role.MANAGER, Role.USER });
        rolePermissions.put("queue", new Role[] { Role.MANAGER, Role.USER });
        rolePermissions.put("topQueue", new Role[] { Role.MANAGER });
        rolePermissions.put("start", new Role[] { Role.MANAGER, Role.SERVICE, });
        rolePermissions.put("stop", new Role[] { Role.MANAGER, Role.SERVICE, });
        rolePermissions.put("restart", new Role[] { Role.MANAGER, Role.SERVICE, });
        rolePermissions.put("status", new Role[] { Role.MANAGER, Role.SERVICE, });
        rolePermissions.put("readConfig", new Role[] { Role.MANAGER, Role.SERVICE, });
        rolePermissions.put("setConfig", new Role[] { Role.MANAGER, Role.SERVICE, });

        long now = Instant.now().getEpochSecond();
        String nowString = String.valueOf(now);
        printToLog("");
        printToLog("----------NEW SERVER INSTANTIATED AT EPOCH " + nowString + "----------");
    }

    // Authentication methods //
    public PublicKey getPublicKey() throws RemoteException {
        recordServerInvocation("getPublicKey", new String[] {}, true);

        return this.authentication.getPublicKey();
    }

    public VerificationResult login(byte[] encryptedLoginRequest) throws RemoteException {
        recordServerInvocation("login", new String[] { encryptedLoginRequest.toString() }, true);
        try {
            String roleSessionToken = this.authentication.authenticate(encryptedLoginRequest);
            int i = roleSessionToken.indexOf(" ");
            String role = roleSessionToken.substring(0, i);
            boolean validRole = !role.equals("INVALID");
            String sessionToken = roleSessionToken.substring(i + 1);
            VerificationResult result = new VerificationResult(validRole,
                    validRole ? "Login successful." : "Login failed", sessionToken);
            // String loginRequest =
            // this.authentication.decryptWithPrivateKey(encryptedLoginRequest);
            return result;
        } catch (Exception e) {
            return new VerificationResult(false, "Login failed: Error decrypting login request.", null);
        }
    }

    public boolean validateRequest(Session session, String function) {
        // Find if session is active
        String username = session.getUsername();
        String sessionToken = session.getSessionToken();
        boolean valid = this.authentication.validateSession(username, sessionToken);
        // Get the role
        Role role = this.authentication.getRoleByUsername(username);
        // Check the role against the function name
        Role[] allowedRoles = this.rolePermissions.get(function);
        for (Role allowedRole : allowedRoles) {
            if (role == allowedRole) {
                return valid;
            }
        }
        return valid;
    }

    // Printer methods //
    private void recordServerInvocation(String function, String[] parameters, boolean valid) throws RemoteException {
        if (valid) {
            String invocation = "Invocation called: " + function + "(";
            int i = 0;
            while (i < parameters.length) {
                invocation = invocation.concat(parameters[i]);
                if (i < parameters.length - 1) {
                    invocation = invocation.concat(", ");
                }
                i = i + 1;
            }
            invocation = invocation.concat(")");
            printToLog(invocation);
        } else {
            printToLog("ERROR: " + function + " called with invalid permission.");
        }
    }

    private void printToLog(String message) {
        File file = new File(serverInvocationFileName);
        try {
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            if (!file.exists()) {
                file.createNewFile();
            }
            out = new FileWriter(file, true);
            writeFile = new BufferedWriter(out);

            writeFile.write(message);
            writeFile.newLine();

            writeFile.close();
            out.close();
        } catch (IOException e) {
            System.out.println("Problem reading file.");
            System.err.println("IOException: " + e.getMessage());
        }
    }

    public void print(Session session, String filename, String printer) throws RemoteException {
        String function = "print";
        boolean allowed = this.validateRequest(session, function);
        recordServerInvocation(function, new String[] { filename, printer }, allowed);

    }

    public void queue(Session session, String printer) throws RemoteException {
        String function = "queue";
        boolean allowed = this.validateRequest(session, function);
        recordServerInvocation(function, new String[] { printer }, allowed);
    }

    public void topQueue(Session session, String printer, int job) throws RemoteException {
        String function = "topQueue";
        boolean allowed = this.validateRequest(session, function);
        recordServerInvocation(function, new String[] { printer, String.valueOf(job) }, allowed);
    }

    public void start(Session session) throws RemoteException {
        String function = "start";
        boolean allowed = this.validateRequest(session, function);
        recordServerInvocation(function, new String[] {}, allowed);
    }

    public void stop(Session session) throws RemoteException {
        String function = "stop";
        boolean allowed = this.validateRequest(session, function);
        recordServerInvocation(function, new String[] {}, allowed);
    }

    public void restart(Session session) throws RemoteException {
        String function = "restart";
        boolean allowed = this.validateRequest(session, function);
        recordServerInvocation(function, new String[] {}, allowed);
    }

    public void status(Session session, String printer) throws RemoteException {
        String function = "status";
        boolean allowed = this.validateRequest(session, function);
        recordServerInvocation(function, new String[] { printer }, allowed);
    }

    public void readConfig(Session session, String parameter) throws RemoteException {
        String function = "readConfig";
        boolean allowed = this.validateRequest(session, function);
        recordServerInvocation(function, new String[] { parameter }, allowed);
    }

    public void setConfig(Session session, String parameter, String value) throws RemoteException {
        String function = "setConfig";
        boolean allowed = this.validateRequest(session, function);
        recordServerInvocation(function, new String[] { parameter, value }, allowed);
    }

    public enum Role {
        MANAGER, // All printer actions
        USER, // Very few printer actions
        SERVICE // Some printer actions
    }
}
