package medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BinarySubarraysWithSum {
    public int numSubarraysWithSum(int[] nums, int goal) {
        int size = nums.length;
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        List<Integer> initial = new ArrayList<>();
        initial.add(-1);
        map.put(0, initial);
        int currSum = 0;
        int count = 0;
        for (int i = 0; i < size; i++) {
            currSum += nums[i];
            if (currSum >= goal) {
                List<Integer> targetIndices = map.get(currSum - goal);
                if (targetIndices != null && !targetIndices.isEmpty())
                    count += targetIndices.size();
            }
            List<Integer> indices = map.getOrDefault(currSum, new ArrayList<>());
            indices.add(i);
            map.put(currSum, indices);
        }
        return count;
    }

    public static void main(String[] args) {
        BinarySubarraysWithSum B = new BinarySubarraysWithSum();
        System.out.println(B.numSubarraysWithSum(new int[]{1,0,1,0,1}, 2));
        System.out.println(B.numSubarraysWithSum(new int[]{0,0,0,0,0}, 0));
    }
}
