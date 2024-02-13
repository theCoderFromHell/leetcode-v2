package hard;

import java.util.*;

public class FreqStackV2 {
    int counter;
    HashMap<Integer, CountWithTime> map;
    PriorityQueue<CountWithTime> pq;

    public FreqStackV2() {
        this.counter = 0;
        this.map = new HashMap<>();
        this.pq = new PriorityQueue<>((o1, o2) -> {
            if (o1.count != o2.count)
                return o2.count - o1.count;
            else
                return o2.getTime() - o1.getTime();
        });
    }

    public void push(int val) {
        CountWithTime countWithTime;
        if (map.containsKey(val)) {
            countWithTime = map.get(val);
            pq.remove(countWithTime);
        } else
            countWithTime = new CountWithTime(val);
        countWithTime.time.push(++counter);
        countWithTime.count = countWithTime.count + 1;
        map.put(val, countWithTime);
        pq.add(countWithTime);
    }

    public int pop() {
        CountWithTime c = pq.poll();
        CountWithTime countWithTime = map.get(c.value);
        countWithTime.count = countWithTime.count - 1;
        countWithTime.time.pop();
        if (countWithTime.count == 0)
            map.remove(c.value);
        else {
            map.put(c.value, countWithTime);
            pq.add(countWithTime);
        }
        return c.value;
    }

    public static void main(String[] args) {
        FreqStackV2 freqStackV2 = new FreqStackV2();
        freqStackV2.push(5);
        freqStackV2.push(7);
        freqStackV2.push(5);
        freqStackV2.push(7);
        freqStackV2.push(4);
        freqStackV2.push(5);
        System.out.println(freqStackV2.pop());
        System.out.println(freqStackV2.pop());
        System.out.println(freqStackV2.pop());
        System.out.println(freqStackV2.pop());
    }
}

class CountWithTime {
    int value;
    int count;
    Stack<Integer> time;
    public CountWithTime(int value){
        this.value = value;
        this.time = new Stack<>();
    }
    public int getValue() {
        return value;
    }

    public int getCount() {
        return count;
    }

    public int getTime() {
        return time.peek();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountWithTime that = (CountWithTime) o;
        return value == that.value && count == that.count && time == that.time;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, count, time);
    }
}

/**
 * Your FreqStack object will be instantiated and called as such:
 * FreqStack obj = new FreqStack();
 * obj.push(val);
 * int param_2 = obj.pop();
 */
