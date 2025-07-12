package medium;

public class SolvingQuestionsWithBrainpower {
    public long mostPoints(int[][] questions) {
        int size = questions.length;
        Long[] dp = new Long[size];
        solve(questions, size, 0, dp);
        return dp[0];
    }

    private long solve(int[][] questions, int size, int index, Long[] dp) {
        if (index >= size)
            return 0;
        if (dp[index] != null)
            return dp[index];
        long maxPoints;
        maxPoints = Math.max(solve(questions, size, index + 1, dp),
                        questions[index][0] + solve(questions, size, index + 1 + questions[index][1], dp));
        dp[index] = maxPoints;
        return maxPoints;
    }

    public static void main(String[] args) {
        SolvingQuestionsWithBrainpower S = new SolvingQuestionsWithBrainpower();
        System.out.println(S.mostPoints(new int[][]{{3,2},{4,3},{4,4},{2,5}}));
        System.out.println(S.mostPoints(new int[][]{{1,1},{2,2},{3,3},{4,4},{5,5}}));
        System.out.println(S.mostPoints(new int[][]{{12,46},{78,19},{63,15},{79,62},{13,10}}));
    }
}
