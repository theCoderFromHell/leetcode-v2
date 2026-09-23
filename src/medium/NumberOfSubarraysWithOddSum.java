package medium;

// https://leetcode.com/problems/number-of-sub-arrays-with-odd-sum/
public class NumberOfSubarraysWithOddSum {
    public int numOfSubarrays(int[] arr) {
        int MOD = 1000000007;
        int size = arr.length;
        int odd = 0, even = 1;
        int result = 0;
        int currSum = 0;
        for (int i = 0; i < size; i++) {
            currSum += arr[i];
            if (currSum % 2 == 0) {
                even++;
                result = (result + odd) % MOD;
            } else {
                odd++;
                result = (result + even) % MOD;
            }
        }
        return result;
    }

    /*
     * Revision Note — Number of Sub-arrays With Odd Sum (Medium)
     * Pattern: Prefix-sum PARITY counting — one pass, two counters
     * Key Insight: sum(i..j) = prefix[j] - prefix[i-1], and a difference is odd exactly when the
     *              two parities DIFFER. So subarrays ending at j with an odd sum = the number of
     *              earlier prefixes whose parity is the opposite of prefix[j]. Carry a running
     *              count of odd and even prefixes and add the opposite one at each step —
     *              never enumerate subarrays (n(n+1)/2 is 5*10^9 at n=10^5).
     * Gotchas:
     *   - even starts at 1, not 0. The empty prefix (sum 0) is even and is what makes a subarray
     *     starting at index 0 countable. Seeding even = 0 silently loses every such subarray.
     *   - "Return it modulo 10^9+7" is a statement about MAGNITUDE, not formatting. It is telling
     *     you the answer exceeds int. Worst case is all-odd input: (n/2 + 1) * (n/2) = 2.5*10^9
     *     at n=10^5, which overshoots Integer.MAX_VALUE by ~3.5*10^8 and returns NEGATIVE.
     *   - Small tests cannot reveal that overflow — you need roughly 65,000 elements before the
     *     count can reach 2^31. Every hand-written case passes while the judge fails.
     *   - int is still the right type once you mod at EVERY accumulation: result < 10^9+7 and
     *     odd/even <= 10^5, so the intermediate peaks near 1.0001*10^9, inside int. Modding only
     *     at the end would be too late — the overflow has already happened.
     *   - currSum accumulates a value only one bit of which is ever read. parity ^= (value & 1)
     *     is equivalent, removes the last thing that could overflow, and names the real invariant:
     *     this is a two-state machine, not a sum.
     * Template:
     *   odd = 0, even = 1, result = 0, parity = 0
     *   for value in arr:
     *       parity ^= value & 1
     *       if parity == 0: even++; result = (result + odd)  % MOD
     *       else:           odd++;  result = (result + even) % MOD
     * Generalises: swap parity for (prefix % k) in a HashMap and the same shape counts subarrays
     *              whose sum is divisible by k (LC 974), or equals k (LC 560).
     */
    public static void main(String[] args) {
        NumberOfSubarraysWithOddSum N = new NumberOfSubarraysWithOddSum();

        // Test 1: LeetCode example 1 — odd sums are [1], [1,3,5], [3], [5]
        System.out.println("Test 1: " + N.numOfSubarrays(new int[]{1, 3, 5}) + " (Expected: 4)");

        // Test 2: LeetCode example 2 — all even values, no subarray can be odd
        System.out.println("Test 2: " + N.numOfSubarrays(new int[]{2, 4, 6}) + " (Expected: 0)");

        // Test 3: LeetCode example 3 — mixed parities
        System.out.println("Test 3: " + N.numOfSubarrays(new int[]{1, 2, 3, 4, 5, 6, 7}) + " (Expected: 16)");

        // Test 4: single odd element
        System.out.println("Test 4: " + N.numOfSubarrays(new int[]{1}) + " (Expected: 1)");

        // Test 5: single even element
        System.out.println("Test 5: " + N.numOfSubarrays(new int[]{2}) + " (Expected: 0)");

        // Test 6: two odds — [1] and [1] qualify, [1,1] does not
        System.out.println("Test 6: " + N.numOfSubarrays(new int[]{1, 1}) + " (Expected: 2)");

        // Test 7: leading evens then an odd — exercises the even = 1 seed
        System.out.println("Test 7: " + N.numOfSubarrays(new int[]{100, 100, 99}) + " (Expected: 3)");

        // Test 8: four odds — every odd-length run qualifies
        System.out.println("Test 8: " + N.numOfSubarrays(new int[]{7, 7, 7, 7}) + " (Expected: 6)");

        // Test 9: OVERFLOW GUARD — n=10^5 all odd gives 2500050000 true subarrays, which exceeds
        //         Integer.MAX_VALUE by ~3.5*10^8. Without a modulo at each step this returns
        //         -1794917296. No smaller input can catch this.
        int[] allOdd = new int[100000];
        java.util.Arrays.fill(allOdd, 1);
        System.out.println("Test 9: " + N.numOfSubarrays(allOdd) + " (Expected: 500049986)");

        // Test 10: n=10^5 all even — must stay exactly 0 at scale
        int[] allEven = new int[100000];
        java.util.Arrays.fill(allEven, 2);
        System.out.println("Test 10: " + N.numOfSubarrays(allEven) + " (Expected: 0)");
    }
}
