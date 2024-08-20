package medium;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class JumpGameIII {
    public boolean canReach(int[] arr, int start) {
        int N = arr.length;
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[N];
        queue.add(start);
        visited[start] = true;
        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (arr[current] == 0)
                return true;
            if (current + arr[current] >= 0 && current + arr[current] < N && !visited[current + arr[current]]) {
                queue.add(current + arr[current]);
                visited[current + arr[current]] = true;
            }
            if (current - arr[current] >= 0 && current - arr[current] < N && !visited[current - arr[current]]) {
                queue.add(current - arr[current]);
                visited[current - arr[current]] = true;
            }
        }
        return false;
    }
}
