package medium;

import java.util.HashMap;
import java.util.HashSet;

public class CountCompleteSubarraysInAnArray {
    public int countCompleteSubarrays(int[] nums) {
        int size = nums.length;
        HashSet<Integer> uniqueNumbers = new HashSet<>();
        for (int i = 0; i < size; i++)
            uniqueNumbers.add(nums[i]);
        int uniqueCount = uniqueNumbers.size();
        HashMap<Integer,Integer> count = new HashMap<>();
        int start = 0, end = 0;
        int result = 0;
        while (start < size || end < size) {
            while (end < size && count.size() < uniqueCount) {
                count.put(nums[end], count.getOrDefault(nums[end], 0) + 1);
                end++;
            }
            if (count.size() == uniqueCount)
                result += (size - end + 1);
            if (start < size && start <= end) {
                count.put(nums[start], count.get(nums[start]) - 1);
                if (count.get(nums[start]) == 0)
                    count.remove(nums[start]);
                start++;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        CountCompleteSubarraysInAnArray C = new CountCompleteSubarraysInAnArray();
        System.out.println(C.countCompleteSubarrays(new int[]{459,459,962,1579,1435,756,1872,1597}));
        System.out.println(C.countCompleteSubarrays(new int[]{1,3,1,2,2}));
        System.out.println(C.countCompleteSubarrays(new int[]{5,5,5,5}));
    }
}
