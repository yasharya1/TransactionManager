package ui;

import model.EventLog;
import model.Event;

import javax.swing.SwingUtilities;

public class Main {
    // EFFECTS: runs main method which runs the GuiManagerApp
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            EventLog l = EventLog.getInstance();
            for (Event e : l) {
                System.out.println(e);
            }
        }));
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GuiManagerApp app = new GuiManagerApp();
                app.setVisible(true);
            }
        });
    }
}
