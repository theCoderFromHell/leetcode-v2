package medium;

import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

public class MaximumGoodSubarraySum {
    public long maximumSubarraySum(int[] nums, int k) {
        int N = nums.length;
        HashMap<Integer,Long> prefix = new HashMap<>();
        HashMap<Integer, PriorityQueue<Integer>> hash = new HashMap<>();
        prefix.put(0,0L);
        for (int i = 0; i < N; i++) {
            prefix.put(i + 1, prefix.get(i) + nums[i]);
            PriorityQueue<Integer> indices = hash.getOrDefault(nums[i], new PriorityQueue<>((a,b) -> Long.compare(prefix.get(b),prefix.get(a))));
            indices.add(i);
            hash.put(nums[i], indices);
        }
        long maxSum = Long.MIN_VALUE;
        boolean found = false;
        for (int i = 0; i < N; i++) {
            if (hash.containsKey(nums[i] + k)) {
                found = true;
                PriorityQueue<Integer> indices = hash.get(nums[i] + k);
                while (!indices.isEmpty() && indices.peek() < i)
                    indices.poll();
                if (!indices.isEmpty()) {
                    long currSum = prefix.get(indices.peek() + 1) - prefix.get(i);
                    maxSum = Math.max(maxSum, currSum);
                }
            }
            if (hash.containsKey(nums[i] - k)) {
                found = true;
                PriorityQueue<Integer> indices = hash.get(nums[i] - k);
                while (!indices.isEmpty() && indices.peek() < i)
                    indices.poll();
                if (!indices.isEmpty()) {
                    long currSum = prefix.get(indices.peek() + 1) - prefix.get(i);
                    maxSum = Math.max(maxSum, currSum);
                }
            }
        }
        return !found ? 0 : maxSum;
    }

    public static void main(String[] args) {
        MaximumGoodSubarraySum M = new MaximumGoodSubarraySum();
        System.out.println(M.maximumSubarraySum(new int[]{1,2,3,4,5,6}, 1));
        System.out.println(M.maximumSubarraySum(new int[]{-1,3,2,4,5}, 3));
        System.out.println(M.maximumSubarraySum(new int[]{-1,-2,-3,-4}, 2));
        System.out.println(M.maximumSubarraySum(new int[]{1,5}, 2));

    }
}
