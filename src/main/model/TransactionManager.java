package model;

import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONArray;
import java.util.Iterator;


// Represents the list of multiple transaction and includes the required
// method to be able to display, add and remove from the list of current
// transactions and create a new list of all transactions.

public class TransactionManager implements Writable {
    private List<Transaction> transactions;

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: initializes transaction manager
    public TransactionManager() {
        this.transactions = new ArrayList<>();
    }


    //REQUIRES: valid transaction information
    //MODIFIES: this
    //EFFECTS: adds given transaction to list of transactions
    public void addTransaction(double amount, LocalDate date,
                               Transaction.TransactionType type, String description,
                               String category) {
        Transaction newTran = new Transaction(amount, date, type, description, category);
        transactions.add(newTran);
        EventLog.getInstance().logEvent(new Event("Transaction added! " + "Name:" + newTran.getDescription()
                + " Type:" + newTran.getType() + " Category:" + newTran.getCategory()));
    }

    //REQUIRES: transactions list to not be empty
    //MODIFIES: this
    //EFFECTS: removes matching id transaction from the list and returns true if removed
    public boolean removeTransaction(int id) {
        Iterator<Transaction> iterator = transactions.iterator();
        while (iterator.hasNext()) {
            Transaction t = iterator.next();
            if (t.getId() == id) {
                iterator.remove();
                EventLog.getInstance().logEvent(new Event("Transaction removed! " + "Name:"
                        + t.getDescription()
                        + " Type:" + t.getType() + " Category:" + t.getCategory()));
                return true;
            }
        }
        return false;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    //REQUIRES:
    //MODIFIES: transactions
    //EFFECTS: sets new transactions to the transactions
    public void setTransactions(List<Transaction> newTransactions) {
        this.transactions = newTransactions;
    }

    //REQUIRES:
    //MODIFIES: transactions
    //EFFECTS: filters transactions list to only those from a specific category
    public List<Transaction> getTransactionsByCategory(String category) {
        return transactions.stream()
                .filter(tran -> tran.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: creates a new list with all transactions
    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions);
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: return the size of the transactions list
    public int numTransactions() {
        return transactions.size();
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: runs each transaction from the transactions list to its required Json format
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("transactions", transactionsToJson());
        return json;
    }

    // EFFECTS: returns things in this Transaction Manger as a JSON array
    private JSONArray transactionsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Transaction t : transactions) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }

}
