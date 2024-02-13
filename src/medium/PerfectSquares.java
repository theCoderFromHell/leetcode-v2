package medium;

import java.util.LinkedList;
import java.util.Queue;

public class PerfectSquares {
    public static int numSquares(int n) {
        if (n <= 3)
            return n;
        Queue<Integer> sq = new LinkedList<>();
        int[] visited = new int[10001];
        sq.add(0);
        visited[0] = 1;
        int steps = 0;
        while (!sq.isEmpty()) {
            int size = sq.size();
            while (size-- > 0) {
                int curr = sq.poll();
                for (int i = 1; i*i < n; i++) {
                    int next = curr + i*i;
                    if (next == n)
                        return steps + 1;
                    if (next > n )
                        break;
                    if (visited[next] != 1) {
                        sq.add(next);
                        visited[next] = 1;
                    }
                }
            }
            steps++;
        }
        return -1;
    }
    public static void main(String[] args) {
        System.out.println(numSquares(12));
        System.out.println(numSquares(13));
    }
}
