package medium;

import java.util.Arrays;
import java.util.Random;

// https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/
public class TwoSumIIInputArrayIsSorted {
    public int[] twoSum(int[] numbers, int target) {
        int length = numbers.length;
        int start = 0, end = length-1;
        int sum;
        while(start < end) {
            sum = numbers[start] + numbers[end];
            if(sum == target) {
                return new int[]{start+1, end+1};
            }
            if(sum < target) {
                start++;
            } else end--;
        }
        return new int[2];
    }

    /*
     * Revision Note - Two Sum II - Input Array Is Sorted (Medium)
     *
     * Pattern: Converge two pointers from both ends; sortedness decides which one moves
     *
     * Key Insight: At any moment the window [start, end] holds every pair still worth testing.
     * If the sum is TOO SMALL, no pair using `start` can ever work - `end` is already the
     * largest available partner, so every other partner for `start` is smaller still. Discard
     * `start` entirely with start++. Symmetrically, a sum that is TOO LARGE eliminates `end`.
     * Each step deletes a whole row or column of the pair matrix, which is why O(n) suffices
     * where the brute force needs O(n^2).
     *
     * Gotchas:
     * - RETURN 1-INDEXED. The problem asks for positions, not indices, so it is start+1 and
     *   end+1. Easiest possible mistake and the examples make it obvious only if you read them
     * - `while (start < end)`, strictly. `<=` would let both pointers land on the same element
     *   and return a "pair" that reuses one number, which the problem forbids
     * - Exactly one solution is GUARANTEED, so the `return new int[2]` fallback is unreachable.
     *   Java needs it; do not read it as handling a real case
     * - Duplicates are fine and need no special handling - [1,1] with target 2 returns [1,2]
     * - Values span -1000..1000 and n <= 3e4, so the sum cannot overflow an int. Worth checking
     *   rather than assuming, since the sibling problems (e.g. 1 Two Sum) have wider ranges
     * - The constant-space requirement is what rules out the HashMap solution of problem 1.
     *   Sortedness is the compensation: it buys the pointer-elimination argument
     *
     * Complexity: O(n) time - start only increases, end only decreases, so they meet after at
     * most n steps. O(1) space, which the problem explicitly demands.
     *
     * Template:
     *   start = 0; end = n-1
     *   while start < end:
     *     sum = numbers[start] + numbers[end]
     *     if sum == target: return {start+1, end+1}
     *     if sum < target:  start++        // start can never pair with anything smaller
     *     else:             end--          // end can never pair with anything larger
     *
     * The elimination argument is the transferable part: it generalises to 15/16/18 (fix one
     * element, converge on the rest), to counting variants like 611 and 923 where one pointer
     * move settles many pairs at once, and to 719 where the converging scan becomes a counting
     * subroutine inside a binary search on the answer.
     */
    // O(n^2) reference: find any valid pair by brute force. Used only to cross-check.
    private static int[] bruteForce(int[] numbers, int target) {
        for (int i = 0; i < numbers.length; i++)
            for (int j = i + 1; j < numbers.length; j++)
                if (numbers[i] + numbers[j] == target) return new int[]{i + 1, j + 1};
        return new int[2];
    }

    /*
     * The problem guarantees exactly one solution, but a random array may admit several. So the
     * check is a PROPERTY check rather than an index comparison: the returned positions must be
     * 1-based, in range, strictly increasing, and actually sum to the target.
     */
    private static boolean validAnswer(int[] numbers, int target, int[] got) {
        if (got == null || got.length != 2) return false;
        int i = got[0], j = got[1];
        if (i < 1 || j > numbers.length || i >= j) return false;
        return numbers[i - 1] + numbers[j - 1] == target;
    }

    public static void main(String[] args) {
        TwoSumIIInputArrayIsSorted T = new TwoSumIIInputArrayIsSorted();

        System.out.println("Test 1: " + Arrays.toString(T.twoSum(new int[]{2,7,11,15}, 9)) + " (Expected: [1, 2])");
        System.out.println("Test 2: " + Arrays.toString(T.twoSum(new int[]{2,3,4}, 6)) + " (Expected: [1, 3])");
        System.out.println("Test 3: " + Arrays.toString(T.twoSum(new int[]{-1,0}, -1)) + " (Expected: [1, 2])");      // n=2, the minimum
        System.out.println("Test 4: " + Arrays.toString(T.twoSum(new int[]{1,1}, 2)) + " (Expected: [1, 2])");        // duplicates
        System.out.println("Test 5: " + Arrays.toString(T.twoSum(new int[]{-3,-1,0,2,4}, 1)) + " (Expected: [1, 5])");// negatives, answer at both ends
        System.out.println("Test 6: " + Arrays.toString(T.twoSum(new int[]{1,2,3,4,4,9,56,90}, 8)) + " (Expected: [4, 5])"); // answer adjacent in the middle
        System.out.println("Test 7: " + Arrays.toString(T.twoSum(new int[]{-1000,-999,999,1000}, 0)) + " (Expected: [1, 4])"); // value extremes
        System.out.println("Test 8: " + Arrays.toString(T.twoSum(new int[]{0,0,3,4}, 0)) + " (Expected: [1, 2])");    // zeros, answer at the very start

        // property cross-check: random sorted arrays, target taken from a real pair
        boolean ok = true;
        String firstBad = "";
        Random rnd = new Random(97);
        for (int t = 0; t < 800; t++) {
            int n = 2 + rnd.nextInt(14);
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = rnd.nextInt(41) - 20;
            Arrays.sort(a);
            int i = rnd.nextInt(n - 1), j = i + 1 + rnd.nextInt(n - i - 1);
            int target = a[i] + a[j];
            int[] got = T.twoSum(a.clone(), target);
            if (!validAnswer(a, target, got)) {
                ok = false;
                if (firstBad.isEmpty())
                    firstBad = Arrays.toString(a) + " target " + target + " got " + Arrays.toString(got);
            }
        }
        System.out.println("Test 9: " + ok + " (Expected: true)  - 800 random sorted arrays, answer verified by property"
                + (ok ? "" : ", first bad: " + firstBad));

        // agreement with the O(n^2) reference where the solution is unique by construction
        boolean agree = true;
        for (int t = 0; t < 400; t++) {
            int n = 2 + rnd.nextInt(10);
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = i * 7 + 1;          // strictly increasing, distinct gaps
            int i = rnd.nextInt(n - 1), j = i + 1 + rnd.nextInt(n - i - 1);
            int target = a[i] + a[j];
            if (!Arrays.equals(T.twoSum(a.clone(), target), bruteForce(a, target))) agree = false;
        }
        System.out.println("Test 10: " + agree + " (Expected: true)  - matches brute force, 400 unique-solution cases");

        // n = 3e4, the constraint ceiling, answer at the far ends
        int[] big = new int[30000];
        for (int i = 0; i < big.length; i++) big[i] = -1000 + (i * 2000) / big.length;
        long t0 = System.nanoTime();
        int[] r = T.twoSum(big.clone(), big[0] + big[big.length - 1]);
        long ms = (System.nanoTime() - t0) / 1_000_000;
        System.out.println("Test 11: " + Arrays.toString(r) + " (Expected: [1, 30000])  - n=30000 in " + ms + "ms");
    }
}
