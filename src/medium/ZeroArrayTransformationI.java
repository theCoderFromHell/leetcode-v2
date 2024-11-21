package medium;

import java.util.ArrayList;
import java.util.List;

public class ZeroArrayTransformationI {
    public boolean isZeroArray(int[] nums, int[][] queries) {
        int N = nums.length;
        int Q = queries.length;
        int[] criticalPoints = new int[N+1];
        for (int i = 0; i < Q; i++) {
            int start = queries[i][0];
            int end = queries[i][1];
            criticalPoints[start]++;
            criticalPoints[end+1]--;
        }
        int ops = 0;
        for (int i = 0; i < N; i++) {
            ops += criticalPoints[i];
            if (nums[i] - ops > 0)
                return false;
        }
        return true;
    }

    public boolean isZeroArrayV2(int[] nums, int[][] queries) {
        int N = nums.length;
        int Q = queries.length;
        List<int[]> criticalPoints = new ArrayList<>();
        for (int i = 0; i < Q; i++) {
            criticalPoints.add(new int[]{queries[i][0], 1});
            criticalPoints.add(new int[]{queries[i][1] + 1, -1});
        }
        criticalPoints.sort((o1, o2) -> {
            if (o1[0] == o2[0])
                return (o1[1] - o2[1]);
            return (o1[0] - o2[0]);
        });
        int size = criticalPoints.size();
        int idx = 0;
        int value = 0;
        int prevIndex = -1;
        int[] operations = new int[N];
        while (idx < size) {
            int index = criticalPoints.get(idx)[0];
            for (int i = prevIndex+1; i < index ; i++) {
                operations[i] = (prevIndex == -1) ? 0 : operations[prevIndex];
            }
            int count = value + criticalPoints.get(idx)[1];
            while (idx+1 < size && criticalPoints.get(idx)[0] == criticalPoints.get(idx+1)[0]) {
                count += criticalPoints.get(idx + 1)[1];
                idx++;
            }
            if (index < N) {
                operations[index] = count;
                prevIndex = index;
            }
            value = count;
            idx++;
        }
        return allZeros(nums, N, operations);
    }

    private boolean allZeros(int[] nums, int size, int[] operations) {
        for (int i = 0; i < size; i++) {
            nums[i] = Math.max(0, nums[i] - operations[i]);
            if (nums[i] != 0)
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        ZeroArrayTransformationI Z = new ZeroArrayTransformationI();
        System.out.println(Z.isZeroArray(new int[]{1,0,1}, new int[][]{{0,2}}));
        System.out.println(Z.isZeroArray(new int[]{4,3,2,1}, new int[][]{{1,3},{0,2}}));
    }
}
