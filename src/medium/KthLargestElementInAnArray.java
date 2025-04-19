package medium;

import java.util.*;

public class KthLargestElementInAnArray {

    public int findKthLargest(int[] nums, int k) {
        int size = nums.length;
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < size; i++)
            numbers.add(nums[i]);
        return quickSelect(numbers, k);
    }

    private int quickSelect(List<Integer> numbers, int k) {
        int size = numbers.size();
        int pivot = numbers.get(new Random().nextInt(size));
        List<Integer> left = new ArrayList<>();
        List<Integer> mid = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int number = numbers.get(i);
            if (number < pivot)
                left.add(number);
            else if (number > pivot)
                right.add(number);
            else
                mid.add(number);
        }
        if (right.size() >= k)
            return quickSelect(right, k);
        if (right.size() + mid.size() < k)
            return quickSelect(left, k - right.size() - mid.size());
        return pivot;
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
