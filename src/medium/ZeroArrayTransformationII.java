package medium;

public class ZeroArrayTransformationII {
    public int minZeroArray(int[] nums, int[][] queries) {
        int N = nums.length;
        int Q = queries.length;
        int start = 0, end = Q;
        int minK = -1;
        while (start <= end) {
            int mid = start + (end - start)/2;
            if (works(nums, N, queries, mid)) {
                minK = (minK == -1) ? mid : Math.min(minK, mid);
                end = mid - 1;
            } else
                start = mid + 1;
        }
        return minK;
    }

    private boolean works(int[] nums, int size, int[][] queries, int k) {
        int[] criticalPoints = new int[size+1];
        for (int i = 0; i < k; i++) {
            int[] query = queries[i];
            criticalPoints[query[0]] += query[2];
            criticalPoints[query[1] + 1] -= query[2];
        }
        int total = 0;
        for (int i = 0; i < size; i++) {
            total += criticalPoints[i];
            if (nums[i] > total)
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        ZeroArrayTransformationII Z = new ZeroArrayTransformationII();
        System.out.println(Z.minZeroArray(new int[]{2,0,2}, new int[][]{{0,2,1},{0,2,1},{1,1,3}}));
        System.out.println(Z.minZeroArray(new int[]{4,3,2,1}, new int[][]{{1,3,2},{0,2,1}}));
    }
}
