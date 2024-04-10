package persistence;

import model.Transaction;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkTransaction(double amount, LocalDate date, String type, String description,
                                    String category, Transaction transaction) {
        assertEquals(amount, transaction.getAmount());
        assertEquals(category, transaction.getCategory());
        assertEquals(date, transaction.getDate());
        assertEquals(type, transaction.getType().toString());
        assertEquals(description, transaction.getDescription());
    }
}
