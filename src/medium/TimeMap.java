package medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class TimeMap {
    private HashMap<String, List<TimedValue>> hashMap;

    public TimeMap() {
        hashMap = new HashMap<>();
    }

    public void set(String key, String value, int timestamp) {
        List<TimedValue> timedValues = hashMap.getOrDefault(key, new ArrayList<>());
        timedValues.add(new TimedValue(value, timestamp));
        hashMap.put(key, timedValues);
    }

    public String get(String key, int timestamp) {
        List<TimedValue> timedValues = hashMap.getOrDefault(key, new ArrayList<>());
        if(timedValues.isEmpty())
            return "";
        else
            return binarySearch(timedValues, 0, timedValues.size()-1, timestamp);
    }

    private String binarySearch(List<TimedValue> timedValues, int start, int end, int timestamp) {
        if(timedValues.isEmpty())
            return "";
        if(timedValues.get(start).getTimestamp() > timestamp)
            return "";
        if(end == start)
            return timedValues.get(start).getValue();
        if(end == start+1) {
            return timedValues.get(end).getTimestamp() <= timestamp
                    ? timedValues.get(end).getValue() : timedValues.get(start).getValue();
        }
        int mid = (end-start)/2 + start;
        if(timedValues.get(mid).getTimestamp() == timestamp)
            return timedValues.get(mid).getValue();
        if(timedValues.get(mid).getTimestamp() < timestamp) {
            return binarySearch(timedValues, mid+1, end, timestamp);
        } else
            return binarySearch(timedValues, start, mid-1, timestamp);
    }

    public static void main(String[] args) {
        TimeMap timeMap = new TimeMap();
        timeMap.set("foo", "bar", 1);
        System.out.println(timeMap.get("foo", 1));
        System.out.println(timeMap.get("foo", 3));
        timeMap.set("foo", "bar2", 4);
        System.out.println(timeMap.get("foo", 4));
        System.out.println(timeMap.get("foo", 5));
    }
}

class TimedValue {
    private String value;
    private int timestamp;

    public TimedValue(String value, int timestamp) {
        this.value = value;
        this.timestamp = timestamp;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
}

/**
 * Your TimeMap object will be instantiated and called as such:
 * TimeMap obj = new TimeMap();
 * obj.set(key,value,timestamp);
 * String param_2 = obj.get(key,timestamp);
 */
