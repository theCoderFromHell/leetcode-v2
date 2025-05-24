package hard;

import java.util.ArrayList;
import java.util.List;

public class StoneGameIV {
    public boolean winnerSquareGame(int n) {
        List<Integer> squares = new ArrayList<>();
        int i = 1;
        while (i*i <= n) {
            squares.add(i * i);
            i++;
        }
        int total = squares.size();
        int[] dp = new int[100003];
        for (i = 0; i <= n; i++) {
            if (dp[i] == 0) {
                int index = 0;
                while (index < total && i + squares.get(index) <= n) {
                    dp[i + squares.get(index)] = 1;
                    index++;
                }
            }
        }
        return dp[n] != 0;
    }

    public static void main(String[] args) {
        StoneGameIV S = new StoneGameIV();
        System.out.println(S.winnerSquareGame(1));
        System.out.println(S.winnerSquareGame(2));
        System.out.println(S.winnerSquareGame(3));
        System.out.println(S.winnerSquareGame(4));
    }
}
