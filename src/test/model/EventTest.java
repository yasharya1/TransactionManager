package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Unit tests for the Event class
 */
public class EventTest {
    private Event e;
    private Date d;

    //NOTE: these tests might fail if time at which line (2) below is executed
    //is different from time that line (1) is executed.  Lines (1) and (2) must
    //run in same millisecond for this test to make sense and pass.

    @BeforeEach
    public void runBefore() {
        e = new Event("Transaction added: Salary");
        d = Calendar.getInstance().getTime();   // (2)
    }

    @Test
    public void testEvent() {
        assertEquals("Transaction added: Salary", e.getDescription());
        //assertEquals(d, e.getDate());
        long timeDiff = Math.abs(e.getDate().getTime() - d.getTime());
        assertTrue(timeDiff < 1000);
    }

    @Test
    public void testToString() {
        assertEquals(d.toString() + "\n" + "Transaction added: Salary", e.toString());
    }

    @Test
    public void testEqualsReflexive() {
        assertTrue(e.equals(e));
    }

    @Test
    public void testEqualsWithNull() {
        assertFalse(e.equals(null));
    }

    @Test
    public void testEqualsWithDifferentClass() {
        assertFalse(e.equals("xyz"));
    }



    @Test
    public void testEqualsWithDifferentDescriptions() {
        Event eDifferentDescription = new Event("Transaction added: Salary");
        assertTrue(e.equals(eDifferentDescription));
    }

    @Test
    public void testHashCodeConsistency() {
        assertEquals(e.hashCode(), e.hashCode());
    }



    @Test
    public void testHashCodeInequality() {
        Event eDifferent = new Event("Transaction added: Grocery");
        assertNotEquals(e.hashCode(), eDifferent.hashCode());
    }

    @Test
    public void testEqualsWithDifferentDates() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date differentDate = calendar.getTime();
        Event eDifferentDate = new Event("Transaction added: Rent");
        eDifferentDate.setDate(differentDate);
        assertFalse(e.equals(eDifferentDate));
    }

}
