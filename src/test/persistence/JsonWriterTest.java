package persistence;

import model.Transaction;
import model.TransactionManager;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest{

    @Test
    void testWriterInvalidFile() {
        try {
            TransactionManager tm = new TransactionManager();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyTransactionManager() {
        try {
            TransactionManager tm = new TransactionManager();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyTransactionManager.json");
            writer.open();
            writer.write(tm);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyTransactionManager.json");
            tm = reader.read();
            assertEquals(0, tm.numTransactions());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


    @Test
    void testWriterGeneralTransactionManager() {
        try {
            TransactionManager tm = new TransactionManager();
            tm.addTransaction(200.0, LocalDate.of(2022, 1, 1),
                    Transaction.TransactionType.EXPENSE, "Dinner", "Food");
            tm.addTransaction(1500.0, LocalDate.of(2022, 1, 15),
                    Transaction.TransactionType.INCOME, "Freelance", "Job");

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralTransactionManager.json");
            writer.open();
            writer.write(tm);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralTransactionManager.json");
            tm = reader.read();
            assertEquals(2, tm.numTransactions());
            checkTransaction(200.0, LocalDate.of(2022, 1, 1),
                    "EXPENSE", "Dinner", "Food", tm.getTransactions().get(0));
            checkTransaction(1500.0, LocalDate.of(2022, 1, 15),
                    "INCOME", "Freelance", "Job", tm.getTransactions().get(1));

        } catch (IOException e) {
            fail("Should not have been thrown");
        }
    }

}
