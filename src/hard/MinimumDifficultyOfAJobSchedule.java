package hard;

import java.util.Arrays;

public class MinimumDifficultyOfAJobSchedule {
    public int minDifficulty(int[] jobDifficulty, int d) {
        int N = jobDifficulty.length;
        int[][] dp = new int[N][d+1];
        for (int[] row : dp)
            Arrays.fill(row, -1);
        int[] highestFromHere = new int[N];
        highestFromHere[N-1] = jobDifficulty[N-1];
        for (int i = N-2; i >= 0; i--)
            highestFromHere[i] = Math.max(jobDifficulty[i], highestFromHere[i + 1]);
        int result = solve(jobDifficulty, d, N, highestFromHere, dp, 0, 1);
        return (result == Integer.MAX_VALUE) ? -1 : result;
    }

    private int solve(int[] jobDifficulty, int d, int N, int[] highestFromHere, int[][] dp, int jobId, int day) {
        if (d == day) {
            return highestFromHere[jobId];
        }
        if (dp[jobId][day] != -1)
            return dp[jobId][day];
        int highest = jobDifficulty[jobId];
        int bestCase = Integer.MAX_VALUE;
        for (int i = jobId; i < N - (d - day); i++) {
            highest = Math.max(highest, jobDifficulty[i]);
            bestCase = Math.min(bestCase, highest + solve(jobDifficulty, d, N, highestFromHere, dp, i+1, day+1));
        }
        dp[jobId][day] = bestCase;
        return dp[jobId][day];
    }

    public static void main(String[] args) {
        MinimumDifficultyOfAJobSchedule m = new MinimumDifficultyOfAJobSchedule();
        System.out.println(m.minDifficulty(new int[]{6,5,4,3,2,1}, 2));
        System.out.println(m.minDifficulty(new int[]{9,9,9}, 4));
        System.out.println(m.minDifficulty(new int[]{1,1,1}, 3));

    }
}
