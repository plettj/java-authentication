package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

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
