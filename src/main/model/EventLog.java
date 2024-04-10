package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


// manages a collection of events and provides methods to log new events, clear the log of all events,
// and iterate over the events. The class ensures that there is only one instance of EventLog throughout
// the application, preventing the duplication of event logs and providing a centralized mechanism to access
// and manipulate the event history.

public class EventLog implements Iterable<Event> {
    private static EventLog theLog;
    private Collection<Event> events;


     //Prevents external instantiation.
    private EventLog() {
        events = new ArrayList<>();
    }


    //Provides global access to the single instance of EventLog.
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }
        return theLog;
    }


    //Adds an event to the log.
    public void logEvent(Event e) {
        events.add(e);
    }


    //Clears the log of all events, then logs the clearance.
    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared."));
    }

    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}
