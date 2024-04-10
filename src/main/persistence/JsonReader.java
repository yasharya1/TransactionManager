package persistence;


import model.Transaction;
import model.TransactionManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.time.LocalDate;

// Represents a reader that reads Transaction Manager from JSON data stored
public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads Transaction Manager from file and returns it;
    // throws IOException if an error occurs reading data from file
    public TransactionManager read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTransactionManger(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Transaction Manager from JSON object and returns it
    private TransactionManager parseTransactionManger(JSONObject jsonObject) {
        TransactionManager tm = new TransactionManager();
        addTransactions(tm, jsonObject);
        return tm;
    }

    // MODIFIES: tm
    // EFFECTS: parses thingies from JSON object and adds them to Transaction Manager
    private void addTransactions(TransactionManager tm, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("transactions");
        for (Object json : jsonArray) {
            JSONObject nextTransaction = (JSONObject) json;
            addTransaction(tm, nextTransaction);
        }
    }

    // MODIFIES: tm
    // EFFECTS: parses transaction from JSON object and adds it to Transaction Manager
    private void addTransaction(TransactionManager tm, JSONObject jsonObject) {
        double amount = jsonObject.getDouble("amount");
        LocalDate date = LocalDate.parse(jsonObject.getString("date"));
        Transaction.TransactionType type = Transaction.TransactionType.valueOf(jsonObject.getString("type"));
        String description = jsonObject.getString("description");
        String category = jsonObject.getString("category");
        tm.addTransaction(amount, date, type, description, category);
    }
}
