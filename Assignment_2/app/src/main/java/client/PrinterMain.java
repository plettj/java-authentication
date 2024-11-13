package client;

public class PrinterMain {
    public static void main(String[] args) {
        try {
            // PROTOCOL BEGINS.

            // 1. User data is already stored on the server.

            // 2. Establish a connection with the server.
            Printer localPrinter = new Printer("client_1");
            localPrinter.establishConnection();

            // Begin login and printer action process.
            localPrinter.start();

            // To TA: Modify the following invocation to test different RMIs.
            localPrinter.actAsManager("Assignment_2/app/src/test/resources/test_1.txt",
                    "Assignment_2/app/src/test/resources/printers/printer_1.txt");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
