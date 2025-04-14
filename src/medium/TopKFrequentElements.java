package medium;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class TopKFrequentElements {
    public int[] topKFrequent(int[] nums, int k) {
        int[] hash = new int[20001];
        Arrays.fill(hash, 0);
        int size = nums.length;
        for (int i = 0; i < size; i++)
            hash[nums[i] + 10000]++;
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(o -> -o[1]));
        for (int i = 0; i < 20000; i++) {
            if (hash[i] > 0)
                pq.add(new int[]{i - 10000, hash[i]});
        }
        int[] result = new int[k];
        int index = 0;
        while(!pq.isEmpty() && k-- != 0)
            result[index++] = pq.poll()[0];
        return result;
    }

    public static void main(String[] args) {
        TopKFrequentElements T = new TopKFrequentElements();
        System.out.println(Arrays.toString(T.topKFrequent(new int[]{1, 1, 1, 2, 2, 3}, 2)));
        System.out.println(Arrays.toString(T.topKFrequent(new int[]{1}, 1)));
    }
}
