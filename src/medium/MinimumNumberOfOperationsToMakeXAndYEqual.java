package medium;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class MinimumNumberOfOperationsToMakeXAndYEqual {
    public int minimumOperationsToMakeEqual(int x, int y) {
        if (y >= x)
            return y - x ;
        int minOps = x - y;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(x);
        int ops = 0;
        HashSet<Integer> visited = new HashSet<>();
        visited.add(x);
        while(!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int value = queue.poll();
                if (value == y) {
                    minOps = Math.min(minOps, ops);
                }
                if (!visited.contains(value+1)) {
                    queue.add(value+1);
                    visited.add(value+1);
                }
                if (!visited.contains(value-1)) {
                    queue.add(value-1);
                    visited.add(value-1);
                }
                if (value % 11 == 0 && !visited.contains(value/11)) {
                    queue.add(value/11);
                    visited.add(value/11);
                }
                if (value % 5 == 0 && !visited.contains(value/5)) {
                    queue.add(value/5);
                    visited.add(value/5);
                }
            }
            ops++;
            if (ops >= minOps)
                break;
        }
        return minOps;
    }

    public static void main(String[] args) {
        MinimumNumberOfOperationsToMakeXAndYEqual m = new MinimumNumberOfOperationsToMakeXAndYEqual();
        System.out.println(m.minimumOperationsToMakeEqual(26, 1));
        System.out.println(m.minimumOperationsToMakeEqual(54, 2));
        System.out.println(m.minimumOperationsToMakeEqual(25, 30));
        System.out.println(m.minimumOperationsToMakeEqual(450, 23));
        System.out.println(m.minimumOperationsToMakeEqual(89, 57));
    }
}
