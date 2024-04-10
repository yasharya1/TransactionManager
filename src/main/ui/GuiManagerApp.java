package ui;

import model.Transaction;
import model.TransactionManager;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

// GuiManagerApp is basically the main user interface that allows the app to run by creating the UI,
// it allows us to manage financial transactions, add and remove transactions as well as
// view our spending based on different categories. It works with the TransactionManager class
// for using the management methods.

public class GuiManagerApp extends JFrame {
    private static final String JSON_STORE = "./data/transactions.json";
    private TransactionManager manager;
    private TransactionTableModel tableModel;
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private JTextField amountField;
    private JTextField dateField;
    private JComboBox<Transaction.TransactionType> typeBox;
    private JTextField descriptionField;
    private JTextField categoryField;
    private  JTextField removeIdField;

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: instantiates the GuiManagerApp
    public GuiManagerApp() {
        manager = new TransactionManager();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        initUI();
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: initializes the UI window for the user to access
    private void initUI() {
        setTitle("Transaction Manager");
        setSize(1800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(createTransactionInputPanel());
        centerPanel.add(createRemovePanel());
        centerPanel.add(createFilterPanel());
        add(centerPanel, BorderLayout.NORTH);
        tableModel = new TransactionTableModel(manager.getAllTransactions());
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: creates panel in the main window for user input
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save Transactions");
        JButton loadButton = new JButton("Load Transactions");
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        saveButton.addActionListener(e -> saveTransactions());
        loadButton.addActionListener(e -> loadTransactions());

        return buttonPanel;

    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: creates panel in the main window for user input
    private JPanel createTransactionInputPanel() {
        JPanel inputPanel = new JPanel(new GridLayout(0, 2));

        amountField = new JTextField(5);
        dateField = new JTextField(5);
        typeBox = new JComboBox<>(Transaction.TransactionType.values());
        descriptionField = new JTextField(5);
        categoryField = new JTextField(5);
        removeIdField = new JTextField(10);

        inputPanel.add(new JLabel("Amount:"));
        inputPanel.add(amountField);
        inputPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        inputPanel.add(dateField);
        inputPanel.add(new JLabel("Type:"));
        inputPanel.add(typeBox);
        inputPanel.add(new JLabel("Description:"));
        inputPanel.add(descriptionField);
        inputPanel.add(new JLabel("Category:"));
        inputPanel.add(categoryField);

        JButton addButton = new JButton("Add Transaction");
        addButton.addActionListener(this::addTransaction);
        inputPanel.add(addButton);

        return inputPanel;
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: creates panel in main window for user story of removing transactions
    private JPanel createRemovePanel() {
        JPanel removePanel = new JPanel(new FlowLayout());
        removePanel.add(new JLabel("Transaction ID to remove:"));
        removePanel.add(removeIdField);

        JButton removeButton = new JButton("Remove Transaction");
        removeButton.addActionListener(this::removeTransaction);
        removePanel.add(removeButton);

        return removePanel;
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: creates panel in main window for user story of filtering transactions
    private JPanel createFilterPanel() {
        JPanel filterPanel = new JPanel();
        JComboBox<String> categoryBox = new JComboBox<>();
        JButton filterButton = new JButton("Filter");

        categoryBox.addItem("All");
        categoryBox.addItem("Salary");
        categoryBox.addItem("Home");
        categoryBox.addItem("Lifestyle");
        categoryBox.addItem("Rent");
        categoryBox.addItem("Food");
        categoryBox.addItem("Misc");

        filterPanel.add(new JLabel("Category:"));
        filterPanel.add(categoryBox);
        filterPanel.add(filterButton);

        filterButton.addActionListener(e -> {
            String selectedCategory = (String) categoryBox.getSelectedItem();
            filterTransactionsByCategory(selectedCategory);
        });

        return filterPanel;
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: adds transactions to be json file and displays splash image
    private void addTransaction(ActionEvent e) {
        try {
            double amount = Double.parseDouble(amountField.getText());
            LocalDate date = LocalDate.parse(dateField.getText());
            Transaction.TransactionType type = (Transaction.TransactionType) typeBox.getSelectedItem();
            String description = descriptionField.getText();
            String category = categoryField.getText();

            manager.addTransaction(amount, date, type, description, category);
            refreshTable();
            JOptionPane.showMessageDialog(this, "Transaction added successfully.",
                    "Transaction Added", JOptionPane.INFORMATION_MESSAGE);
            showSplashScreen();


            amountField.setText("");
            dateField.setText("");
            descriptionField.setText("");
            categoryField.setText("");
            typeBox.setSelectedIndex(0);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error adding transaction: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: removes transactions from transactions table in UI
    private void removeTransaction(ActionEvent e) {
        try {
            int idToRemove = Integer.parseInt(removeIdField.getText());
            boolean removed = manager.removeTransaction(idToRemove);
            if (removed) {
                refreshTable();
                JOptionPane.showMessageDialog(this, "Transaction removed successfully.",
                        "Transaction Removed", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No transaction found with ID: " + idToRemove,
                        "Remove Transaction", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid ID.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: shows a filtered specifc list of transactions on the UI
    private void filterTransactionsByCategory(String category) {
        if (category == null || category.equals("All")) {
            tableModel.setTransactions(manager.getAllTransactions());
        } else {
            tableModel.setTransactions(manager.getTransactionsByCategory(category));
        }
        tableModel.fireTableDataChanged();
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: makes sure that the table on UI is showing most up to date state of transactions
    public void refreshTable() {
        SwingUtilities.invokeLater(() -> {
            tableModel.setTransactions(manager.getAllTransactions());
            tableModel.fireTableDataChanged();
        });
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: saves new added transactions to Json file
    public void saveTransactions() {
        try {
            jsonWriter.open();
            jsonWriter.write(manager);
            jsonWriter.close();
            JOptionPane.showMessageDialog(this, "Transactions saved to " + JSON_STORE,
                    "Save Transactions", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Unable to write to file: " + JSON_STORE,
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: loads Json file to the table specific form in the UI
    private void loadTransactions() {
        try {
            manager = jsonReader.read();
            refreshTable();
            JOptionPane.showMessageDialog(this, "Transactions loaded from " + JSON_STORE,
                    "Load Transactions", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Unable to read from file: " + JSON_STORE,
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: shows the image stored locally
    private void showSplashScreen() {
        SplashImage splash = new SplashImage("data/mayweathermoney.jpeg", 1000,
                600, 400);
        splash.setVisible(true);
    }



}
