package medium;

import java.util.ArrayList;
import java.util.List;

public class Triangle {
    public static int minimumTotal(List<List<Integer>> triangle) {
        int N = triangle.size();
        if (N == 1)
            return triangle.get(0).get(0);
        int result = Integer.MAX_VALUE;
        for (int i = 1; i < N; i++) {
            int size = triangle.get(i).size();
            for (int j = 0; j < size; j++) {
                int value = triangle.get(i).get(j);
                if (j == 0)
                    triangle.get(i).set(j, value + triangle.get(i-1).get(0));
                else if (j == size-1)
                    triangle.get(i).set(j, value + triangle.get(i-1).get(size-2));
                else
                    triangle.get(i).set(j, value + Math.min(triangle.get(i-1).get(j-1), triangle.get(i-1).get(j)));
                if (i == N-1)
                    result = Math.min(result, triangle.get(i).get(j));
            }
        }
        return result;
    }
    public static void main(String[] args) {
        ArrayList<List<Integer>> triangle = new ArrayList<>();
        List<Integer> triangle1 = List.of(2);
        List<Integer> triangle2 = List.of(3,4);
        List<Integer> triangle3 = List.of(6,5,7);
        List<Integer> triangle4 = List.of(4,1,8,3);
        triangle.add(new ArrayList<>(triangle1));
        triangle.add(new ArrayList<>(triangle2));
        triangle.add(new ArrayList<>(triangle3));
        triangle.add(new ArrayList<>(triangle4));
        int result = minimumTotal(triangle);

    }
}
