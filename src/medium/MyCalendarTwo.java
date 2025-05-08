package medium;

import java.util.Map;
import java.util.TreeMap;

public class MyCalendarTwo {
    private Map<Integer,Integer> bookingMap;
    public MyCalendarTwo() {
        this.bookingMap = new TreeMap<>();
    }

    public boolean book(int startTime, int endTime) {
        bookingMap.put(startTime, bookingMap.getOrDefault(startTime, 0) + 1);
        bookingMap.put(endTime, bookingMap.getOrDefault(endTime, 0) - 1);
        int maxCount = 0;
        for (int time : bookingMap.keySet()) {
            maxCount += bookingMap.get(time);
            if (maxCount > 2) {
                bookingMap.put(startTime, bookingMap.getOrDefault(startTime, 0) - 1);
                bookingMap.put(endTime, bookingMap.getOrDefault(endTime, 0) + 1);
                if (bookingMap.getOrDefault(startTime, 0) == 0)
                    bookingMap.remove(startTime);
                if (bookingMap.getOrDefault(endTime, 0) == 0)
                    bookingMap.remove(endTime);
                return false;
            }
        }
        return true;
    }
}
