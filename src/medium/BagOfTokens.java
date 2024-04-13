package medium;

import java.util.Arrays;

public class BagOfTokens {
    public int bagOfTokensScore(int[] tokens, int power) {
        int N = tokens.length;
        Arrays.sort(tokens);
        int maxScore = 0;
        int score = 0;
        int start = 0, end = N-1;
        while (start <= end) {
            if (tokens[start] <= power) {
                power -= tokens[start];
                score++;
                maxScore = Math.max(maxScore, score);
                start++;
            } else if (score > 0) {
                score--;
                power += tokens[end];
                end--;
            } else
                break;
        }
        return maxScore;
    }

    public static void main(String[] args) {
        BagOfTokens bagOfTokens = new BagOfTokens();
        System.out.println(bagOfTokens.bagOfTokensScore(new int[]{100}, 50));
        System.out.println(bagOfTokens.bagOfTokensScore(new int[]{200,100}, 150));
        System.out.println(bagOfTokens.bagOfTokensScore(new int[]{100,200,300,400}, 200));

    }
}
