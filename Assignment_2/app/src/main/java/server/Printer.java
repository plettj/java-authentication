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

import authentication.VerificationResult;

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

    public Printer() throws RemoteException {
        super();
        this.authentication = new Authentication();
        this.authentication.print();
    }

    // Authentication methods //
    public String getPublicKey() throws RemoteException {
        recordServerInvocation("getPublicKey", new String[] {});

        return this.authentication.getPublicKey().toString();
    }

    public VerificationResult login(String encryptedLoginRequest) throws RemoteException {
        recordServerInvocation("login", new String[] { encryptedLoginRequest });

        try {
            String loginRequest = this.authentication.decryptWithPrivateKey(encryptedLoginRequest);

            System.out.println("Decrypted login request: " + loginRequest);
        } catch (Exception e) {
            return new VerificationResult(false, "Login failed: Error decrypting login request.");
        }

        return new VerificationResult(true, "Login successful.");
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

    public void print(String filename, String printer) throws RemoteException {
        recordServerInvocation("print", new String[] { filename, printer });
    }

    public void queue(String printer) throws RemoteException {
        recordServerInvocation("queue", new String[] { printer });
    }

    public void topQueue(String printer, int job) throws RemoteException {
        recordServerInvocation("topQueue", new String[] { printer, String.valueOf(job) });
    }

    public void start() throws RemoteException {
        recordServerInvocation("start", new String[] {});
    }

    public void stop() throws RemoteException {
        recordServerInvocation("stop", new String[] {});
    }

    public void restart() throws RemoteException {
        recordServerInvocation("restart", new String[] {});
    }

    public void status(String printer) throws RemoteException {
        recordServerInvocation("status", new String[] { printer });
    }

    public void readConfig(String parameter) throws RemoteException {
        recordServerInvocation("readConfig", new String[] { parameter });
    }

    public void setConfig(String parameter, String value) throws RemoteException {
        recordServerInvocation("setConfig", new String[] { parameter, value });
    }

    public enum Role {
        ADMIN, // All printer actions
        MANAGER, // Only some printer actions
        USER // Very few printer actions
    }
}
