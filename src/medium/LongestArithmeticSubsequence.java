package medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LongestArithmeticSubsequence {
    public int longestArithSeqLength(int[] nums) {
        int N = nums.length;
        List<HashMap<Integer,Integer>> commonDiffs = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            HashMap<Integer,Integer> cds = new HashMap<>();
            cds.put(0, 1);
            commonDiffs.add(cds);
        }
        int maxLength = 1;
        for (int i = 1; i < N; i++) {
            for (int j = 0; j < i; j++) {
                int diff = nums[i] - nums[j];
                HashMap<Integer,Integer> cds = commonDiffs.get(j);
                if (cds.containsKey(diff)) {
                    commonDiffs.get(i).put(diff,
                            Math.max(commonDiffs.get(i).getOrDefault(diff, 0), cds.get(diff) + 1));
                    maxLength = Math.max(maxLength, cds.get(diff) + 1);
                } else {
                    commonDiffs.get(i).put(diff, 2);
                    maxLength = Math.max(maxLength, 2);
                }

            }
        }
        return maxLength;
    }
}
