package medium;

public class MaximumCandiesAllocatedToKChildren {
    public int maximumCandies(int[] candies, long k) {
        int size = candies.length;
        int low = 1, high = 0, mid;
        int result = 0;
        for (int i = 0; i < size; i++)
            high = Math.max(high, candies[i]);
        while (low <= high) {
            mid = (high - low)/2 + low;
            if (works(candies, size, k, mid)) {
                result = Math.max(result, mid);
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return result;
    }

    private boolean works(int[] candies, int size, long k, int pileSize) {
        long count = 0;
        for (int i = 0; i < size; i++)
            count += (candies[i] / pileSize);
        return count >= k;
    }

    public static void main(String[] args) {
        MaximumCandiesAllocatedToKChildren M = new MaximumCandiesAllocatedToKChildren();
        System.out.println(M.maximumCandies(new int[]{5,8,6}, 3));
        System.out.println(M.maximumCandies(new int[]{2,5}, 11));
    }
}
