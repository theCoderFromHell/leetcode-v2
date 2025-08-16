package common;

import java.util.List;

public class Util {
    public static void printArray(int[] nums) {
        int N = nums.length;
        for (int i = 0; i < N; i++)
            System.out.print(nums[i] + " ");
        System.out.println();
    }

    public static <T> void printMatrix(List<List<T>> matrix) {
        if (matrix == null || matrix.isEmpty()) {
            System.out.println("(Empty matrix)");
            return;
        }
        int maxWidth = matrix.stream()
                .flatMap(List::stream)
                .map(obj -> obj == null ? 4 : obj.toString().length()) // length of "null" = 4
                .max(Integer::compareTo)
                .orElse(0);

        for (List<T> row : matrix) {
            StringBuilder sb = new StringBuilder();
            for (T element : row) {
                String value = (element == null) ? "null" : element.toString();
                sb.append(String.format("%-" + (maxWidth + 2) + "s", value));
            }
            System.out.println(sb.toString().stripTrailing());
        }
    }
}
