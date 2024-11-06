package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import server.PrinterInterface;

public class PrinterMain {
    public static void main(String[] args) {
        try {
            // Connect to the registry running on localhost and port 1099
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            // Lookup the "PrintServer" in the registry
            PrinterInterface printerInterface = (PrinterInterface) registry.lookup("PrintServer");

            // Call remote methods on the server
            printerInterface.start();
            printerInterface.print("Assignment_2/app/src/test/resources/test_1.txt",
                    "Assignment_2/app/src/test/resources/printers/printer_1");
            printerInterface.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
