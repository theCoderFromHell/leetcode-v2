package medium;

import java.util.TreeMap;

public class MyCalendar {
    TreeMap<Integer,Integer> map;
    public MyCalendar() {
        this.map = new TreeMap<>();
    }

    public boolean book(int start, int end) {
        Integer previous = map.floorKey(start);
        Integer next = map.ceilingKey(start);
        if ((previous == null || map.get(previous) <= start) && (next == null || next >= end)) {
            map.put(start, end);
            return true;
        }
        return false;
    }
}
