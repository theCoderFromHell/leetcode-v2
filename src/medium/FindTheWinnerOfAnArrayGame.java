package medium;

import java.util.LinkedList;
import java.util.Queue;

public class FindTheWinnerOfAnArrayGame {
    public int getWinner(int[] arr, int k) {
        int N = arr.length;
        Queue<Integer> queue = new LinkedList<>();
        int maximum = Integer.MIN_VALUE;
        for (int i = 1; i < N; i++) {
            queue.add(arr[i]);
            maximum = Math.max(maximum, arr[i]);
        }
        int winner = arr[0];
        int wins = 0;
        while (!queue.isEmpty()) {
            int top = queue.poll();
            if (top > winner) {
                wins = 1;
                queue.offer(winner);
                winner = top;
            } else {
                queue.offer(top);
                wins++;
            }
            if (winner == maximum || wins == k)
                return winner;
        }
        return winner;
    }
}
