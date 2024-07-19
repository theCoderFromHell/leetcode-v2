package medium;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class KthLargestElementInAnArray {
    public int findKthLargest(int[] nums, int k) {
        if(null == nums || nums.length == 0)
            return -1;
        Arrays.sort(nums);
        return nums[nums.length - k];
    }

    public int findKthLargestV1(int[] nums, int k) {
        if(null == nums || nums.length == 0)
            return -1;
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
        for (int num : nums)
            pq.add(num);
        int count = 1;
        while (count < k) {
            pq.poll();
            count++;
        }
        return pq.poll();
    }
}
