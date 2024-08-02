package medium;

import java.util.HashMap;

public class LongestArithmeticSubsequenceOfGivenDifference {
    public int longestSubsequence(int[] arr, int difference) {
        int N = arr.length;
        HashMap<Integer,Integer> map = new HashMap<>();
        int maxLength = 1;

        for (int i = 0; i < N; i++) {
            int current = 1;
            if (map.containsKey(arr[i] - difference)) {
                int prev = map.get(arr[i] - difference);
                if (prev + 1 > current) {
                    current = prev + 1;
                    maxLength = Math.max(maxLength, current);
                }
            }
            map.put(arr[i], current);
        }
        return maxLength;
    }

    public static void main(String[] args) {
        LongestArithmeticSubsequenceOfGivenDifference L = new LongestArithmeticSubsequenceOfGivenDifference();
        System.out.println(L.longestSubsequence(new int[]{3,0,-3,4,-4,7,6}, 3));
    }
}
