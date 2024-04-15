package medium;

import java.util.HashMap;

public class ContiguousArray {
    public int findMaxLength(int[] nums) {
        int N = nums.length;
        HashMap<Integer,Integer> map = new HashMap<>();
        map.put(0, -1);
        int sum = 0;
        int maxLength = 0;
        for (int i = 0; i < N; i++) {
            sum += (nums[i] == 1 ? 1 : -1);
            if (map.containsKey(sum)) {
                maxLength = Math.max(maxLength, i - map.get(sum));
            } else {
                map.put(sum, i);
            }
        }
        return maxLength;
    }
}
