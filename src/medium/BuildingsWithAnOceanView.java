package medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BuildingsWithAnOceanView {
    public int[] findBuildings(int[] heights) {
        int size = heights.length;
        int currMax = 0;
        List<Integer> buildings = new ArrayList<>();
        for (int i = size-1; i >= 0; i--) {
            if (heights[i] > currMax) {
                buildings.add(i);
                currMax = heights[i];
            }
        }
        size = buildings.size();
        int[] result = new int[size];
        for (int i = 0; i < size; i++)
            result[i] = buildings.get(size - 1 - i);
        return result;
    }

    public static void main(String[] args) {
        BuildingsWithAnOceanView B = new BuildingsWithAnOceanView();
        System.out.println(Arrays.toString(B.findBuildings(new int[]{4,2,3,1})));
        System.out.println(Arrays.toString(B.findBuildings(new int[]{4,3,2,1})));
        System.out.println(Arrays.toString(B.findBuildings(new int[]{1,3,2,4})));
    }
}
