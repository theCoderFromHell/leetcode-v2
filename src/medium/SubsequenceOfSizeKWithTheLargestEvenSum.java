package medium;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class SubsequenceOfSizeKWithTheLargestEvenSum {
    public long largestEvenSum(int[] nums, int k) {
        int size = nums.length;
        Arrays.sort(nums);
        long sum = 0;
        int minEven = Integer.MAX_VALUE, minOdd = Integer.MAX_VALUE;
        for (int i = size-1; i >= size-k; i--) {
            sum += nums[i];
            if (nums[i] % 2 == 0)
                minEven = Math.min(minEven, nums[i]);
            else
                minOdd = Math.min(minOdd, nums[i]);
        }
        if (sum % 2 == 0)
            return sum;
        int maxEven = Integer.MIN_VALUE, maxOdd = Integer.MIN_VALUE;
        for (int i = 0; i < size-k; i++) {
            if (nums[i] % 2 == 0)
                maxEven = Math.max(maxEven, nums[i]);
            else
                maxOdd = Math.max(maxOdd, nums[i]);
        }
        long option1 = -1;
        if (minEven != Integer.MAX_VALUE && maxOdd != Integer.MIN_VALUE)
            option1 = sum - minEven + maxOdd;
        long option2 = -1;
        if (minOdd != Integer.MAX_VALUE && maxEven != Integer.MIN_VALUE)
            option2 = sum - minOdd + maxEven;
        if (option1 % 2 == 0 && option1 >= option2)
            return option1;
        else if (option2 % 2 == 0 && option2 >= option1)
            return option2;
        return -1;
    }
    public long largestEvenSumV2(int[] nums, int k) {
        int size = nums.length;
        PriorityQueue<Integer> evens = new PriorityQueue<>(Comparator.comparingInt(o -> -o));
        PriorityQueue<Integer> odds = new PriorityQueue<>(Comparator.comparingInt(o -> -o));
        for (int i = 0; i < size; i++) {
            if (nums[i] % 2 == 0)
                evens.add(nums[i]);
            else
                odds.add(nums[i]);
        }
        int count = 0;
        long sum = 0;
        int smallestEven = Integer.MAX_VALUE, smallestOdd = Integer.MAX_VALUE;
        while (count < k && (!evens.isEmpty() || !odds.isEmpty())) {
            int even = !evens.isEmpty() ? evens.peek() : -1;
            int odd = !odds.isEmpty() ? odds.peek() : -1;
            if (even > odd) {
                int value = evens.poll();
                smallestEven = Math.min(smallestEven, value);
                sum += value;
                count++;
            } else {
                int value = odds.poll();
                smallestOdd = Math.min(smallestOdd, value);
                sum += value;
                count++;
            }
        }
        if (sum % 2 == 0)
            return sum;
        else {
            long option1 = -1;
            if (!odds.isEmpty() && smallestEven != Integer.MAX_VALUE)
                option1 = sum - smallestEven + odds.poll();
            long option2 = -1;
            if (!evens.isEmpty() && smallestOdd != Integer.MAX_VALUE)
                option2 = sum - smallestOdd + evens.poll();
            if (option1 % 2 == 0 && option1 >= option2)
                return option1;
            else if (option2 % 2 == 0 && option2 >= option1)
                return option2;
            return -1;
        }
    }

    public static void main(String[] args) {
        SubsequenceOfSizeKWithTheLargestEvenSum S = new SubsequenceOfSizeKWithTheLargestEvenSum();
        System.out.println(S.largestEvenSum(new int[]{3,4,3,2,1}, 2));
        System.out.println(S.largestEvenSum(new int[]{1,5,5,5,4}, 4));
        System.out.println(S.largestEvenSum(new int[]{4,1,5,3,1}, 3));
        System.out.println(S.largestEvenSum(new int[]{4,6,2}, 3));
        System.out.println(S.largestEvenSum(new int[]{1,3,5}, 1));
    }
}
