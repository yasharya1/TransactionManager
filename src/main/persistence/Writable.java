package persistence;

import org.json.JSONObject;

// ALlows new transactions to be written into the Json

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
