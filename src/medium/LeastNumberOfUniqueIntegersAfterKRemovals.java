package medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LeastNumberOfUniqueIntegersAfterKRemovals {
    public int findLeastNumOfUniqueInts(int[] arr, int k) {
        int size = arr.length;
        HashMap<Integer,Integer> count = new HashMap<>();
        for (int i = 0; i < size; i++)
            count.put(arr[i], count.getOrDefault(arr[i], 0) + 1);
        HashMap<Integer, List<Integer>> frequency = new HashMap<>();
        for (int num : count.keySet()) {
            List<Integer> numbersForFrequency = frequency.getOrDefault(count.get(num), new ArrayList<>());
            numbersForFrequency.add(num);
            frequency.put(count.get(num), numbersForFrequency);
        }
        for (int freq : frequency.keySet()) {
            if (k < freq)
                break;
            List<Integer> numbersForFrequency = frequency.get(freq);
            for (int num : numbersForFrequency) {
                if (k >= freq) {
                    count.remove(num);
                    k -= freq;
                    if (k <= 0)
                        break;
                }
            }
            if (k <= 0)
                break;
        }
        return count.size();
    }

    public static void main(String[] args) {
        LeastNumberOfUniqueIntegersAfterKRemovals L = new LeastNumberOfUniqueIntegersAfterKRemovals();
        System.out.println(L.findLeastNumOfUniqueInts(new int[]{5,5,4}, 1));
        System.out.println(L.findLeastNumOfUniqueInts(new int[]{4,3,1,1,3,3,2}, 3));
    }
}
