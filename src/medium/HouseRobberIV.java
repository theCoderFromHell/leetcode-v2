package medium;

public class HouseRobberIV {
    public int minCapability(int[] nums, int k) {
        int size = nums.length;
        int highest = 0;
        for (int i = 0; i < size; i++)
            highest = Math.max(highest, nums[i]);
        int low = 1, high = highest;
        int mid;
        int result = Integer.MAX_VALUE;
        while (low <= high) {
            mid = (high - low)/2 + low;
            if (works(nums, size, k, mid)) {
                result = Math.min(result, mid);
                high = mid - 1;
            } else
                low = mid + 1;
        }
        return result;
    }

    private boolean works(int[] nums, int size, int k, int target) {
        int count = 0;
        int i = 0;
        while (i < size) {
            if (nums[i++] <= target) {
                count++;
                if (count == k)
                    return true;
                i++;
            }
        }
        return count == k;
    }

    public static void main(String[] args) {
        HouseRobberIV H = new HouseRobberIV();
        System.out.println(H.minCapability(new int[]{2,3,5,9}, 2));
        System.out.println(H.minCapability(new int[]{2,7,9,3,1}, 2));
    }
}
