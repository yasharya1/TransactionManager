package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

class TransactionTest {
    private Transaction testExpenseTransaction;
    private Transaction testIncomeTransaction;

    @BeforeEach
    void runBefore(){
        testExpenseTransaction = new Transaction(200, LocalDate.of(2023, 10,22),
                Transaction.TransactionType.EXPENSE, "Rent", "Home");
        testIncomeTransaction = new Transaction(400, LocalDate.of(2023, 10,22),
                Transaction.TransactionType.INCOME, "Salary", "Lifestyle");
        Transaction.resetCounter();
    }

    @Test
    void testConstructor() {
        assertEquals(1, testExpenseTransaction.getId());
        assertEquals(200, testExpenseTransaction.getAmount());
        assertEquals(LocalDate.of(2023, 10, 22), testExpenseTransaction.getDate());
        assertEquals(Transaction.TransactionType.EXPENSE, testExpenseTransaction.getType());
        assertEquals("Rent", testExpenseTransaction.getDescription());
    }

    @Test
    void testGetCategory() {
        assertEquals("Home", testExpenseTransaction.getCategory());
    }

    @Test
    void testIsExpense() {
        assertTrue(testExpenseTransaction.isExpense());
        assertFalse(testIncomeTransaction.isExpense());
    }

    @Test
    void negativeAmount_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Transaction(-10, LocalDate.now(),
                    Transaction.TransactionType.EXPENSE, "Shoes", "Misc");
        }, "Amount has to be positive");
    }

    @Test
    void nullDate_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Transaction(10, null,
                    Transaction.TransactionType.EXPENSE, "Shoes", "Misc");
        }, "Date cannot be null");
    }

    @Test
    void nullDescription_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Transaction(10, LocalDate.now(),
                    Transaction.TransactionType.EXPENSE, null, "Misc");
        }, "Description cannot be null");
    }

    @Test
    void nullCategory_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Transaction(10, LocalDate.now(),
                    Transaction.TransactionType.EXPENSE, "Shoes", null);
        }, "Category cannot be null");
    }


}