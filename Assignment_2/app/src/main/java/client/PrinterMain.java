package client;

public class PrinterMain {
    public static void main(String[] args) {
        try {
            // Create an instance of a printer client.
            Printer localPrinter = new Printer("client_1");

            localPrinter.establishConnection();
            
            // ask user to login
            Login login = localPrinter.createLoginRequest();
            login.print();

            // authenticate with login ...
            
            localPrinter.printOnServer("Assignment_2/app/src/test/resources/test_1.txt",
                    "Assignment_2/app/src/test/resources/printers/printer_1");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
