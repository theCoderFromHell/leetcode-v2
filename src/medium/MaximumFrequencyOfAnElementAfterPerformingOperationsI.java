package medium;

import java.util.HashMap;
import java.util.TreeSet;

public class MaximumFrequencyOfAnElementAfterPerformingOperationsI {
    public int maxFrequency(int[] nums, int k, int numOperations) {
        int N = nums.length;
        HashMap<Integer,Integer> existingFrequency = new HashMap<>();
        HashMap<Integer,Integer> transformedFrequency = new HashMap<>();
        TreeSet<Integer> points = new TreeSet<>();
        for (int i = 0; i < N; i++) {
            points.add(nums[i]);
            points.add(nums[i] - k);
            points.add(nums[i] + k + 1);

            existingFrequency.put(nums[i], existingFrequency.getOrDefault(nums[i], 0) + 1);
            transformedFrequency.put(nums[i] - k, transformedFrequency.getOrDefault(nums[i] - k, 0) + 1);
            transformedFrequency.put(nums[i] + k + 1, transformedFrequency.getOrDefault(nums[i] + k + 1, 0) - 1);
        }
        int countThroughTransformation = 0;
        int countThroughExistingValues = 0;
        int maxFrequency = 0;
        for (int point : points) {
            countThroughTransformation += transformedFrequency.getOrDefault(point, 0);
            countThroughExistingValues = existingFrequency.getOrDefault(point, 0);
            maxFrequency = Math.max(maxFrequency, countThroughExistingValues + Math.min(numOperations, countThroughTransformation - countThroughExistingValues));
        }
        return maxFrequency;
    }
}
