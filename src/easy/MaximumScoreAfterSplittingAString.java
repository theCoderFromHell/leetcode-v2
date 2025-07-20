package easy;

public class MaximumScoreAfterSplittingAString {
    public int maxScore(String s) {
        int size = s.length();
        int[] zeros = new int[size];
        int count = 0;
        for (int i = 0; i < size; i++) {
            count += ('1' - s.charAt(i));
            zeros[i] = count;
        }
        count = 0;
        int[] ones = new int[size];
        for (int i = size-1; i >= 0; i--) {
            count += (s.charAt(i) - '0');
            ones[i] = count;
        }
        int maxScore = 0;
        for (int i = 1; i < size; i++) {
            maxScore = Math.max(maxScore, zeros[i-1] + ones[i]);
        }
        return maxScore;
    }

    public static void main(String[] args) {
        MaximumScoreAfterSplittingAString M = new MaximumScoreAfterSplittingAString();
        System.out.println(M.maxScore("011101"));
        System.out.println(M.maxScore("00111"));
        System.out.println(M.maxScore("1111"));
    }
}
