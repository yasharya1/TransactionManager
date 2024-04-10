package ui;

import model.Transaction;
import model.TransactionManager;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

// ManagerApp is basically the main user interface that allows the app to run in the console
// it allows us to manage financial transactions, add and remove transactions as well as
// view our spending based on different categories. It works with the TransactionManager class
// for using the management methods.

public class ManagerApp {
    private static final String JSON_STORE = "./data/transactions.json";
    private final TransactionManager manager;
    private final Scanner userInput;
    private double budget;
    private double totalExpenses = 0;
    private double totalIncome = 0;
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;

    // EFFECTS: constructs manager app and runs application
    public ManagerApp() {
        manager = new TransactionManager();
        userInput = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    public void runApp() {
        boolean running = true;
        while (running) {
            displayInitialOptions();
            String initialChoice = userInput.next().toLowerCase();
            switch (initialChoice) {
                case "s": saveTransactions();
                break;
                case"l": loadTransactions();
                break;
                case "b": createBudget();
                    manageTransactions();
                    break;
                case "q":
                    System.out.println("Exiting...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid Entry");
                    break;
            }
        }
    }

    // EFFECTS: displays menu of initial options to user
    private void displayInitialOptions() {
        System.out.println("\nWould you like to:");
        System.out.println("\ts -> Save transactions to file");
        System.out.println("\tl -> Load transactions from file");
        System.out.println("\tb -> Begin or Continue without further Saving/Loading");
        System.out.println("\tq -> Quit Entire Session");
    }

    // EFFECTS: gets user inputs from initial menu
    private void manageTransactions() {
        boolean managing = true;
        while (managing) {
            showMenu();
            String instruction = userInput.next().toLowerCase();
            managing = processManagementChoice(instruction);

        }
    }

    // EFFECTS: initiates different methods based on user input
    private boolean processManagementChoice(String choice) {
        switch (choice) {
            case "a": addTransaction();
                return true;
            case "r": removeTransaction();
                return true;
            case "p": printAllTransactions();
                return true;
            case "s": saveTransactions();
                return true;
            case "l": loadTransactions();
                return true;
            case "c": printTransactionByCat();
                return true;
            case "q": finalMessage();
                return false;
            default:
                System.out.println("Invalid Entry");
                return true;
        }
    }
    // EFFECTS: displays final message to user

    private void finalMessage() {
        double netCashFlow = totalIncome - totalExpenses;
        if (budget >= 0) {
            System.out.println("Session Complete. Remaining Budget: $" + budget +  ". Net Cash Flow: $" + netCashFlow);
        } else {
            System.out.println("Session Complete. Budget exceeded by: $" + Math.abs(budget) + ". Net Cash Flow: $"
                    + netCashFlow);
        }

    }

    // EFFECTS: displays transaction management menu to user
    private void showMenu() {
        System.out.println("\nChoose Option:");
        System.out.println("\ta -> Add a new transaction");
        System.out.println("\tr -> Remove a existing transaction");
        System.out.println("\tp -> Print all your transactions");
        System.out.println("\ts -> Save all your transactions to file");
        System.out.println("\tl -> Load all your transactions from file");
        System.out.println("\tc -> Print transaction by category");
        System.out.println("\tq -> Quit Transaction Entry");
    }

    //REQUIRES:
    //MODIFIES: budget
    //EFFECTS: assigns user input to budget value
    private void createBudget() {
        System.out.println("Enter Your Intended Spending Budget: ");
        budget = userInput.nextDouble();
    }

    //REQUIRES: valid transaction
    //MODIFIES: transactions list
    //EFFECTS: adds respective transaction to list of transactions
    private void addTransaction() {
        System.out.println("Enter amount: ");
        double amount = userInput.nextDouble();
        userInput.nextLine();
        System.out.println("Enter Date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(userInput.next());
        System.out.println("Enter Type (INCOME/EXPENSE): ");
        Transaction.TransactionType type = Transaction.TransactionType.valueOf(userInput.next().toUpperCase());
        System.out.println("Enter Name/Description: ");
        String description = userInput.next();
        System.out.println("Enter Category (Salary/Home/Lifestyle/Rent/Food/Misc): ");
        String category = userInput.next();

        manager.addTransaction(amount, date, type, description, category);

        if (type == Transaction.TransactionType.EXPENSE) {
            budget = budget - amount;
            totalExpenses = totalExpenses + amount;
            System.out.println("Expense added, Remaining Spending Budget: $" + budget);
        } else {
            totalIncome = totalIncome + amount;
            System.out.println("Income added, Remaining Spending Budget: $" + budget);
        }

    }



    //REQUIRES: transactions list to not be empty
    //MODIFIES: transactions
    //EFFECTS: remove transaction with matching id from transactions
    private void removeTransaction() {
        System.out.println("Enter Transaction ID: ");
        int id = userInput.nextInt();

        if (manager.removeTransaction(id)) {
            System.out.println("Successfully removed transaction");
        } else {
            System.out.println("Failed to remove transaction - ID not found");
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: adds together all transactions into single output
    private void printAllTransactions() {
        List<Transaction> transactions = manager.getAllTransactions();
        if (transactions.isEmpty()) {
            System.out.println("Transactions list is currently empty");
            return;
        }
        for (Transaction transaction : transactions) {
            printDetails(transaction);
            System.out.println();
        }
    }

    // EFFECTS: saves the transaction manager to file
    private void saveTransactions() {
        try {
            jsonWriter.open();
            jsonWriter.write(manager);
            jsonWriter.close();
            System.out.println("Transactions saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to read from file " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads transaction manager from file
    private void loadTransactions() {
        try {
            TransactionManager loadedManager = jsonReader.read();
            manager.setTransactions(loadedManager.getTransactions());
            System.out.println("Transactions loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file " + JSON_STORE);
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: prints transactions based on user category input
    private void printTransactionByCat() {
        System.out.println("Enter Category (Salary/Home/Lifestyle/Rent/Food/Misc): ");
        String category = userInput.next();

        List<Transaction> transactions = manager.getTransactionsByCategory(category);
        if (transactions.isEmpty()) {
            System.out.println("No transaction in this Category");
            return;
        }

        for (Transaction transaction : transactions) {
            printDetails(transaction);
            System.out.println();
        }
    }

    //REQUIRES: valid transaction
    //MODIFIES:
    //EFFECTS: prints all the details of the transaction
    private void printDetails(Transaction transaction) {
        System.out.println("ID: " + transaction.getId());
        System.out.println("Amount: " + transaction.getAmount());
        System.out.println("Date: " + transaction.getDate());
        System.out.println("Type: " + transaction.getType());
        System.out.println("Description/Name: " + transaction.getDescription());
    }
}
