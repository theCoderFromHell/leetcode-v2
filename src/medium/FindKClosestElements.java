package medium;

import java.util.*;

public class FindKClosestElements {
    public static List<Integer> findClosestElementsV2(int[] arr, int k, int x) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(Comparator.naturalOrder());
        for (int number : arr) {
            if (number <= x)
                maxHeap.add(number);
            else
                minHeap.add(number);
        }
        int i = 0;
        List<Integer> result = new ArrayList<>();
        while (i < k) {
            Integer left = null, right = null;
            if (!maxHeap.isEmpty())
                left = Math.abs(x - maxHeap.peek());
            if (!minHeap.isEmpty())
                right = Math.abs(x - minHeap.peek());
            if (left != null && (right == null || left <= right))
                result.add(maxHeap.poll());
            else if (right != null)
                result.add(minHeap.poll());
            i++;
        }
        result.sort(Comparator.naturalOrder());
        return result;
    }

    public static List<Integer> findClosestElementsV3(int[] arr, int k, int x) {
        int N = arr.length;
        int idx = findClosestNumber(arr, N, x);
        if (idx == -1 || idx == 0) {
            return Arrays.stream(Arrays.copyOfRange(arr, 0, k)).boxed().toList();
        }
        if (idx == N || idx == N-1) {
            List<Integer> result = new ArrayList<>(Arrays.stream(Arrays.copyOfRange(arr, N - k, N)).boxed().toList());
            Collections.sort(result);
            return result;
        }

        List<Integer> result = new ArrayList<>();
        int left = idx-1;
        int right = idx+1;
        result.add(arr[idx]);
        k--;
        while (k-- > 0) {
            int l = (left < 0) ? Integer.MAX_VALUE : Math.abs(x - arr[left]);
            int r = (right >= N) ? Integer.MAX_VALUE : Math.abs(x - arr[right]);
            if (l <= r) {
                result.add(arr[left]);
                left--;
            } else {
                result.add(arr[right]);
                right++;
            }
        }
        result.sort(Comparator.naturalOrder());
        return result;
    }

    private static int findClosestNumber(int[] arr, int N, int x) {
        if (x < arr[0])
            return -1;
        if (x > arr[N-1])
            return N;
        int minDiff = Integer.MAX_VALUE;
        int idx = -1;
        for (int i = 0; i < N; i++) {
            if (Math.abs(arr[i] - x) < minDiff) {
                idx = i;
                minDiff = Math.abs(arr[i] - x);
            }
        }
        return idx;
    }
    public static List<Integer> findClosestElements(int[] arr, int k, int x) {
        int N = arr.length;
        int left = 0, right = N-1;
        while (right - left + 1 > k) {
            if (Math.abs(x - arr[left]) > Math.abs(x - arr[right])) {
                left++;
            } else
                right--;
        }
        List<Integer> result = new ArrayList<>();
        for (int i = left; i <= right ; i++)
            result.add(arr[i]);
        return result;
    }

    public static void main(String[] args) {
        System.out.println("******V1*******");
        System.out.println(findClosestElements(new int[]{1,2,3,4,5}, 4, 3));
        System.out.println(findClosestElements(new int[]{1,2,3,4,5}, 4, -1));
        System.out.println("******V2*******");
        System.out.println(findClosestElementsV2(new int[]{1,2,3,4,5}, 4, 3));
        System.out.println(findClosestElementsV2(new int[]{1,2,3,4,5}, 4, -1));
        System.out.println("******V3*******");
        System.out.println(findClosestElementsV3(new int[]{1,2,3,4,5}, 4, 3));
        System.out.println(findClosestElementsV3(new int[]{1,2,3,4,5}, 4, -1));

    }
}
