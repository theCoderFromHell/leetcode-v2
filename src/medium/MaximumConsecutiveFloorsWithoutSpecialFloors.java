package medium;

import java.util.Arrays;

public class MaximumConsecutiveFloorsWithoutSpecialFloors {
    public int maxConsecutive(int bottom, int top, int[] special) {
        int size = special.length;
        int[] temp = new int[size + 2];
        temp[0] = bottom;
        temp[size+1] = top;
        for (int i = 1; i <= size; i++)
            temp[i] = special[i - 1];
        Arrays.sort(temp);
        int maxGap = Math.max(temp[1] - temp[0], temp[size + 1] - temp[size]);
        for (int i = 2; i < size + 1; i++)
            maxGap = Math.max(maxGap, temp[i] - temp[i - 1] - 1);
        return maxGap;
    }

    public static void main(String[] args) {
        MaximumConsecutiveFloorsWithoutSpecialFloors M = new MaximumConsecutiveFloorsWithoutSpecialFloors();
        System.out.println(M.maxConsecutive(2,9, new int[]{4,6}));
        System.out.println(M.maxConsecutive(6,8, new int[]{7,6,8}));
    }
}
