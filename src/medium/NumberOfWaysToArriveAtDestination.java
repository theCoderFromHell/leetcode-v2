package medium;

public class NumberOfWaysToArriveAtDestination {
    int MOD = 1000000007;
    public int countPaths(int n, int[][] roads) {
        long[][][] dp = new long[n][n][2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    dp[i][j][0] = 0;
                    dp[i][j][1] = 1;
                } else {
                    dp[i][j][0] = (long) 1e12;
                    dp[i][j][1] = 0;
                    dp[j][i][0] = (long) 1e12;
                    dp[j][i][1] = 0;
                }
            }
        }
        int size = roads.length;
        for (int i = 0; i < size; i++) {
            int source = roads[i][0];
            int destination = roads[i][1];
            int time = roads[i][2];
            dp[source][destination][0] = time;
            dp[source][destination][1] = 1;
            dp[destination][source][0] = time;
            dp[destination][source][1] = 1;
        }

        for (int middle = 0; middle < n; middle++) {
            for (int source = 0; source < n; source++) {
                for (int destination = 0; destination < n; destination++) {
                    if (middle != source && middle != destination) {
                        long time = dp[source][middle][0] + dp[middle][destination][0];
                        if (time < dp[source][destination][0]) {
                            dp[source][destination][0] = time;
                            dp[source][destination][1] = (dp[source][middle][1] * dp[middle][destination][1]) % MOD;
                        } else if (time == dp[source][destination][0])
                            dp[source][destination][1] = (dp[source][destination][1] + dp[source][middle][1] * dp[middle][destination][1]) % MOD;
                    }
                }
            }
        }
        return (int)dp[n-1][0][1];
    }

    public static void main(String[] args) {
        NumberOfWaysToArriveAtDestination N = new NumberOfWaysToArriveAtDestination();
        System.out.println(N.countPaths(7, new int[][]{{0,6,7},{0,1,2},{1,2,3},{1,3,3},{6,3,3},{3,5,1},{6,5,1},{2,5,1},{0,4,5},{4,6,2}}));
        System.out.println(N.countPaths(2, new int[][]{{1,0,10}}));
    }
}
