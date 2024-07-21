package easy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IntersectionOfTwoArraysII {
    public int[] intersect(int[] nums1, int[] nums2) {
        int M = nums1.length;
        int N = nums2.length;
        HashMap<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < M; i++) {
            map.put(nums1[i], map.getOrDefault(nums1[i], 0) + 1);
        }

        List<Integer> temp =  new ArrayList<>();
        for (int i = 0; i < N; i++) {
            if (map.containsKey(nums2[i])) {
                temp.add(nums2[i]);
                if (map.get(nums2[i]) == 1)
                    map.remove(nums2[i]);
                else
                    map.put(nums2[i], map.get(nums2[i]) - 1);
            }
        }
        int i = 0;
        int[] result = new int[temp.size()];
        for (int num : temp)
            result[i++] = num;
        return result;
    }
}
