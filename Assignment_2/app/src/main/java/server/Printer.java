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
        
        rolePermissions.put("print", new Role[] {Role.MANAGER, Role.USER});
        rolePermissions.put("queue", new Role[] {Role.MANAGER, Role.USER});
        rolePermissions.put("topQueue", new Role[] {Role.MANAGER});
        rolePermissions.put("start", new Role[] {Role.MANAGER, Role.SERVICE, });
        rolePermissions.put("stop", new Role[] {Role.MANAGER, Role.SERVICE, });
        rolePermissions.put("restart", new Role[] {Role.MANAGER, Role.SERVICE, });
        rolePermissions.put("status", new Role[] {Role.MANAGER, Role.SERVICE, });
        rolePermissions.put("readConfig", new Role[] {Role.MANAGER, Role.SERVICE, });
        rolePermissions.put("setConfig", new Role[] {Role.MANAGER, Role.SERVICE, });

        recordServerInvocation("----------", new String[] {"Server Instantiated"});
    }

    // Authentication methods //
    public PublicKey getPublicKey() throws RemoteException {
        recordServerInvocation("getPublicKey", new String[] {});

        return this.authentication.getPublicKey();
    }

    public VerificationResult login(byte[] encryptedLoginRequest) throws RemoteException {
        recordServerInvocation("login", new String[] { encryptedLoginRequest.toString() });
        System.out.println("login called " + encryptedLoginRequest + " " + encryptedLoginRequest.toString());
        try {
            String roleSessionToken = this.authentication.authenticate(encryptedLoginRequest);
            int i = roleSessionToken.indexOf(" ");
            String role = roleSessionToken.substring(0, i);
            String sessionToken = roleSessionToken.substring(i + 1);
            VerificationResult result = new VerificationResult(role != "-", role != "-" ? "Login successful." : "Login failed", sessionToken);
            // String loginRequest = this.authentication.decryptWithPrivateKey(encryptedLoginRequest);

            System.out.println("Login request result: " + role);
            System.out.println("result: " + result.isSuccess());
            return result;
        } catch (Exception e) {
            return new VerificationResult(false, "Login failed: Error decrypting login request.", null);
        }
    }

    public boolean validateRequest(Session session, String function) {
        // Find if session is active

        // Get the role

        // Check the role against the function name

        return false;
    }

    // Printer methods //
    private void recordServerInvocation(String function, String[] parameters) throws RemoteException {
        File file = new File(serverInvocationFileName);
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

            writeFile.write(invocation);
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
        recordServerInvocation(allowed ? function : function + "-INVALID", new String[] { filename, printer });    
        
    }

    public void queue(Session session, String printer) throws RemoteException {
        String function = "queue";
        boolean allowed = this.validateRequest(session, function);
        recordServerInvocation(allowed ? function : function + "-INVALID", new String[] { printer });
    }

    public void topQueue(Session session, String printer, int job) throws RemoteException {
        String function = "topQueue";
        boolean allowed = this.validateRequest(session, function);
        recordServerInvocation(allowed ? function : function + "-INVALID", new String[] { printer, String.valueOf(job) });
    }

    public void start(Session session) throws RemoteException {
        String function = "start";
        boolean allowed = this.validateRequest(session, function);
        recordServerInvocation(allowed ? function : function + "-INVALID", new String[] {});
    }

    public void stop(Session session) throws RemoteException {
        String function = "stop";
        boolean allowed = this.validateRequest(session, function);
        recordServerInvocation(allowed ? function : function + "-INVALID", new String[] {});
    }

    public void restart(Session session) throws RemoteException {
        String function = "restart";
        boolean allowed = this.validateRequest(session, function);
        recordServerInvocation(allowed ? function : function + "-INVALID", new String[] {});
    }

    public void status(Session session, String printer) throws RemoteException {
        String function = "status";
        boolean allowed = this.validateRequest(session, function);
        recordServerInvocation(allowed ? function : function + "-INVALID", new String[] { printer });
    }

    public void readConfig(Session session, String parameter) throws RemoteException {
        String function = "readConfig";
        boolean allowed = this.validateRequest(session, function);
        recordServerInvocation(allowed ? function : function + "-INVALID", new String[] { parameter });
    }

    public void setConfig(Session session, String parameter, String value) throws RemoteException {
        String function = "setConfig";
        boolean allowed = this.validateRequest(session, function);
        recordServerInvocation(allowed ? function : function + "-INVALID", new String[] { parameter, value });
    }

    public enum Role {
        MANAGER, // All printer actions
        USER, // Very few printer actions
        SERVICE // Some printer actions
    }
}
