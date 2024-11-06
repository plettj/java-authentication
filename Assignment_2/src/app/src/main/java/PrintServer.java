/**
 * RUN COMMAND:
 * javac Assignment_2/src/*.java && java Assignment_2.src.PrintServer
 */

package Assignment_2.src.app.src.main.java;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
// Simple canvas example: https://github.com/plettj/javaSnake/blob/master/src/main/java/dev/plett/javasnake/SnakeView.java

/** GLOBALS */

/**
 * PrintServer class simulates a server that manages print jobs.
 * It handles operations like adding print jobs, controlling the server, and
 * updating configurations.
 */
public class PrintServer extends UnicastRemoteObject implements PrintServerInterface {
    private Map<String, Queue<PrintJob>> printerQueues;
    private Map<String, String> printerStatuses;
    private Map<String, String> configSettings;

    private Map<String, BufferedWriter> printers;

    public PrintServer() throws RemoteException {
        super();
        this.printerQueues = new HashMap<>();
        this.printerStatuses = new HashMap<>();
        this.configSettings = new HashMap<>();
    }

    FileReader in;
    FileWriter out;
    BufferedReader readFile;
    BufferedWriter writeFile;
    String textLine;

    String LOG_PRINTER_QUEUE = "Assignment_2/logs/log_printer_queue.txt";
    File log_printer_queue_file = new File(LOG_PRINTER_QUEUE);

    /**
     * Prints the specified file on the designated printer.
     *
     * @param filename The name of the file to print.
     * @param printer  The name of the printer to print to.
     */
    public void print(String filename, String printer) throws RemoteException {

        File printerFile = new File(printer);

        try {
            // Step 1. Get or create the printer file.
            if (!printerFile.exists()) {
                printerFile.createNewFile();
            }

            // Step 2. Read the file and write to the printer file.
            in = new FileReader(filename);
            readFile = new BufferedReader(in);
            out = new FileWriter(printerFile, true);
            writeFile = new BufferedWriter(out);
            while ((textLine = readFile.readLine()) != null) {
                writeFile.write(textLine);
                writeFile.newLine();
            }
            writeFile.close();
            out.close();
            readFile.close();
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("File does not exist or not found");
            System.err.println("FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Problem reading file.");
            System.err.println("IOException: " + e.getMessage());
        }
    }

    /**
     * Lists the print queue for the specified printer on the user’s display.
     *
     * @param printer The name of the printer whose queue is to be displayed.
     * @return A list of print jobs in the format of "job number - file name".
     */
    public String queue(String printer) throws RemoteException {
        try {
            out = new FileWriter(log_printer_queue_file, true);
            writeFile = new BufferedWriter(out);

            Queue printer_queue = printerQueues.get(printer);
            if (printer_queue == null) {
                System.out.println("Printer does not exist");
            } else {
                Iterator<PrintJob> it = printer_queue.iterator();
                while (it.hasNext()) {
                    PrintJob name = it.next();
                    System.out.println(name);
                }
            }
        } catch (IOException e) {

        }

        return null; // Replace with actual queue data
    }

    /**
     * Moves the specified job to the top of the print queue for the given printer.
     *
     * @param printer The name of the printer whose queue will be reordered.
     * @param job     The job number to move to the top of the queue.
     */
    public void topQueue(String printer, int job) throws RemoteException {
        // Implementation here
    }

    /**
     * Starts the print server.
     */
    public void start() throws RemoteException {
        // Implementation here
    }

    /**
     * Stops the print server.
     */
    public void stop() throws RemoteException {
        // Implementation here
    }

    /**
     * Restarts the print server, clears the print queue, and starts the server
     * again.
     */
    public void restart() throws RemoteException {
        // Implementation here
    }

    /**
     * Displays the status of the specified printer to the user.
     *
     * @param printer The name of the printer whose status is to be displayed.
     * @return The status of the printer.
     */
    public String status(String printer) throws RemoteException {
        // Implementation here
        return null; // Replace with actual status
    }

    /**
     * Reads the value of the specified configuration parameter on the print server.
     *
     * @param parameter The name of the configuration parameter.
     * @return The value of the specified parameter.
     */
    public String readConfig(String parameter) throws RemoteException {
        // Implementation here
        return null; // Replace with actual parameter value
    }

    /**
     * Sets the specified configuration parameter to the given value on the print
     * server.
     *
     * @param parameter The name of the configuration parameter.
     * @param value     The value to set for the configuration parameter.
     */
    public void setConfig(String parameter, String value) throws RemoteException {
        // Implementation here
    }

    /**
     * TEMPORARY MAIN FUNCTION for testing if our PrintServer runs
     * 
     * @param args
     */
    public static void main(String[] args) {

        try {
            PrintServer server = new PrintServer();
            // TODO: Make constants for relative `tests` and `printers` locations.
            server.print("Assignment_2/tests/test_1.txt", "Assignment_2/printers/printer4.txt");
        } catch (RemoteException e) {
            System.out.println("RemoteException: " + e.getMessage());
        }

        System.out.println("This code is running...");
    }
}
