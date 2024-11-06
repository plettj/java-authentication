package client;

import java.util.Scanner;

public class PrinterMain {

    public Login inputToLogin() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username: ");
        String username = scanner.nextLine();
        System.out.println("Enter your password: ");
        String password = scanner.nextLine();
        System.out.println("Enter your symmetric key: ");
        String symmetricKey = scanner.nextLine();
        scanner.close();

        // TODO: hash password with SHA3256
        Login login = new Login(username, password, symmetricKey);
        return login;
    }

    public static void main(String[] args) {
        try {
            // Create an instance of a printer client.
            Printer localPrinter = new Printer("client_1");

            localPrinter.establishConnection();

            localPrinter.printOnServer("Assignment_2/app/src/test/resources/test_1.txt",
                    "Assignment_2/app/src/test/resources/printers/printer_1");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
