package Assignment_2.src.app.src.main.java;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class PrintServerMain {
    public static void main(String[] args) {
        try {
            // Start the RMI registry on port 1099
            Registry registry = LocateRegistry.createRegistry(1099);

            // Create an instance of the PrintServer
            PrintServerInterface printServer = new PrintServer();

            // Bind the PrintServer instance to the registry with the name "PrintServer"
            registry.rebind("PrintServer", printServer);

            System.out.println("Print server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
