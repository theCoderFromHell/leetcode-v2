package hard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FreqStack {
    Map<Integer, Integer> countMap;
    ArrayList<ArrayList<Integer>> freqList;
    int maxFrequency;

    public FreqStack() {
        this.countMap = new HashMap<>();
        this.freqList = new ArrayList<>();
        freqList.add(new ArrayList<>());
        this.maxFrequency = 0;
    }

    public void push(int value) {
        int frequency = countMap.getOrDefault(value, 0);
        countMap.put(value, ++frequency);
        if (frequency >= freqList.size())
            freqList.add(new ArrayList<>());
        freqList.get(frequency).add(value);
        maxFrequency = Math.max(maxFrequency, frequency);
    }

    public int pop() {
        ArrayList<Integer> mostFrequent = freqList.get(maxFrequency);
        int value = mostFrequent.getLast();
        mostFrequent.removeLast();
        if (mostFrequent.isEmpty())
            maxFrequency--;
        countMap.put(value, countMap.get(value)-1);
        return value;
    }

    public static void main(String[] args) {
        FreqStack freqStack = new FreqStack();
        freqStack.push(5);
        freqStack.push(7);
        freqStack.push(5);
        freqStack.push(7);
        freqStack.push(4);
        freqStack.push(5);
        System.out.println(freqStack.pop());
        System.out.println(freqStack.pop());
        System.out.println(freqStack.pop());
        System.out.println(freqStack.pop());
    }
}
