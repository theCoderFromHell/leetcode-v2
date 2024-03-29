package medium;

import java.util.HashSet;
import java.util.LinkedHashSet;

public class WaterAndJugProblem {
    public boolean canMeasureWater(int x, int y, int target) {
        if (target == 0)
            return true;
        HashSet<String> visited = new LinkedHashSet<>();
        return dfs(0, 0, x, y, target, visited);
    }

    private boolean dfs(int curr_x, int curr_y, int x, int y, int target, HashSet<String> visited) {
        if (curr_x + curr_y == target)
            return true;
        visited.add(curr_x + "-" + curr_y);
        if (curr_x < x && !visited.contains(x + "-" + curr_y))
            if (dfs(x, curr_y, x, y, target, visited))
                return true;

        if (curr_y < y && !visited.contains(curr_x + "-" + y))
            if (dfs (curr_x, y, x, y, target, visited))
                return true;

        if (curr_x > 0 && !visited.contains(0 + "-" + curr_y))
            if (dfs (0, curr_y, x, y, target, visited))
                return true;

        if (curr_y > 0 && !visited.contains(curr_x + "-" + 0))
            if (dfs (curr_x, 0, x, y, target, visited))
                return true;

        if (curr_x > 0 && curr_y < y) {
            int transfer = Math.min(y - curr_y, x);
            if (!visited.contains((curr_x - transfer) + "-" + (curr_y + transfer)))
                if (dfs (curr_x - transfer, curr_y + transfer, x, y, target, visited))
                    return true;
        }
        if (curr_y > 0 && curr_x < x) {
            int transfer = Math.min(x - curr_x, y);
            if (!visited.contains((curr_x + transfer) + "-" + (curr_y - transfer)))
                if (dfs (curr_x + transfer, curr_y - transfer, x, y, target, visited))
                    return true;
        }
        return false;
    }

    public static void main(String[] args) {
        WaterAndJugProblem p = new WaterAndJugProblem();
        System.out.println(p.canMeasureWater(3,5, 4));
        System.out.println(p.canMeasureWater(2,6, 5));
        System.out.println(p.canMeasureWater(1,2, 3));
    }
}
