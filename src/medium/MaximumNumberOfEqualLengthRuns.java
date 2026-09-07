package medium;

// https://leetcode.com/problems/maximum-number-of-equal-length-runs/
public class MaximumNumberOfEqualLengthRuns {
    public int maxSameLengthRuns(String s) {
        char[] sChars = s.toCharArray();
        int size = sChars.length;
        int index = 0;
        int[] frequency = new int[size + 1];
        int maxCount = 0;
        while (index < size) {
            int curr = 1;
            while (index + 1 < size && sChars[index] == sChars[index + 1]) {
                index++;
                curr++;
            }
            frequency[curr]++;
            maxCount = Math.max(maxCount, frequency[curr]);
            index++;
        }
        return maxCount;
    }

    /*
     * Revision Note — Maximum Number of Equal Length Runs (Medium)
     * Pattern: Run-length encoding + frequency counting
     * Key Insight: Scan runs in one pass with an inner while; store each run's length in a
     *              frequency array indexed by length. The answer is the max value in that array
     *              (the highest count of runs sharing the same length).
     * Gotchas:
     *   - frequency array must be size+1 (not size) to hold a run equal to the whole string.
     *   - maxCount tracks the max *frequency* (a count), not the run length — keep the units consistent.
     *   - Update maxCount inline right after frequency[curr]++ to avoid a redundant second loop.
     *   - Use toCharArray() once rather than repeated charAt() calls — avoids per-call bounds checks.
     *   - Inner while checks index+1 < size before comparing chars to avoid AIOOB at end of string.
     */
    public static void main(String[] args) {
        MaximumNumberOfEqualLengthRuns M = new MaximumNumberOfEqualLengthRuns();

        // Test 1: three runs all length 1 — max frequency is 3
        System.out.println("Test 1: " + M.maxSameLengthRuns("abc") + " (Expected: 3)");

        // Test 2: two runs of length 2, one run of length 3 — max frequency is 2
        System.out.println("Test 2: " + M.maxSameLengthRuns("aaabbbcc") + " (Expected: 2)");

        // Test 3: all runs the same length — three runs of length 2
        System.out.println("Test 3: " + M.maxSameLengthRuns("aabbcc") + " (Expected: 3)");

        // Test 4: single character — one run of length 1
        System.out.println("Test 4: " + M.maxSameLengthRuns("a") + " (Expected: 1)");

        // Test 5: whole string is one run — frequency[size]=1, returned correctly
        System.out.println("Test 5: " + M.maxSameLengthRuns("aaaa") + " (Expected: 1)");

        // Test 6: empty string
        System.out.println("Test 6: " + M.maxSameLengthRuns("") + " (Expected: 0)");
    }
}
