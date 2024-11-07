package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import authentication.Hashing;
import server.PrinterInterface;

public class Printer {
    private String clientName;
    private server.PrinterInterface serverPrinter;
    private Authentication authentication;

    Printer(String clientName) {
        this.clientName = clientName;
        this.authentication = new Authentication();
        System.out.println("Printer created for client: " + this.clientName);
    }

    public Login createLoginRequest() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your username: ");
            String username = scanner.nextLine();
            System.out.println("Enter your password: ");
            String password = scanner.nextLine();
            System.out.println("Enter your symmetric key: ");
            String symmetricKey = scanner.nextLine();
            scanner.close();
    
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

        System.out.println("Connection established with printer server");
    }

    public void printOnServer(String filename, String printer) throws Exception {
        this.serverPrinter.start();
        this.serverPrinter.print(filename, printer);
        this.serverPrinter.stop();
    }
}
