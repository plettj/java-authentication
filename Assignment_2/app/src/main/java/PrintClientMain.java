package Assignment_2.app.src.main.java;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class PrintClientMain {
    public static void main(String[] args) {
        try {
            // Connect to the registry running on localhost and port 1099
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            // Lookup the "PrintServer" in the registry
            PrintServerInterface printServer = (PrintServerInterface) registry.lookup("PrintServer");

            // Call remote methods on the server
            printServer.start();
            printServer.print("Assignment_2/app/src/test/resources/test_1.txt",
                    "Assignment_2/app/src/test/resources/printers/printer_1");
            System.out.println(printServer.queue("printer_1"));
            printServer.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
