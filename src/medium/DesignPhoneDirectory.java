package medium;

import java.util.HashSet;

// https://leetcode.com/problems/design-phone-directory/
public class DesignPhoneDirectory {
    static class PhoneDirectory {
        HashSet<Integer> available;
        public PhoneDirectory(int maxNumbers) {
            this.available = new HashSet<>();
            for (int i = 0; i < maxNumbers; i++) {
                available.add(i);
            }
        }

        public int get() {
            if (available.isEmpty())
                return -1;
            int number = available.iterator().next();
            available.remove(number);
            return number;
        }

        public boolean check(int number) {
            return available.contains(number);
        }

        public void release(int number) {
            available.add(number);
        }
    }

    /*
     * Revision Note — Design Phone Directory (Medium)
     * Pattern: A single HashSet of the available numbers — membership IS the state
     * Key Insight: get() says "return ANY available number", not the smallest. That permission
     *              is the design hint: with no ordering requirement a HashSet suffices, and all
     *              three operations reduce to set membership. No queue, no heap, no sorting.
     * Gotchas:
     *   - DOUBLE RELEASE is the trap this problem is built around. release(2) twice must not let
     *     2 be handed out twice. A Queue<Integer> of free numbers gets this WRONG — the queue has
     *     no idea it already contains 2, so it enqueues a duplicate and get() serves it twice.
     *     A Set is idempotent by construction, so the bug cannot be expressed. Choosing a
     *     structure whose invariant matches the problem beats patching a faster one.
     *   - Releasing a number that was never taken is the same situation and is equally safe.
     *   - get() must return -1, not throw, when exhausted. Check isEmpty() first.
     *   - available.iterator().next() is the idiom for "any element of a Set". It is NOT truly
     *     O(1): it scans the HashMap table from bucket 0 until it finds an occupant, and Java
     *     never shrinks that table. As the set empties the scan lengthens while the set shrinks,
     *     so draining n numbers is O(n^2) — measurably ~4us per call at n=10^4 versus the
     *     nanoseconds a real O(1) op costs. Fine within constraints; do not claim O(1) for get()
     *     in an interview without that caveat.
     * Alternative for true O(1): Queue for handout + Set for membership, with release guarded as
     *              if (available.add(number)) free.offer(number);
     *              That guard is load-bearing — Set.add returns false when already present, and
     *              without it double release re-enqueues and the directory serves a used number.
     *              Faster, twice the code, and reintroduces the bug this version cannot have.
     * Complexity: check/release O(1); get() O(1) amortised with the caveat above; space O(n).
     */
    public static void main(String[] args) {
        // Test 1: LeetCode walkthrough — first two gets hand out distinct numbers
        PhoneDirectory P = new PhoneDirectory(3);
        System.out.println("Test 1: " + (P.get() != P.get()) + " (Expected: true)");

        // Test 2: the third number is still free
        System.out.println("Test 2: " + P.check(2) + " (Expected: true)");

        // Test 3: taking it empties the pool
        System.out.println("Test 3: " + P.get() + " (Expected: 2)");

        // Test 4: now taken, so no longer available
        System.out.println("Test 4: " + P.check(2) + " (Expected: false)");

        // Test 5: exhausted pool returns -1 rather than throwing
        System.out.println("Test 5: " + P.get() + " (Expected: -1)");

        // Test 6: release puts it back
        P.release(2);
        System.out.println("Test 6: " + P.check(2) + " (Expected: true)");

        // Test 7: and it can be handed out again
        System.out.println("Test 7: " + P.get() + " (Expected: 2)");

        // Test 8: DOUBLE RELEASE GUARD — release(0) twice must yield ONE number, then -1.
        //         A queue-based implementation without a guard returns 0 twice here.
        PhoneDirectory D = new PhoneDirectory(2);
        D.get();
        D.get();
        D.release(0);
        D.release(0);
        System.out.println("Test 8: " + D.get() + " (Expected: 0)");
        System.out.println("Test 9: " + D.get() + " (Expected: -1)");

        // Test 10: releasing a number that was never taken must not create a phantom
        PhoneDirectory U = new PhoneDirectory(2);
        U.release(1);
        int count = 0;
        while (U.get() != -1)
            count++;
        System.out.println("Test 10: " + count + " (Expected: 2)");

        // Test 11: zero-capacity directory
        PhoneDirectory Z = new PhoneDirectory(0);
        System.out.println("Test 11: " + Z.get() + " (Expected: -1)");

        // Test 12: capacity one — take it, check it, release it, take it again
        PhoneDirectory O = new PhoneDirectory(1);
        System.out.println("Test 12: " + O.get() + " (Expected: 0)");
        System.out.println("Test 13: " + O.check(0) + " (Expected: false)");
        O.release(0);
        System.out.println("Test 14: " + O.get() + " (Expected: 0)");

        // Test 15: drain the full pool — every number handed out exactly once
        PhoneDirectory B = new PhoneDirectory(1000);
        java.util.Set<Integer> seen = new java.util.HashSet<>();
        int n;
        while ((n = B.get()) != -1)
            seen.add(n);
        System.out.println("Test 15: " + seen.size() + " (Expected: 1000)");
    }
}
