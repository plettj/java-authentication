package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.Key;
import java.util.Scanner;

import javax.crypto.KeyGenerator;

import authentication.Hashing;
import authentication.VerificationResult;
import server.PrinterInterface;

public class Printer {
    private String clientName;
    private server.PrinterInterface serverPrinter;
    private Authentication authentication;

    private String symmetricKey;

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
            String encryptedLogin = this.authentication.encryptForServer(login.toString());

            System.out.println("Sending login request to server: " + encryptedLogin);
            VerificationResult result = this.serverPrinter.login(encryptedLogin);

            if (result.isSuccess()) {
                System.out.println("Login SUCCESS");
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
            String username = scanner.nextLine();
            System.out.println("Enter your password: ");
            String password = scanner.nextLine();
            scanner.close();

            // Create our symmetric key.
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            Key key = keyGen.generateKey();
            this.symmetricKey = key.toString();

            // RESOURCE: Symmetric key encryption/decryption in Java.
            // https://gregorycernera.medium.com/encrypting-and-decrypting-a-message-using-symmetric-keys-with-java-explained-step-by-step-with-a523b67877d8

            Hashing hash = new Hashing(password);
            password = hash.getHash();
            Login login = new Login(username, password, symmetricKey);
            return login;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }

    }

    public void authenticateWithLogin(Login login) {

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
        this.serverPrinter.start();
        this.serverPrinter.print(filename, printer);
        this.serverPrinter.stop();
    }
}
