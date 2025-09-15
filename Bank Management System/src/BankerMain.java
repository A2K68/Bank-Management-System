import java.io.*;
import java.util.*;

public class BankerMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== Banker Panel ===");
            System.out.println("1. Add Customer");
            System.out.println("2. View All Accounts");
            System.out.println("3. Remove Customer");
            System.out.println("4. Update Password");
            System.out.println("5. Start Chat");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addCustomer(scanner);
                    break;
                case 2:
                    viewAllAccounts();
                    break;
                case 3:
                    removeCustomer(scanner);
                    break;
                case 4:
                    updatePassword(scanner);
                    break;
                case 5:
                    ChatServer.startChat();
                    break;
                case 6:
                    System.out.println("Exiting Banker Panel.");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public static void addCustomer(Scanner scanner) {
        System.out.print("Enter Customer Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Account Number: ");
        int accNo = scanner.nextInt();
        System.out.print("Enter Initial Balance: ");
        double balance = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        System.out.print("Set a Password: ");
        String password = scanner.nextLine();

        try (FileWriter fw = new FileWriter("customer.txt", true)) {
            fw.write(name + "," + accNo + "," + balance + "," + password + "\n");
            System.out.println("Customer added successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }

    public static void viewAllAccounts() {
        try (Scanner fileScanner = new Scanner(new File("customer.txt"))) {
            System.out.println("\n--- All Customers ---");
            while (fileScanner.hasNextLine()) {
                String[] parts = fileScanner.nextLine().split(",");
                System.out.println("Name: " + parts[0]);
                System.out.println("Account No: " + parts[1]);
                System.out.println("Balance: $" + parts[2]);
                System.out.println("-------------------------");
            }
        } catch (Exception e) {
            System.out.println("No customer records found.");
        }
    }

    public static void removeCustomer(Scanner scanner) {
        System.out.print("Enter Account Number to remove: ");
        int accNo = scanner.nextInt();
        scanner.nextLine();

        File inputFile = new File("customer.txt");
        File tempFile = new File("temp.txt");

        boolean found = false;

        try (
                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                PrintWriter writer = new PrintWriter(new FileWriter(tempFile))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (Integer.parseInt(parts[1]) == accNo) {
                    found = true;
                    continue; // skip writing this line
                }
                writer.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error processing file.");
        }

        inputFile.delete();
        tempFile.renameTo(inputFile);

        if (found) {
            System.out.println("Customer removed successfully.");
        } else {
            System.out.println("Account not found.");
        }
    }

    public static void updatePassword(Scanner scanner) {
        System.out.print("Enter Account Number to update password: ");
        int accNo = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter New Password: ");
        String newPassword = scanner.nextLine();

        File inputFile = new File("customer.txt");
        File tempFile = new File("temp.txt");

        boolean found = false;

        try (
                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                PrintWriter writer = new PrintWriter(new FileWriter(tempFile))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (Integer.parseInt(parts[1]) == accNo) {
                    found = true;
                    writer.println(parts[0] + "," + parts[1] + "," + parts[2] + "," + newPassword);
                } else {
                    writer.println(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error processing file.");
        }

        inputFile.delete();
        tempFile.renameTo(inputFile);

        if (found) {
            System.out.println("Password updated successfully.");
        } else {
            System.out.println("Account not found.");
        }
    }
}
