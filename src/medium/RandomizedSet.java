package medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomizedSet {
    private HashMap<Integer, Integer> valuesIndex;
    private List<Integer> values;
    private Integer N;
    public RandomizedSet() {
        valuesIndex = new HashMap<>();
        values = new ArrayList<>();
        N = 0;
    }

    public boolean insert(int val) {
        if (valuesIndex.containsKey(val))
            return false;
        if (values.size() > N)
            values.add(N, val);
        else
            values.add(val);
        valuesIndex.put(val, N);
        N++;
        return true;
    }

    public boolean remove(int val) {
        if (!valuesIndex.containsKey(val))
            return false;
        Integer index = valuesIndex.get(val);
        valuesIndex.remove(val);
        if (index != N-1) {
            values = swap(values, index, N - 1);
            valuesIndex.put(values.get(index), index);
        }
        N--;
        return true;
    }

    public int getRandom() {
        int random = ThreadLocalRandom.current().nextInt(0, N);
        return values.get(random);
    }

    private List<Integer> swap(List<Integer> nums, int a, int b) {
        int temp = nums.get(a);
        nums.set(a, nums.get(b));
        nums.set(b, temp);
        return nums;
    }

    public static void main(String[] args) {
        RandomizedSet randomizedSet = new RandomizedSet();
        System.out.println(randomizedSet.insert(0));
        System.out.println(randomizedSet.insert(1));
        System.out.println(randomizedSet.remove(0));
        System.out.println(randomizedSet.insert(2));
        System.out.println(randomizedSet.remove(1));
        System.out.println(randomizedSet.getRandom());

    }
}
