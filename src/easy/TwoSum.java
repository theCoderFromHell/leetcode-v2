package easy;
import java.util.*;

public class TwoSum {

    public static int[] twoSum(int[] nums, int target) {
        int length = nums.length;
        Map<Integer, Integer> map = new HashMap<>();
        for(int i=0; i<length; i++) {
            int rest = target - nums[i];
            if(map.containsKey(rest) && i != map.get(rest)) {
                return new int[] {i, map.get(rest)};
            }
            map.put(nums[i], i);
        }
        return null;
    }

    public static void main(String[] args) {
        int[] nums = {2,7,11,15};
        int target = 22;
        System.out.println(Arrays.toString(twoSum(nums, target)));

    }
}
