package medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class KnightDialer {
    int MOD = 1000000007;
    public int knightDialer(int n) {
        HashMap<Integer, List<Integer>> options = new HashMap<>();
        options.put(10, Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        options.put(0, Arrays.asList(4, 6));
        options.put(1, Arrays.asList(6, 8));
        options.put(2, Arrays.asList(7, 9));
        options.put(3, Arrays.asList(4, 8));
        options.put(4, Arrays.asList(0, 3, 9));
        options.put(6, Arrays.asList(0, 1, 7));
        options.put(7, Arrays.asList(2, 6));
        options.put(8, Arrays.asList(1, 3));
        options.put(9, Arrays.asList(2, 4));

        Integer[][] dp = new Integer[11][5001];
        return solve(10, n, options, dp);
    }

    private int solve(int root, int n, HashMap<Integer, List<Integer>> options, Integer[][] dp) {
        if (n == 1)
            return options.getOrDefault(root, new ArrayList<>()).size();
        if (dp[root][n] != null)
            return dp[root][n];
        long count = 0;
        for (int target : options.getOrDefault(root, new ArrayList<>())) {
            count = (count + solve(target, n-1, options, dp)) % MOD;
        }
        dp[root][n] = (int) count;
        return dp[root][n];
    }

    public static void main(String[] args) {
        KnightDialer K = new KnightDialer();
        System.out.println(K.knightDialer(1));
        System.out.println(K.knightDialer(2));
        System.out.println(K.knightDialer(3131));
        System.out.println(K.knightDialer(50));
        System.out.println(K.knightDialer(23));
        System.out.println(K.knightDialer(675));
    }
}
