package medium;


import java.util.Arrays;

public class RangeAddition {
    public int[] getModifiedArray(int length, int[][] updates) {
        int[] line = new int[100002];
        int size = updates.length;
        for (int i = 0; i < size; i++) {
            int start = updates[i][0];
            int end = updates[i][1];
            int increment = updates[i][2];
            line[start] += increment;
            line[end+1] -= increment;
        }
        int[] result = new int[length];
        int count = 0;
        for (int i = 0; i < length; i++) {
            count += line[i];
            result[i] = count;
        }
        return result;
    }

    public static void main(String[] args) {
        RangeAddition R = new RangeAddition();
        System.out.println(Arrays.toString(R.getModifiedArray(5, new int[][]{{1, 3, 2}, {2, 4, 3}, {0, 2, -2}})));
        System.out.println(Arrays.toString(R.getModifiedArray(10, new int[][]{{2,4,6},{5,6,8},{1,9,-4}})));
    }
}
