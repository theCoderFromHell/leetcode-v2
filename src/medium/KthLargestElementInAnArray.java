package medium;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class KthLargestElementInAnArray {

    public int findKthLargest(int[] nums, int k) {
        if(null == nums || nums.length == 0)
            return -1;

        return quickSelect(nums, nums.length - k, 0, nums.length - 1);
    }

    private int quickSelect(int[] nums, int k, int start, int end) {
        if (end < start)
            return -1;
        int size = end - start + 1;
        if (size == 1)
            return nums[start];
        int random = (int) ((Math.random() * (end - start)) + start);
        swap(nums, random, end);
        int pivot = nums[end];
        int p = start;
        for (int i = start; i < end; i++) {
            if (nums[i] <= pivot) {
                swap(nums, i, p);
                p++;
            }
        }
        swap(nums, end, p);
        if (p == k)
            return nums[p];
        else if (p < k)
            return quickSelect(nums, k,  p+1, end);
        else
            return quickSelect(nums, k, start, p-1);
    }

    public static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public int findKthLargestV2(int[] nums, int k) {
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

    public static void main(String[] args) {
        KthLargestElementInAnArray K = new KthLargestElementInAnArray();
        System.out.println(K.findKthLargest(new int[]{3,2,1,5,6,4}, 2));
        System.out.println(K.findKthLargest(new int[]{3,2,3,1,2,4,5,5,6}, 4));
    }
}
