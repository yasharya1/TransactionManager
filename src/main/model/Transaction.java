package model;

import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDate;

// Holds single financial transaction, including all transaction details such
// id, description, amount of transaction, date of transaction and whether
// the transaction was an income transaction or an expense .

public class Transaction implements Writable {
    private static int counter = 0;
    private final int id;
    private final String description;
    private final double amount;
    private final LocalDate date;
    private final TransactionType type;
    private final String category;

    // EFFECTS: intialise a transaction
    public Transaction(double amount, LocalDate date,
                       TransactionType type, String description,
                       String category) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount has to be positive");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        if (description == null) {
            throw new IllegalArgumentException("Description cannot be null");
        }
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }
        this.id = ++counter;
        this.amount = amount;
        this.date = date;
        this.type = type;
        this.description = description;
        this.category = category;
    }


    //REQUIRES: transaction to have a type set
    //MODIFIES: this
    //EFFECTS: returns true if the type of transaction is an expense
    public boolean isExpense() {
        return this.type == TransactionType.EXPENSE;
    }

    public LocalDate getDate() {
        return this.date;
    }


    public String getDescription() {
        return this.description;
    }



    public TransactionType getType() {
        return type;
    }



    public int getId() {
        return id;
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: changes counter to equal 0
    public static void resetCounter() {
        counter = 0;
    }



    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public enum TransactionType {
        EXPENSE, INCOME
    }

    // EFFECTS: takes the individual transaction fields and turns them into the
    // required json format for a complete transaction
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("description", description);
        json.put("amount", amount);
        json.put("date", date);
        json.put("type", type);
        json.put("category", category);
        return json;
    }


}
