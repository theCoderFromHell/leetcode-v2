package medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CourseScheduleIV {
    public List<Boolean> checkIfPrerequisite(int numCourses, int[][] prerequisites, int[][] queries) {
        boolean[][] matrix = new boolean[numCourses][numCourses];
        for (int i = 0; i < numCourses; i++)
            Arrays.fill(matrix[i], false);
        for  (int[] prerequisite : prerequisites)
            matrix[prerequisite[1]][prerequisite[0]] = true;
        for (int k = 0; k < numCourses; k++) {
            for (int i = 0; i < numCourses; i++) {
                for (int j = 0; j < numCourses; j++) {
                    if (matrix[i][k] && matrix[k][j])
                        matrix[i][j] = true;
                }
            }
        }
        List<Boolean> result = new ArrayList<>();
        for (int[] query : queries)
            result.add(matrix[query[1]][query[0]]);
        return result;
    }

    public static void main(String[] args) {
        CourseScheduleIV C = new CourseScheduleIV();
        System.out.println(C.checkIfPrerequisite(5, new int[][]{{0,1},{1,2},{2,3},{3,4}}, new int[][]{{0,4},{4,0},{1,3},{3,0}}));
        System.out.println(C.checkIfPrerequisite(13, new int[][]{{2,1},{2,7},{2,0},{2,10},{2,11},{1,7},{1,0},{1,9},{1,4},{1,11},{7,3},{7,9},{7,4},{7,11},{7,8},{3,6},{3,12},{3,5},{6,10},{6,8},{0,4},{12,9},{12,8},{9,4},{9,11},{9,8},{9,5},{10,8},{4,8}}, new int[][]{{12,11},{11,1},{10,12},{9,10},{10,11},{11,12},{2,7},{6,8},{3,2},{9,5},{8,7},{1,4},{3,12},{9,6},{4,3},{11,4},{5,7},{3,9},{3,1},{8,12},{5,12},{0,8},{10,5},{10,11},{12,11},{12,9},{5,4},{11,5},{12,10},{11,0},{6,10},{11,7},{8,10},{2,1},{3,4},{8,7},{11,6},{9,11},{1,4},{10,8},{7,1},{8,7},{9,7},{5,1},{8,10},{11,8},{8,12},{9,12},{12,11},{6,12},{12,11},{6,10},{9,12},{8,10},{8,11},{8,5},{7,9},{12,11},{11,12},{8,0},{12,11},{7,0},{8,7},{5,11},{11,8},{1,9},{4,10},{11,6},{10,12}}));
    }
}
