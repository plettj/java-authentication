package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.Key;
import java.util.Scanner;

import javax.crypto.KeyGenerator;

import authentication.Hashing;
import authentication.Session;
import authentication.VerificationResult;
import server.PrinterInterface;

public class Printer {
    private String clientName;
    private server.PrinterInterface serverPrinter;
    private Authentication authentication;

    private String symmetricKey;

    private String username;
    private String sessionToken;

    Printer(String clientName) {
        this.clientName = clientName;
        this.authentication = new Authentication();
        System.out.println("Printer created for client: " + this.clientName);
    }

    public void start() {
        // 3. Get info for a login request, and send it over encrypted.
        Login login = this.createLoginRequest();

        if (login == null) {
            System.out.println("Error creating login request.");
            return;
        }

        login.print();

        try {
            byte[] encryptedLogin = this.authentication.encryptForServer(login.toString());
            VerificationResult result = this.serverPrinter.login(encryptedLogin);

            if (result.isSuccess()) {
                System.out.println("Login SUCCESS - " + result.getMessage() + " - " + result.getSessionToken());
                this.sessionToken = result.getSessionToken();
            } else {
                System.out.println("Login FAIL");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public Login createLoginRequest() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your username: ");
            this.username = scanner.nextLine();

            String passwordOrToken = this.sessionToken;

            if (passwordOrToken == null) {
                System.out.println("Enter your password: ");
                passwordOrToken = scanner.nextLine();
            }

            scanner.close();

            // Create our symmetric key.
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            Key key = keyGen.generateKey();
            this.symmetricKey = key.toString();

            // RESOURCE: Symmetric key encryption/decryption in Java.
            // https://gregorycernera.medium.com/encrypting-and-decrypting-a-message-using-symmetric-keys-with-java-explained-step-by-step-with-a523b67877d8

            Hashing hash = new Hashing(passwordOrToken);
            passwordOrToken = hash.getHash();
            Login login = new Login(this.username, passwordOrToken, symmetricKey);
            return login;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }

    }

    public void establishConnection() throws Exception {
        // Connect to the registry running on localhost and port 1099
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);

        // Lookup the printer server in the registry
        this.serverPrinter = (PrinterInterface) registry.lookup("ServerPrinter");

        System.out.println("Connection established with printer server.");

        // Retreive the server's public key
        this.authentication.setServerKey(this.serverPrinter.getPublicKey());
    }

    public void printOnServer(String filename, String printer) throws Exception {
        this.serverPrinter.print(new Session(this.username, this.sessionToken), filename, printer);
    }

    public void actAsUser(String filename, String printer) throws Exception {
        Session session = new Session(this.username, this.sessionToken);
        this.serverPrinter.print(session, filename, printer);
        this.serverPrinter.queue(session, printer);
    }
    public void actAsManager(String filename, String printer) throws Exception {
        Session session = new Session(this.username, this.sessionToken);
        this.serverPrinter.print(session, filename, printer);
        this.serverPrinter.queue(session, printer);
        this.serverPrinter.topQueue(session, printer, 0);
        this.serverPrinter.start(session);
    }

    public void actAsService(String filename, String printer) throws Exception {
        Session session = new Session(this.username, this.sessionToken);
        this.serverPrinter.start(session);
        this.serverPrinter.status(session, printer);
        this.serverPrinter.stop(session);
    }
}
