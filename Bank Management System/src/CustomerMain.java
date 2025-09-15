import java.io.*;
import java.util.*;

public class CustomerMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== Customer Panel ===");
            System.out.print("Enter Account Number: ");
            int accNo = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            String[] customerData = findCustomer(accNo, password);
            if (customerData == null) {
                System.out.println("Login failed! Account not found or wrong password.");
                continue;
            }

            System.out.println("Login successful! Welcome " + customerData[0]);

            while (true) {
                System.out.println("\n1. Check Balance");
                System.out.println("2. Deposit Money");
                System.out.println("3. Withdraw Money");
                System.out.println("4. Start Chat");
                System.out.println("5. Logout");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.println("Balance: $" + customerData[2]);
                        break;
                    case 2:
                        System.out.print("Enter amount to deposit: ");
                        double deposit = scanner.nextDouble();
                        updateBalance(accNo, deposit, true);
                        System.out.println("Deposit successful.");
                        return; // logout to refresh balance
                    case 3:
                        System.out.print("Enter amount to withdraw: ");
                        double withdraw = scanner.nextDouble();
                        if (Double.parseDouble(customerData[2]) >= withdraw) {
                            updateBalance(accNo, withdraw, false);
                            System.out.println("Withdraw successful.");
                        } else {
                            System.out.println("Insufficient balance.");
                        }
                        return; // logout to refresh balance
                    case 4:
                        ChatClient.main(null);
                        break;
                    case 5:
                        System.out.println("Logging out...");
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            }
        }
    }

    public static String[] findCustomer(int accNo, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("customer.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (Integer.parseInt(parts[1]) == accNo && parts[3].equals(password)) {
                    return parts;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
        return null;
    }

    public static void updateBalance(int accNo, double amount, boolean isDeposit) {
        File inputFile = new File("customer.txt");
        File tempFile = new File("temp.txt");

        try (
                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                PrintWriter writer = new PrintWriter(new FileWriter(tempFile))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int currentAcc = Integer.parseInt(parts[1]);
                if (currentAcc == accNo) {
                    double newBalance = Double.parseDouble(parts[2]) + (isDeposit ? amount : -amount);
                    writer.println(parts[0] + "," + parts[1] + "," + newBalance + "," + parts[3]);
                } else {
                    writer.println(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error updating balance.");
        }

        inputFile.delete();
        tempFile.renameTo(inputFile);
    }
}
