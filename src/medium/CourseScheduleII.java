package medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CourseScheduleII {
    private int count;

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int[] result = new int[numCourses];
        if(prerequisites == null || prerequisites.length == 0) {
            for (int i = 0; i < numCourses; i++) {
                result[i] = i;
            }
            return result;
        }
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        int[] order = new int[numCourses];
        for (int[] prerequisite : prerequisites) {
            List<Integer> list = map.getOrDefault(prerequisite[0], new ArrayList<>());
            list.add(prerequisite[1]);
            map.put(prerequisite[0], list);
        }
        count = 1;
        for (int i = 0; i < numCourses; i++)
            if (!map.containsKey(i))
                order[i] = count++;

        for (Integer node : map.keySet()) {
            if (order[node] == 0) {
                int[] visited = new int[numCourses];
                if (!isPossible(node, map, visited, order))
                    return new int[]{};
            }
        }
        for (int i = 0; i < numCourses; i++) {
            if(order[i] == 0)
                return new int[]{};
            else
                result[order[i]-1] = i;
        }
        return result;
    }

    private boolean isPossible(int node, HashMap<Integer, List<Integer>> map, int[] visited, int[] order) {
        visited[node] = 1;
        List<Integer> dependencies = map.get(node);
        if (dependencies != null && !dependencies.isEmpty()) {
            for (Integer dependency : dependencies) {
                if(order[dependency] == 0) {
                    if(visited[dependency] == 1)
                        return false;
                    if(!isPossible(dependency, map, visited, order))
                        return false;
                }
            }
        }
        order[node] = count++;
        return true;
    }
}
