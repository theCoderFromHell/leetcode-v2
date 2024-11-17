package medium;

public class ShortestSubarrayToBeRemovedToMakeArraySorted {
    public int findLengthOfShortestSubarray(int[] arr) {
        int N = arr.length;
        int right = N-1;
        while (right > 0 && arr[right-1] <= arr[right])
            right--;
        int left = 0;
        int result = right;
        while (left < right && (left == 0 || arr[left-1] <= arr[left])) {
            while (right < N && arr[left] > arr[right])
                right++;
            result = Math.min(result, right - left - 1);
            left++;
        }
        return result;
    }

    public static void main(String[] args) {
        ShortestSubarrayToBeRemovedToMakeArraySorted S = new ShortestSubarrayToBeRemovedToMakeArraySorted();
        System.out.println(S.findLengthOfShortestSubarray(new int[]{1,2,3,10,4,2,3,5}));
        System.out.println(S.findLengthOfShortestSubarray(new int[]{5,4,3,2,1}));
        System.out.println(S.findLengthOfShortestSubarray(new int[]{1,2,3}));
    }
}
