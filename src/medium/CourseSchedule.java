package medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CourseSchedule {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        if(numCourses <= 0 || prerequisites == null || prerequisites.length == 0)
            return true;
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        int length = prerequisites.length;
        for (int i = 0; i < length; i++) {
            List<Integer> list = map.getOrDefault(prerequisites[i][0], new ArrayList<>());
            list.add(prerequisites[i][1]);
            map.put(prerequisites[i][0], list);
        }
        int[] included = new int[numCourses];
        for (int i = 0; i < numCourses; i++)
            if (!map.containsKey(i))
                included[i] = 1;
        for (Integer node : map.keySet()) {
            if (included[node] == 0) {
                int[] visited = new int[numCourses];
                if (!isPossible(node, map, included, visited))
                    return false;
                else
                    included[node] = 1;
            }
        }
        for (int i = 0; i < numCourses; i++) {
            if(included[i] == 0)
                return false;
        }
        return true;
    }

    private boolean isPossible(int node, HashMap<Integer, List<Integer>> map, int[] included, int[] visited) {
        visited[node] = 1;
        List<Integer> dependencies = map.get(node);
        for (Integer dependency : dependencies) {
            if(included[dependency] == 0) {
                if(visited[dependency] == 1)
                    return false;
                if(!isPossible(dependency, map, included, visited))
                    return false;
            }
        }
        included[node] = 1;
        return true;
    }
}
