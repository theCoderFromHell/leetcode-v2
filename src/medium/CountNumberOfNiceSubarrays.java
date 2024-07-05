package medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CountNumberOfNiceSubarrays {
    public int numberOfSubarrays(int[] nums, int k) {
        int N = nums.length;
        HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
        ArrayList<Integer> dummy = new ArrayList<>();
        dummy.add(-1);
        map.put(0, dummy);
        int count = 0;
        int result = 0;
        for (int i = 0; i < N; i++) {
            count += ((nums[i] % 2 == 1) ? 1 : 0);
            if (map.containsKey(count - k)) {
                List<Integer> prev = map.get(count - k);
                result += prev.size();
            }
            ArrayList<Integer> instances = map.getOrDefault(count, new ArrayList<>());
            instances.add(i);
            map.put(count, instances);
        }
        return result;
    }

    public static void main(String[] args) {
        CountNumberOfNiceSubarrays c = new CountNumberOfNiceSubarrays();
        System.out.println(c.numberOfSubarrays(new int[]{1,1,2,1,1}, 3));
        System.out.println(c.numberOfSubarrays(new int[]{2,4,6}, 1));
        System.out.println(c.numberOfSubarrays(new int[]{2,2,2,1,2,2,1,2,2,2}, 2));
    }
}
