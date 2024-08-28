package hard;

import java.util.HashMap;
import java.util.LinkedHashSet;

public class LFUCache {
    int capacity;
    int minFrequency;
    HashMap<Integer, int[]> map;
    HashMap<Integer, LinkedHashSet<Integer>> frequencyOfUse;
    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.minFrequency = Integer.MAX_VALUE;
        this.map = new HashMap<>();
        this.frequencyOfUse = new HashMap<>();
    }

    public int get(int key) {
        if (!map.containsKey(key))
            return -1;
        int[] values = map.get(key);
        int frequency = values[0];
        LinkedHashSet<Integer> numbersInNectFrequency = frequencyOfUse.getOrDefault(frequency+1, new LinkedHashSet<>());
        numbersInNectFrequency.addLast(key);
        frequencyOfUse.put(frequency+1, numbersInNectFrequency);
        LinkedHashSet<Integer> numbers = frequencyOfUse.get(frequency);
        numbers.remove(key);
        if (numbers.isEmpty()) {
            frequencyOfUse.remove(frequency);
            if (minFrequency == frequency)
                minFrequency = findNextFrequency(frequencyOfUse, minFrequency+1);
        }
        else
            frequencyOfUse.put(frequency, numbers);
        map.put(key, new int[]{frequency+1, values[1]});
        return values[1];
    }

    public void put(int key, int value) {
        if (!map.containsKey(key) && map.size() == capacity) {
            LinkedHashSet<Integer> numbers = frequencyOfUse.get(minFrequency);
            int keyToBeRemoved = numbers.getFirst();
            numbers.remove(keyToBeRemoved);
            if (numbers.isEmpty()) {
                frequencyOfUse.remove(minFrequency);
                minFrequency = findNextFrequency(frequencyOfUse, minFrequency+1);
            } else
                frequencyOfUse.put(minFrequency, numbers);
            map.remove(keyToBeRemoved);
        }
        int frequency = map.getOrDefault(key, new int[]{0,0})[0];
        if (frequency == 0) {
            map.put(key, new int[]{1, value});
            minFrequency = Math.min(minFrequency, 1);
            LinkedHashSet<Integer> numbers = frequencyOfUse.getOrDefault(1, new LinkedHashSet<>());
            numbers.addLast(key);
            frequencyOfUse.put(1, numbers);
        } else {
            LinkedHashSet<Integer> numbersInNextFrequency = frequencyOfUse.getOrDefault(frequency+1, new LinkedHashSet<>());
            numbersInNextFrequency.addLast(key);
            frequencyOfUse.put(frequency+1, numbersInNextFrequency);
            LinkedHashSet<Integer> numbers = frequencyOfUse.get(frequency);
            numbers.remove(key);
            if (numbers.isEmpty()) {
                frequencyOfUse.remove(frequency);
                if (minFrequency == frequency)
                    minFrequency = findNextFrequency(frequencyOfUse, minFrequency+1);
            }
            else
                frequencyOfUse.put(frequency, numbers);
            map.put(key, new int[]{frequency+1, value});
            minFrequency = Math.min(minFrequency, frequency+1);
        }
    }

    private int findNextFrequency(HashMap<Integer, LinkedHashSet<Integer>> frequencyOfUse, int minFrequency) {
        if (frequencyOfUse.isEmpty())
            return 0;
        while (frequencyOfUse.get(minFrequency) == null)
            minFrequency++;
        return minFrequency;
    }

    public static void main(String[] args) {
        LFUCache L = new LFUCache(1);
        L.put(2,1);
        System.out.println(L.get(2));
        L.put(3,2);
        System.out.println(L.get(2));
        System.out.println(L.get(3));
    }
}
