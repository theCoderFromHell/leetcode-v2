package medium;

public class DominoAndTrominoTiling {
    public int numTilings(int n) {
        int MOD = 1000000007;
        if (n <= 2)
            return n;
        long previous = 1L;
        long current = 1L;
        long partial = 0L;

        for (int i = 2; i <= n; i++) {
            long temp = current;
            current = (2 * partial + previous + current) % MOD;
            partial = (partial + previous) % MOD;
            previous = temp;
        }
        return (int) current;
    }

    public int numTilingsV2(int n) {
        int MOD = 1000000007;
        if (n <= 2)
            return n;
        long[] dp = new long[n+1];
        dp[0] = 1;
        dp[1] = 1;
        long[] partial = new long[n+1];
        partial[0] = 1;
        partial[1] = 0;
        for (int i = 2; i <= n; i++) {
            dp[i] = ((2 * partial[i-1] % MOD) + (dp[i-2] % MOD) + dp[i-1] % MOD) % MOD;
            partial[i] = (partial[i-1] % MOD + dp[i-2] % MOD) % MOD;
        }
        return (int)dp[n];
    }

    public static void main(String[] args) {
        DominoAndTrominoTiling D = new DominoAndTrominoTiling();
        System.out.println(D.numTilings(3));
        System.out.println(D.numTilings(4));
        System.out.println(D.numTilings(5));
        System.out.println(D.numTilings(6));
        System.out.println(D.numTilings(7));
        System.out.println(D.numTilings(8));
        System.out.println(D.numTilings(9));
        System.out.println(D.numTilings(10));
    }
}
