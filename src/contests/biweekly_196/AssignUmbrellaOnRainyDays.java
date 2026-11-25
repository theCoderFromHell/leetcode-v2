package contests.biweekly_196;

import java.util.Arrays;

// https://leetcode.com/problems/assign-umbrella-on-rainy-days/
public class AssignUmbrellaOnRainyDays {
    public String[] assignUmbrella(int[] rain) {
        int size = rain.length;
        int alice = 0, bob = 0;
        String[] result = new String[size];
        boolean aliceNeeds, bobNeeds;
        for (int i = 0; i < size; i++) {
            aliceNeeds = (i < size-1 && rain[i] == 1 && rain[i+1] == 1);
            bobNeeds = (rain[i] == 1);
            if (!aliceNeeds && !bobNeeds)
                result[i] = "None";
            else if (aliceNeeds && bobNeeds) {
                if (alice <= bob) {
                    alice++;
                    result[i] = "Alice";
                } else {
                    bob++;
                    result[i] = "Bob";
                }
            } else if (aliceNeeds) {
                alice++;
                result[i] = "Alice";
            } else if (bobNeeds) {
                bob++;
                result[i] = "Bob";
            }
        }
        return result;
    }

    /*
     * Revision Note — Assign Umbrella on Rainy Days (Easy, Biweekly Contest 196)
     *
     * Pattern: one-pass simulation with two counters.
     *
     * Key insight: Alice needing the umbrella implies Bob needs it too, since rain[i] == 1 is part of
     * both conditions. That leaves three cases. With no rain, nobody gets it. With rain today but not
     * tomorrow (or on the last day), Bob gets it. With rain today and tomorrow, whoever has had it
     * fewer times gets it, and Alice wins ties.
     *
     * Gotchas:
     *  - On the last day Alice can never need it: the i < size - 1 guard (Test 3, and Example 1's day 4).
     *  - Ties go to Alice, so the comparison is alice <= bob, not < (Test 5).
     *  - The counts carry across dry days. Bob is ahead from day 0 in Test 6, so Alice wins day 2.
     *  - The `else if (aliceNeeds)` branch can never run: when aliceNeeds is true, bobNeeds is too,
     *    so the both-need branch has already taken it.
     *
     * Complexity: O(n) time, O(n) for the output array.
     *
     * Template:
     *   for each day i:
     *       if rain[i] == 0: None
     *       else if i == n - 1 or rain[i + 1] == 0: Bob++
     *       else: alice <= bob ? Alice++ : Bob++
     */
    public static void main(String[] args) {
        AssignUmbrellaOnRainyDays A = new AssignUmbrellaOnRainyDays();

        // Test 1: LeetCode example 1 — two rainy pairs; Alice takes the first day of each, Bob the second
        System.out.println("Test 1: " + Arrays.toString(A.assignUmbrella(new int[]{1, 1, 0, 1, 1})) + " (Expected: [Alice, Bob, None, Alice, Bob])");

        // Test 2: LeetCode example 2 — no two rainy days in a row, so Alice never needs it
        System.out.println("Test 2: " + Arrays.toString(A.assignUmbrella(new int[]{0, 1, 0, 1})) + " (Expected: [None, Bob, None, Bob])");

        // Test 3: a single rainy day — it is the last day, so only Bob can need it
        System.out.println("Test 3: " + Arrays.toString(A.assignUmbrella(new int[]{1})) + " (Expected: [Bob])");

        // Test 4: no rain at all
        System.out.println("Test 4: " + Arrays.toString(A.assignUmbrella(new int[]{0, 0, 0})) + " (Expected: [None, None, None])");

        // Test 5: rain every day — both need it on days 0-3, so it alternates starting with Alice
        // (tie), and the last day goes to Bob alone
        System.out.println("Test 5: " + Arrays.toString(A.assignUmbrella(new int[]{1, 1, 1, 1, 1})) + " (Expected: [Alice, Bob, Alice, Bob, Bob])");

        // Test 6: Bob gets day 0 alone, and the counts carry over the dry day, so Alice has fewer and
        // wins day 2
        System.out.println("Test 6: " + Arrays.toString(A.assignUmbrella(new int[]{1, 0, 1, 1})) + " (Expected: [Bob, None, Alice, Bob])");
    }
}
