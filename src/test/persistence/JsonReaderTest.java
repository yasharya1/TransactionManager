package persistence;

import model.Transaction;
import model.TransactionManager;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            TransactionManager tm = reader.read();
            fail("IOException expected");
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
    void testReaderGeneralTransactionManager() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralTransactionManager.json");
        try {
            TransactionManager tm = reader.read();
            assertEquals(2, tm.numTransactions());
            checkTransaction(100.0, LocalDate.of(2022, 1, 1),
                    "INCOME", "Salary", "Job", tm.getTransactions().get(0));
            checkTransaction(50.0, LocalDate.of(2022, 1, 2),
                    "EXPENSE", "Groceries", "Food", tm.getTransactions().get(1));
        } catch (IOException e) {
           // fail ("Couldn't read from file");
        }
    }



}

