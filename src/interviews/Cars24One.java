package interviews;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Cars24One {
    public static boolean isPossibleToSchedule(int[][] dependencies, int numOfCourses) {
        if(dependencies == null || dependencies.length == 0 || dependencies[0].length == 0)
            return false;
        int rows = dependencies.length;
        HashMap<Integer, List<Integer>> dependenciesMap = new HashMap<>();
        for(int i = 0; i < rows; i++) {
            List<Integer> list = dependenciesMap.getOrDefault(dependencies[i][0], new ArrayList<>());
            list.add(dependencies[i][1]);
            dependenciesMap.put(dependencies[i][0], list);
        }
        HashSet<Integer> included = new HashSet<>();
        for(int i = 0; i < numOfCourses; i++) {
            if(!dependenciesMap.containsKey(i))
                included.add(i);
        }
       for(int i = 0; i < numOfCourses ; i++) {
           HashSet<Integer> current = new HashSet<>();
           if(cycleExists(dependenciesMap, current, included, i))
               return false;
       }
       return true;
    }
    private static boolean cycleExists(HashMap<Integer, List<Integer>> dependenciesMap, HashSet<Integer> current,
                                       HashSet<Integer> included, int node) {
        if(included.contains(node))
            return false;
        List<Integer> dependencies  =  dependenciesMap.get(node);
        current.add(node);
        for (int dependentNode : dependencies) {
            if(current.contains(dependentNode))
                return true;
             boolean cycleCheck = cycleExists(dependenciesMap, current, included, dependentNode);
             if(cycleCheck)
                 return true;
             current.remove(dependentNode);
        }
        return false;
    }


    public static void main(String[] args) {
        System.out.println(isPossibleToSchedule(new int[][]{
                {0,1},
                {1,2},
                {2,4},
                {4,3},
                {3,1}
        }, 5));
    }
}
