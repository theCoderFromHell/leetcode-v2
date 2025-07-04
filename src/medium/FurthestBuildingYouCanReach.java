package medium;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FurthestBuildingYouCanReach {
    public int furthestBuilding(int[] heights, int bricks, int ladders) {
        int size = heights.length;
        List<int[]> diff = new ArrayList<>();
        diff.add(new int[]{0,0});
        for (int i = 1; i < size; i++)
            diff.add(new int[]{ heights[i] - heights[i-1], i});
        diff.sort(Comparator.comparingInt(a -> a[0]));
        int low = 0, high = size-1;
        int mid;
        int farthestBuilding = 0;
        while (low <= high) {
            mid = low + (high - low)/2;
            if (canReach(mid, diff, bricks, ladders)) {
                farthestBuilding = Math.max(farthestBuilding, mid);
                low = mid + 1;
            } else
                high = mid - 1;
        }
        return farthestBuilding;
    }

    private boolean canReach(int mid, List<int[]> diff, int bricks, int ladders) {
        int size = diff.size();
        for (int i = 1; i < size; i++) {
            int climb = diff.get(i)[0];
            int building = diff.get(i)[1];
            if (building > mid || climb <= 0)
                continue;
            if (bricks >= climb)
                bricks -= climb;
            else if (ladders > 0)
                ladders--;
            else
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        FurthestBuildingYouCanReach F = new FurthestBuildingYouCanReach();
        System.out.println(F.furthestBuilding(new int[]{4,2,7,6,9,14,12}, 5, 1));
        System.out.println(F.furthestBuilding(new int[]{4,12,2,7,3,18,20,3,19}, 10, 2));
        System.out.println(F.furthestBuilding(new int[]{14,3,19,3}, 17, 0));
    }
}
