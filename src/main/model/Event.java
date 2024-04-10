package model;

import java.util.Calendar;
import java.util.Date;


//represents an Event entity within the system. Each instance of Event captures a unique event by storing
// the event's description and the date it was logged. The dateLogged field records the date
// and time when the event instance was created, providing a temporal context for the event. t entails.

public class Event {
    private static final int HASH_CONSTANT = 13;
    private Date dateLogged;
    private String description;


    //Requires: A non-null String argument description representing the event's description.
    //Effects: Initializes a new Event instance with the current date and time as its dateLogged and sets its
    // description to the provided argument.
    public Event(String description) {
        dateLogged = Calendar.getInstance().getTime();
        this.description = description;
    }


    // Effects: Returns the date and time when the event was logged as a Date object.
    public Date getDate() {
        return dateLogged;
    }

    // Effects:  changes the date to the given date
    public void setDate(Date date) {
        this.dateLogged = date;
    }


    // Effects: Returns the textual description of the event as a String.
    public String getDescription() {
        return description;
    }

    // Effects: Returns true if the provided object is an instance of Event and both the date logged and the
    // description match between the two events. Otherwise, returns false.
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        Event event = (Event) other;

        return (this.dateLogged.equals(event.dateLogged))
                &&
                this.description.equals(event.description);
    }


    // Effects: Generates and returns a hash code value for the Event instance based on its dateLogged and description.
    @Override
    public int hashCode() {
        return (HASH_CONSTANT * dateLogged.hashCode() + description.hashCode());
    }


    // Effects: Returns a String representation of the Event instance, including its logged date and description.
    @Override
    public String toString() {
        return dateLogged.toString() + "\n" + description;
    }

}
