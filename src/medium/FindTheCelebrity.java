package medium;

import java.util.LinkedList;
import java.util.Queue;

public class FindTheCelebrity {
    public int findCelebrity(int n) {
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++)
            queue.add(i);
        while(queue.size() > 1) {
            int a = queue.poll();
            int b = queue.poll();
            queue.offer(knows(a, b) ? b : a);
        }
        if (queue.isEmpty())
            return -1;
        int potentialCelebrity = queue.poll();
        for (int i = 0; i < n; i++) {
            if (i != potentialCelebrity) {
                if (knows(potentialCelebrity, i) || !knows(i, potentialCelebrity)) {
                    return -1;
                }
            }
        }
        return potentialCelebrity;
    }

    private boolean knows(int a, int b) {
        return true;
    }
}
