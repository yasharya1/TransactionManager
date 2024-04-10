package ui;

import model.Transaction;


import javax.swing.table.AbstractTableModel;
import java.util.List;


// The TransactionTableModel class extends AbstractTableModel and is designed to model the
// transaction data for a JTable. This model supports displaying transactions in a tabular format, where each
// transaction is a row in the table, and columns represent transaction attributes such as ID, amount, date, type,
// description, and category.

public class TransactionTableModel extends AbstractTableModel {
    private List<Transaction> transactions;
    private final String[] columnNames = {"ID", "Amount", "Date", "Type", "Description", "Category"};

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: initialises the TransactionTableModel
    public TransactionTableModel(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: sets the given list to the list of transactions
    public void setTransactions(List<Transaction> newTransactions) {
        transactions = newTransactions;
        fireTableDataChanged();
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: gets number of transactions in the list
    @Override
    public int getRowCount() {
        return transactions.size();
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: gets number of columns
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: returns specific value for a transaciton
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Transaction transaction = transactions.get(rowIndex);
        switch (columnIndex) {
            case 0: return transaction.getId();
            case 1: return transaction.getAmount();
            case 2: return transaction.getDate();
            case 3: return transaction.getType();
            case 4: return transaction.getDescription();
            case 5: return transaction.getCategory();
            default: return null;
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: returns the name of the column
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
