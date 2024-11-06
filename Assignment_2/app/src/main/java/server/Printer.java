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

/**
 * Printer class simulates a server that manages print jobs.
 * It handles operations like adding print jobs, controlling the server, and
 * updating configurations.
 */
public class Printer extends UnicastRemoteObject implements PrinterInterface {

    FileWriter out;
    BufferedWriter writeFile;
    String serverInvocationFileName = "src/logs/server_invocation_records.txt";

    public Printer() throws RemoteException {
        super(); // Call the parent constructor
    }

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

    /**
     * Prints the specified file on the designated printer.
     *
     * @param filename The name of the file to print.
     * @param printer  The name of the printer to print to.
     */
    public void print(String filename, String printer) throws RemoteException {
        recordServerInvocation("print", new String[] { filename, printer });
    }

    /**
     * Lists the print queue for the specified printer on the user’s display.
     *
     * @param printer The name of the printer whose queue is to be displayed.
     * @return A list of print jobs in the format of "job number - file name".
     */
    public void queue(String printer) throws RemoteException {
        recordServerInvocation("queue", new String[] { printer });
    }

    /**
     * Moves the specified job to the top of the print queue for the given printer.
     *
     * @param printer The name of the printer whose queue will be reordered.
     * @param job     The job number to move to the top of the queue.
     */
    public void topQueue(String printer, int job) throws RemoteException {
        recordServerInvocation("topQueue", new String[] { printer, String.valueOf(job) });
    }

    /**
     * Starts the print server.
     */
    public void start() throws RemoteException {
        recordServerInvocation("start", new String[] {});
    }

    /**
     * Stops the print server.
     */
    public void stop() throws RemoteException {
        recordServerInvocation("stop", new String[] {});
    }

    /**
     * Restarts the print server, clears the print queue, and starts the server
     * again.
     */
    public void restart() throws RemoteException {
        recordServerInvocation("restart", new String[] {});
    }

    /**
     * Displays the status of the specified printer to the user.
     *
     * @param printer The name of the printer whose status is to be displayed.
     * @return The status of the printer.
     */
    public void status(String printer) throws RemoteException {
        recordServerInvocation("status", new String[] { printer });
    }

    /**
     * Reads the value of the specified configuration parameter on the print server.
     *
     * @param parameter The name of the configuration parameter.
     * @return The value of the specified parameter.
     */
    public void readConfig(String parameter) throws RemoteException {
        recordServerInvocation("readConfig", new String[] { parameter });
    }

    /**
     * Sets the specified configuration parameter to the given value on the print
     * server.
     *
     * @param parameter The name of the configuration parameter.
     * @param value     The value to set for the configuration parameter.
     */
    public void setConfig(String parameter, String value) throws RemoteException {
        recordServerInvocation("setConfig", new String[] { parameter, value });
    }

    /**
     * TEMPORARY MAIN FUNCTION for testing if our Printer runs
     * 
     * @param args
     */
    public static void main(String[] args) {
        try {
            Printer server = new Printer();
            // TODO: Make constants for relative `tests` and `printers` locations.
            server.print("Assignment_2/tests/test_1.txt", "Assignment_2/printers/printer4.txt");
        } catch (RemoteException e) {
            System.out.println("RemoteException: " + e.getMessage());
        }

        System.out.println("This code is running...");
    }
}
