package hard;

import java.util.Deque;
import java.util.LinkedList;

public class SlidingWindowMaximum {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int N = nums.length;
        int[] result= new int[N-k+1];
        int index = 0;
        Deque<Integer> deque = new LinkedList<>();
        for (int i = 0; i < N; i++) {
            if (i >= k) {
                result[index++] = nums[deque.getFirst()];
                if (i - deque.getFirst() >= k)
                    deque.removeFirst();
            }
            while (!deque.isEmpty() && nums[i] > nums[deque.getLast()]) {
                deque.removeLast();
            }
            deque.add(i);
        }
        result[index] = nums[deque.getFirst()];
        return result;
    }

    public static void main(String[] args) {
        SlidingWindowMaximum S = new SlidingWindowMaximum();
        System.out.println(S.maxSlidingWindow(new int[]{1,3,-1,-3,5,3,6,7}, 3));
        System.out.println(S.maxSlidingWindow(new int[]{1}, 1));
    }
}
