package medium;

// https://leetcode.com/problems/stone-game-ii/
public class StoneGameII {
    public int stoneGameII(int[] piles) {
        int size = piles.length;
        int total = 0;
        for (int i = 0; i < size; i++) {
            total += piles[i];
        }
        Integer[][] dp = new Integer[size][size+1];
        int diff = game(piles, size, dp, 0, 1);
        return (total + diff)/2;
    }

    private int game(int[] piles, int size, Integer[][] dp, int index, int M) {
        if (index >= size)
            return 0;
        if (dp[index][M] != null)
            return dp[index][M];
        int maxStones = Integer.MIN_VALUE;
        int sum = 0;
        for (int X = 1; X <= 2 * M; X++) {
            if (index + X - 1 >= size)
                break;
            sum += piles[index + X - 1];
            maxStones = Math.max(maxStones, sum - game(piles, size, dp, index + X, Math.max(M, X)));
        }
        dp[index][M] = maxStones;
        return dp[index][M];
    }

    /*
     * Revision Note — Stone Game II (Medium)
     * Pattern: Minimax on (index, M) with memoisation — the recursion returns the DIFFERENCE
     * Key Insight: State is (index, M) — where you are and how large a bite is legal. One
     *              function serves both players: sum - game(next) means "what I take, minus
     *              whatever my opponent then nets against me". Because both play optimally the
     *              roles are symmetric, so no whoseTurn flag is needed.
     * Gotchas:
     *   - The recursion yields a DIFFERENCE, but the problem asks for Alice's TOTAL. Convert:
     *     alice + bob = total and alice - bob = diff, so alice = (total + diff) / 2. Returning
     *     the raw difference gives -6 on [2,7,9,4,4] where the answer is 10. This is exactly
     *     where Stone Game III differs — III asks WHO WINS, which needs only the SIGN, so the
     *     difference is the whole answer there and no conversion applies. Same recursion shape,
     *     different question.
     *   - The integer division is safe: alice and bob differ by 2*bob from total, so total and
     *     diff always share parity and (total + diff) / 2 never truncates.
     *   - Seed maxStones with Integer.MIN_VALUE, not 0. A losing position has a negative
     *     difference, and seeding 0 silently floors it — [2,7,9,4,4] returns 0 instead of -6.
     *     (Stone Game III avoids this by seeding with its first legal move rather than 0.)
     *   - dp must be [size][size + 1]. M is part of the state and reaches size, so a [size][size]
     *     array throws ArrayIndexOutOfBounds — visibly on a single pile, where dp[0][1] is
     *     already out of range.
     *   - M = max(M, X), not M = X. The ceiling only ever rises, so taking a big chunk then a
     *     small one keeps the larger allowance.
     *   - Break when index + X - 1 >= size; X is bounded by the piles that actually remain,
     *     not merely by 2M.
     * Template:
     *   game(i, M):
     *     if i >= size: return 0
     *     best = MIN_VALUE, sum = 0
     *     for X in 1..2M while i+X-1 < size:
     *         sum += piles[i+X-1]
     *         best = max(best, sum - game(i+X, max(M,X)))
     *     return memo[i][M] = best
     *   answer = (total + game(0,1)) / 2
     * Complexity: O(n^2) states times O(n) transitions = O(n^3) time, O(n^2) space. At n=100
     *             that is ~10^6 — instant.
     */
    public static void main(String[] args) {
        StoneGameII S = new StoneGameII();

        // Test 1: LeetCode example 1 — Alice takes 1, Bob takes 2, Alice takes 2 -> 2+4+4
        System.out.println("Test 1: " + S.stoneGameII(new int[]{2, 7, 9, 4, 4}) + " (Expected: 10)");

        // Test 2: LeetCode example 2 — the 100 pile dominates, Alice must set up to reach it
        System.out.println("Test 2: " + S.stoneGameII(new int[]{1, 2, 3, 4, 5, 100}) + " (Expected: 104)");

        // Test 3: single pile — Alice takes it all, and M never grows
        System.out.println("Test 3: " + S.stoneGameII(new int[]{1}) + " (Expected: 1)");

        // Test 4: single pile at the value ceiling
        System.out.println("Test 4: " + S.stoneGameII(new int[]{10000}) + " (Expected: 10000)");

        // Test 5: two piles — with M=1 Alice may take up to 2, so she takes both
        System.out.println("Test 5: " + S.stoneGameII(new int[]{1, 2}) + " (Expected: 3)");

        // Test 6: all piles equal — the split is even, so the DIFFERENCE is 0 while the
        //         answer is 10. Any solution returning the difference passes this by accident.
        System.out.println("Test 6: " + S.stoneGameII(new int[]{5, 5, 5, 5}) + " (Expected: 10)");

        // Test 7: front-loaded — grabbing the 10 immediately is not automatically best
        System.out.println("Test 7: " + S.stoneGameII(new int[]{10, 1, 1, 1, 1, 1, 1, 1}) + " (Expected: 13)");

        // Test 8: increasing run — M growth matters more than any single pile
        System.out.println("Test 8: " + S.stoneGameII(new int[]{1, 2, 3, 4, 5, 6, 7}) + " (Expected: 15)");

        // Test 9: decreasing run, odd length
        System.out.println("Test 9: " + S.stoneGameII(new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1}) + " (Expected: 25)");

        // Test 10: MAX SIZE, all equal — 100 piles of 10^4. Exercises the dp bounds, since M
        //          can grow large here; a dp sized [n][n] rather than [n][n+1] throws AIOOBE.
        int[] maxEqual = new int[100];
        java.util.Arrays.fill(maxEqual, 10000);
        System.out.println("Test 10: " + S.stoneGameII(maxEqual) + " (Expected: 500000)");

        // Test 11: MAX SIZE, increasing 1..100 — total 5050, near-even split
        int[] maxInc = new int[100];
        for (int i = 0; i < maxInc.length; i++)
            maxInc[i] = i + 1;
        System.out.println("Test 11: " + S.stoneGameII(maxInc) + " (Expected: 2526)");
    }
}
