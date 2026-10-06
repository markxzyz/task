package Banking;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GCashCLIApp {

    enum Role {
        ADMIN, STANDARD
    }

    
    static class Account {
        String email;
        String password;
        double balance;
        Role role;
        List<String> transactions;

        public Account(String email, String password, double initialBalance, Role role) {
            this.email = email;
            this.password = password;
            this.balance = initialBalance;
            this.role = role;
            this.transactions = new ArrayList<>();
            this.transactions.add(LocalDate.now() + " | Account Created | +PHP " + String.format("%.2f", initialBalance));
        }
    }

    
    private static final Map<String, Account> userDatabase = new HashMap<>();
    private static Account currentUser = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        
        userDatabase.put("admin@gcash.com", new Account("admin@gcash.com", "AdminPass123", 0.0, Role.ADMIN));
        userDatabase.put("user@gcash.com", new Account("user@gcash.com", "Password123", 1500.50, Role.STANDARD));

        boolean appRunning = true;

        while (appRunning) {
            System.out.println("*****************************************");
            System.out.println("                  WELCOME                ");
            System.out.println("*****************************************");

            
            boolean isLoggedIn = false;
            while (!isLoggedIn) {
                System.out.println("\n1. Log In");
                System.out.println("2. Create New Account ");
                System.out.println("3. Exit Application");
                System.out.println("-----------------------------------------");
                System.out.print("Select an option (1-3): ");
                String entryChoice = scanner.nextLine().trim();

                switch (entryChoice) {
                    case "1":
                        isLoggedIn = handleLogin(scanner);
                        break;
                    case "2":
                        handleRegister(scanner);
                        promptReturnToMenu(scanner, "entry");
                        break;
                    case "3":
                        System.out.println("\nThank you for using GCash CLI. Goodbye!");
                        scanner.close();
                        return;
                    default:
                        System.out.println("\n[ERROR] Invalid choice. Please select 1-3.");
                }
            }

            
            boolean userSessionActive = true;
            while (userSessionActive) {
                if (currentUser.role == Role.ADMIN) {
                    printAdminMenu();
                    System.out.print("Select an option (1-5): ");
                    String choice = scanner.nextLine().trim();

                    switch (choice) {
                        case "1":
                            viewAllAccounts();
                            promptReturnToMenu(scanner, "main");
                            break;
                        case "2":
                            viewSystemSummary();
                            promptReturnToMenu(scanner, "main");
                            break;
                        case "3":
                            searchUserAccount(scanner);
                            promptReturnToMenu(scanner, "main");
                            break;
                        case "4":
                            System.out.println("\n[LOGOUT] You have logged out successfully.");
                            currentUser = null;
                            userSessionActive = false;
                            break;
                        case "5":
                            System.out.println("\nThank you for using GCash CLI. Goodbye!");
                            userSessionActive = false;
                            appRunning = false;
                            break;
                        default:
                            System.out.println("\n[ERROR] Invalid choice. Please select 1-5.");
                    }
                } else {
                    printStandardMenu();
                    System.out.print("Select an option (1-6): ");
                    String choice = scanner.nextLine().trim();

                    switch (choice) {
                        case "1":
                            checkBalance();
                            promptReturnToMenu(scanner, "main");
                            break;
                        case "2":
                            cashIn(scanner);
                            promptReturnToMenu(scanner, "main");
                            break;
                        case "3":
                            cashTransfer(scanner);
                            promptReturnToMenu(scanner, "main");
                            break;
                        case "4":
                            viewTransactions();
                            promptReturnToMenu(scanner, "main");
                            break;
                        case "5":
                            System.out.println("\n[LOGOUT] You have logged out successfully.");
                            currentUser = null;
                            userSessionActive = false;
                            break;
                        case "6":
                            System.out.println("\nThank you for using GCash CLI. Goodbye!");
                            userSessionActive = false;
                            appRunning = false;
                            break;
                        default:
                            System.out.println("\n[ERROR] Invalid choice. Please select 1-6.");
                    }
                }
            }
        }
        scanner.close();
    }

    
    private static void promptReturnToMenu(Scanner scanner, String menuType) {
        System.out.println("\nPress Enter to return to the " + ("entry".equals(menuType) ? "Welcome" : "Main") + " Menu...");
        scanner.nextLine();
    }

    
    private static void handleRegister(Scanner scanner) {
        System.out.println("\n--- CREATE NEW ACCOUNT ---");
        System.out.print("Enter New Email: ");
        String email = scanner.nextLine().trim();

        if (email.isEmpty() || !email.contains("@")) {
            System.out.println("[ERROR] Invalid email format.");
            return;
        }

        if (userDatabase.containsKey(email)) {
            System.out.println("[ERROR] Account already exists with this email. Please log in.");
            return;
        }

        System.out.print("Enter Password (min 6 chars): ");
        String password = scanner.nextLine().trim();
        if (password.length() < 6) {
            System.out.println("[ERROR] Password must be at least 6 characters long.");
            return;
        }

        System.out.print("Enter Initial Deposit Amount: PHP ");
        try {
            double initialBalance = Double.parseDouble(scanner.nextLine().trim());
            if (initialBalance < 0) {
                System.out.println("[ERROR] Initial deposit cannot be negative.");
                return;
            }

            Account newAccount = new Account(email, password, initialBalance, Role.STANDARD);
            userDatabase.put(email, newAccount);
            System.out.println("\n[SUCCESS] Standard User account created successfully!");
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Invalid numeric input for deposit.");
        }
    }

    // --- Authentication ---
    private static boolean handleLogin(Scanner scanner) {
        System.out.println("\n--- LOG IN ---");
        System.out.print("Enter Email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine().trim();

        Account account = userDatabase.get(email);

        if (account != null && account.password.equals(password)) {
            currentUser = account;
            System.out.println("\n[SUCCESS] Login successful! Welcome, " + currentUser.email + " (" + currentUser.role + ")");
            return true;
        } else {
            System.out.println("\n[FAIL] Invalid email or password. Please try again.");
            return false;
        }
    }

    // --- Menu Displays ---
    private static void printStandardMenu() {
        System.out.println("\n-----------------------------------------");
        System.out.println("            STANDARD USER DASHBOARD      ");
        System.out.println("-----------------------------------------");
        System.out.println("1. Check Balance");
        System.out.println("2. Cash In");
        System.out.println("3. Cash Transfer");
        System.out.println("4. Transaction History");
        System.out.println("5. Log Out");
        System.out.println("6. Exit Application");
        System.out.println("-----------------------------------------");
    }

    private static void printAdminMenu() {
        System.out.println("\n-----------------------------------------");
        System.out.println("             ADMIN DASHBOARD             ");
        System.out.println("-----------------------------------------");
        System.out.println("1. View All User Accounts");
        System.out.println("2. View System Summary & Total Holdings");
        System.out.println("3. Search User Details & History");
        System.out.println("4. Log Out");
        System.out.println("5. Exit Application");
        System.out.println("-----------------------------------------");
    }

    // --- Standard Features ---
    private static void checkBalance() {
        System.out.println("\n--- CHECK BALANCE ---");
        System.out.printf("Current Available Balance: PHP %.2f%n", currentUser.balance);
    }

    private static void cashIn(Scanner scanner) {
        System.out.println("\n--- CASH IN ---");
        System.out.print("Enter amount to Cash In: PHP ");
        try {
            double amount = Double.parseDouble(scanner.nextLine().trim());
            if (amount <= 0) {
                System.out.println("[ERROR] Amount must be greater than zero.");
                return;
            }
            currentUser.balance += amount;
            currentUser.transactions.add(LocalDate.now() + " | Cash In          | +PHP " + String.format("%.2f", amount));
            System.out.printf("[SUCCESS] Added PHP %.2f. New Balance: PHP %.2f%n", amount, currentUser.balance);
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Invalid numeric input.");
        }
    }

    private static void cashTransfer(Scanner scanner) {
        System.out.println("\n--- CASH TRANSFER ---");
        System.out.print("Enter Recipient Email: ");
        String recipientInput = scanner.nextLine().trim();

        if (recipientInput.isEmpty()) {
            System.out.println("[ERROR] Recipient cannot be empty.");
            return;
        }

        if (recipientInput.equalsIgnoreCase(currentUser.email)) {
            System.out.println("[ERROR] You cannot transfer funds to your own account.");
            return;
        }

        System.out.print("Enter Transfer Amount: PHP ");
        try {
            double amount = Double.parseDouble(scanner.nextLine().trim());
            if (amount <= 0) {
                System.out.println("[ERROR] Amount must be greater than zero.");
                return;
            }

            if (amount > currentUser.balance) {
                System.out.printf("[ERROR] Insufficient funds! Current balance: PHP %.2f%n", currentUser.balance);
                return;
            }

            currentUser.balance -= amount;
            currentUser.transactions.add(LocalDate.now() + " | Transfer to " + recipientInput + " | -PHP " + String.format("%.2f", amount));

            Account recipientAccount = userDatabase.get(recipientInput);
            if (recipientAccount != null) {
                recipientAccount.balance += amount;
                recipientAccount.transactions.add(LocalDate.now() + " | Transfer from " + currentUser.email + " | +PHP " + String.format("%.2f", amount));
            }

            System.out.printf("\n[SUCCESS] Transferred PHP %.2f to %s.%n", amount, recipientInput);
            System.out.printf("Remaining Balance: PHP %.2f%n", currentUser.balance);

        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Invalid numeric input.");
        }
    }

    private static void viewTransactions() {
        System.out.println("\n--- TRANSACTION HISTORY ---");
        if (currentUser.transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (String txn : currentUser.transactions) {
                System.out.println(" • " + txn);
            }
        }
    }

    // --- Admin Features ---
    private static void viewAllAccounts() {
        System.out.println("\n--- ALL REGISTERED ACCOUNTS ---");
        System.out.printf("%-25s | %-10s | %-15s%n", "Email", "Role", "Balance (PHP)");
        System.out.println("-------------------------------------------------------");
        for (Account acc : userDatabase.values()) {
            System.out.printf("%-25s | %-10s | PHP %-12.2f%n", acc.email, acc.role, acc.balance);
        }
    }

    private static void viewSystemSummary() {
        System.out.println("\n--- SYSTEM SUMMARY ---");
        int totalAccounts = userDatabase.size();
        int standardUsers = 0;
        int adminUsers = 0;
        double totalFunds = 0.0;

        for (Account acc : userDatabase.values()) {
            if (acc.role == Role.STANDARD) {
                standardUsers++;
                totalFunds += acc.balance;
            } else {
                adminUsers++;
            }
        }

        System.out.println("Total Accounts System-Wide: " + totalAccounts);
        System.out.println("Standard User Accounts    : " + standardUsers);
        System.out.println("Admin Accounts            : " + adminUsers);
        System.out.printf("Total User Funds Held     : PHP %.2f%n", totalFunds);
    }

    private static void searchUserAccount(Scanner scanner) {
        System.out.println("\n--- SEARCH USER ACCOUNT ---");
        System.out.print("Enter Target Email: ");
        String targetEmail = scanner.nextLine().trim();

        Account acc = userDatabase.get(targetEmail);
        if (acc == null) {
            System.out.println("[ERROR] User account not found.");
            return;
        }

        System.out.println("\nUser Details:");
        System.out.println("Email   : " + acc.email);
        System.out.println("Role    : " + acc.role);
        System.out.printf("Balance : PHP %.2f%n", acc.balance);
        System.out.println("\nTransaction Logs:");
        for (String txn : acc.transactions) {
            System.out.println(" • " + txn);
        }
    }
}