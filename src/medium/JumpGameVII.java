package medium;

import java.util.*;

public class JumpGameVII {
    public boolean canReach(String s, int minJump, int maxJump) {
        int size = s.length();
        if (s.charAt(0) != '0' || s.charAt(size-1) != '0')
            return false;
        HashSet<Integer> spots = new HashSet<>();
        for (int i = 0; i < size; i++) {
            if (s.charAt(i) == '0')
                spots.add(i);
        }
        Queue<Integer> queue = new LinkedList<>();
        queue.add(0);
        HashSet<Integer> visited = new HashSet<>();
        visited.add(0);
        int min = 0;
        int max = 0;
        while (!queue.isEmpty()) {
            int top = queue.poll();
            if (top == size-1)
                return true;
            min = Math.max(max, top + minJump);
            max = Math.min(size-1, top + maxJump);
            for (int i = min; i <= max; i++) {
                if (i == size-1)
                    return true;
                if (i < size && spots.contains(i) && !visited.contains(i)) {
                    queue.add(i);
                    spots.remove(i);
                    visited.add(i);
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        JumpGameVII J = new JumpGameVII();
        System.out.println(J.canReach("0000000000", 2, 5));
        System.out.println(J.canReach("011010", 2, 3));
        System.out.println(J.canReach("01101110", 2, 3));
    }
}
