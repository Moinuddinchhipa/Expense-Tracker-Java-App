package alephys_assessment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * ExpenseTracker is a simple command-line application
 * for tracking income and expenses, saving and loading data to/from files,
 * and generating monthly financial summaries.
 */
public class ExpenseTracker {

    // List to store all transactions in memory
    private static List<Transaction> transactions = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to Expense Tracker!");

        while (true) {
            // Display menu options
            System.out.println("\nChoose an option:");
            System.out.println("1. Add Income/Expense");
            System.out.println("2. View Monthly Summary");
            System.out.println("3. Load from File");
            System.out.println("4. Save to File");
            System.out.println("5. Exit");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number from 1 to 5.");
                continue;
            }

            // Handle user's choice
            switch (choice) {
                case 1:
                    addTransaction();
                    break;
                case 2:
                    showMonthlySummary();
                    break;
                case 3:
                    loadFromFile();
                    break;
                case 4:
                    saveToFile();
                    break;
                case 5:
                    System.out.println("Exiting. Goodbye!");
                    System.exit(0); // Exit program
                default:
                    System.out.println("Invalid choice! Please enter a number from 1 to 5.");
            }
        }
    }

    /**
     * Adds a new income or expense transaction to the list
     * This method validates input and loops until correct data is entered.
     */
    private static void addTransaction() {
        String type;
        while (true) {
            System.out.print("Enter type (income/expense): ");
            type = scanner.nextLine().trim().toLowerCase();
            if (type.equals("income") || type.equals("expense")) {
                break; // valid input
            } else {
                System.out.println("Invalid type! Please enter 'income' or 'expense'.");
            }
        }

        List<String> validCategories = List.of("salary", "business", "food", "rent", "travel");
        String category;
        while (true) {
            System.out.print("Enter category (salary/business/food/rent/travel): ");
            category = scanner.nextLine().trim().toLowerCase();
            if (validCategories.contains(category)) {
                break; // valid category
            } else {
                System.out.println("Invalid category! Please choose from: salary, business, food, rent, travel.");
            }
        }

        double amount;
        while (true) {
            System.out.print("Enter amount: ");
            String amountStr = scanner.nextLine().trim();
            try {
                amount = Double.parseDouble(amountStr);
                if (amount > 0) {
                    break; // valid positive amount
                } else {
                    System.out.println("Amount must be positive! Please enter again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount! Please enter a valid number.");
            }
        }

        LocalDate date;
        while (true) {
            System.out.print("Enter date (yyyy-MM-dd): ");
            String dateStr = scanner.nextLine().trim();
            try {
                date = LocalDate.parse(dateStr);
                break; // valid date format
            } catch (Exception e) {
                System.out.println("Invalid date format! Please use yyyy-MM-dd.");
            }
        }

        // Add new transaction after all validations pass
        transactions.add(new Transaction(type, category, amount, date));
        System.out.println("Transaction added successfully!");
    }

    /**
     * Displays a summary of transactions grouped by month and year,
     * showing net balance, total income, and total expenses.
     */
    private static void showMonthlySummary() {
        Map<String, Double> monthlyNetBalance = new HashMap<>();
        double totalIncome = 0, totalExpense = 0;

        for (Transaction t : transactions) {
            // Key format: MONTH-YEAR (e.g., MAY-2025)
            String key = t.date.getMonth() + "-" + t.date.getYear();

            // Update net balance per month: income adds, expense subtracts
            monthlyNetBalance.putIfAbsent(key, 0.0);
            monthlyNetBalance.put(key,
                    monthlyNetBalance.get(key) + (t.type.equals("income") ? t.amount : -t.amount));

            // Aggregate total income and expenses for all transactions
            if (t.type.equals("income")) {
                totalIncome += t.amount;
            } else {
                totalExpense += t.amount;
            }
        }

        // Print monthly summary
        System.out.println("\n--- Monthly Summary ---");
        monthlyNetBalance.forEach((monthYear, net) -> System.out.println(monthYear + ": Net Balance = " + net));
        System.out.println("Total Income: " + totalIncome);
        System.out.println("Total Expenses: " + totalExpense);
    }

    /**
     * Saves all current transactions to a file specified by the user.
     * File format: CSV with fields: type,category,amount,date
     */
    private static void saveToFile() throws IOException {
        System.out.print("Enter file name to save: ");
        String filename = scanner.nextLine();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Transaction t : transactions) {
                writer.write(t.toString()); // CSV format from Transaction.toString()
                writer.newLine();
            }
        }
        System.out.println("Data saved to file successfully!");
    }

    /**
     * Loads transactions from a specified file.
     * Clears current transactions before loading to avoid duplicates.
     * Expects file in CSV format: type,category,amount,date
     */
    private static void loadFromFile() throws IOException {
        System.out.print("Enter file name to load: ");
        String filename = scanner.nextLine();

        transactions.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 4) {
                    System.out.println("Skipping invalid line: " + line);
                    continue;
                }
                try {
                    Transaction t = new Transaction(
                            parts[0].trim(),
                            parts[1].trim(),
                            Double.parseDouble(parts[2].trim()),
                            LocalDate.parse(parts[3].trim()));
                    transactions.add(t);
                } catch (Exception e) {
                    System.out.println("Skipping invalid transaction data: " + line);
                }
            }
        }
        System.out.println("Data loaded from file successfully!");
    }
}