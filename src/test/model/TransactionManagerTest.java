package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionManagerTest {
    private TransactionManager testManager;
    private final String testFile = "test_transactions.ser";

    @BeforeEach
    void runBefore() {
        testManager = new TransactionManager();
        Transaction.resetCounter();
    }

    @Test
    void testAddRemoveTransaction() {
        testManager.addTransaction(200, LocalDate.of(2023, 2, 15),
                Transaction.TransactionType.EXPENSE, "Tuition", "Misc");
        assertEquals(1, testManager.numTransactions());
        testManager.addTransaction(200, LocalDate.of(2023, 2, 15),
                Transaction.TransactionType.EXPENSE, "Groceries", "Food");
        assertEquals(2, testManager.numTransactions());
        assertFalse(testManager.removeTransaction(3));
        assertTrue(testManager.removeTransaction(2));
        assertTrue(testManager.removeTransaction(1));
        assertFalse(testManager.removeTransaction(1));

    }

    @Test
    void testGetAllTransactions() {
        assertTrue(testManager.getAllTransactions().isEmpty());
        testManager.addTransaction(200, LocalDate.of(2023, 2, 15),
                Transaction.TransactionType.EXPENSE, "Tuition", "Misc");
        assertEquals(1, testManager.getTransactions().size());
        testManager.addTransaction(200, LocalDate.of(2023, 2, 15),
                Transaction.TransactionType.EXPENSE, "Groceries", "Food");
        assertEquals(2, testManager.getAllTransactions().size());
    }

    @Test
    void testGetTransactionsByCategory() {
        testManager.addTransaction(200, LocalDate.of(2023, 2, 15),
                Transaction.TransactionType.EXPENSE, "Tuition", "Misc");
        testManager.addTransaction(200, LocalDate.of(2023, 2, 15),
                Transaction.TransactionType.EXPENSE, "Groceries", "Food");
        List<Transaction> foodTransactions = testManager.getTransactionsByCategory("Food");
        assertEquals(1, foodTransactions.size());
        assertTrue(testManager.getTransactionsByCategory("Lifestyle").isEmpty());
    }

    @Test
    void testSetTransactions() {
        List<Transaction> newTransactions = new ArrayList<>();
        assertTrue(testManager.getAllTransactions().isEmpty());
        testManager.addTransaction(200, LocalDate.of(2023, 2, 15),
                Transaction.TransactionType.EXPENSE, "Tuition", "Misc");
        testManager.addTransaction(200, LocalDate.of(2023, 2, 15),
                Transaction.TransactionType.EXPENSE, "Groceries", "Food");
        assertEquals(2, testManager.getTransactions().size());
        testManager.setTransactions(newTransactions);
        assertTrue(testManager.getAllTransactions().isEmpty());

    }

}