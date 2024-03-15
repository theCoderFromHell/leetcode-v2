package easy;

import java.util.HashMap;
import java.util.HashSet;

public class Logger {
    private HashMap<String, Integer> nextTime;
    public Logger() {
        nextTime = new HashMap<>();
    }

    public boolean shouldPrintMessage(int timestamp, String message) {
        Integer curr = nextTime.getOrDefault(message, 0);
        if (curr <= timestamp) {
            nextTime.put(message, timestamp + 10);
            return true;
        }
        return false;
    }
}

/**
 * Your Logger object will be instantiated and called as such:
 * Logger obj = new Logger();
 * boolean param_1 = obj.shouldPrintMessage(timestamp,message);
 */