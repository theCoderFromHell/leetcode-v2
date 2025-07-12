package medium;

public class UniqueBinarySearchTrees {
    public int numTrees(int n) {
        if(n == 0)
            return 1;
        if(n == 1 || n == 2)
            return n;
        int[] dp = new int[n+1];
        dp[0] = 1;
        dp[1] = 1;
        dp[2] = 2;
        for(int i=3; i<=n; i++) {
            int current = 0;
            int total = 0;
            while(current < i) {
                total += (dp[current] * dp[i-1-current]);
                current++;
            }
            dp[i] = total;
        }
        return dp[n];
    }

    public static void main(String[] args) {
        UniqueBinarySearchTrees U = new UniqueBinarySearchTrees();
        System.out.println(U.numTrees(3));
        System.out.println(U.numTrees(1));
    }
}
