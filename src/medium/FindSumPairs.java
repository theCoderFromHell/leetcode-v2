package medium;

import java.util.HashMap;

public class FindSumPairs {
    int[] nums1;
    int[] nums2;
    HashMap<Integer,Integer> nums1Count;
    HashMap<Integer,Integer> nums2Count;
    public FindSumPairs(int[] nums1, int[] nums2) {
        this.nums1 = nums1;
        this.nums2 = nums2;
        this.nums1Count = new HashMap<>();
        this.nums2Count = new HashMap<>();
        for (int value : nums1)
            nums1Count.put(value, nums1Count.getOrDefault(value, 0) + 1);
        for (int value : nums2)
            nums2Count.put(value, nums2Count.getOrDefault(value, 0) + 1);
    }

    public void add(int index, int val) {
        int original = nums2[index];
        nums2Count.put(original, nums2Count.getOrDefault(original, 0) - 1);
        nums2[index] += val;
        nums2Count.put(nums2[index], nums2Count.getOrDefault(nums2[index], 0) + 1);
    }

    public int count(int tot) {
        int count = 0;
        int M = nums1.length;
        for (int value : nums1Count.keySet()) {
            Integer count1 = nums1Count.get(value);
            Integer count2 = nums2Count.get(tot - value);
            if (count1 != null && count2 != null)
                count += (count1 * count2);
        }
        return count;
    }

    public static void main(String[] args) {
        FindSumPairs F = new FindSumPairs(new int[]{1, 1, 2, 2, 2, 3}, new int[]{1, 4, 5, 2, 5, 4});
        System.out.println(F.count(7));
        F.add(3,2);
        System.out.println(F.count(8));
        System.out.println(F.count(4));
        F.add(0,1);
        F.add(1,1);
        System.out.println(F.count(7));
    }
}
