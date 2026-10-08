package medium;

import java.util.Arrays;

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
    }
}
