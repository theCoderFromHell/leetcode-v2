package medium;

public class FindFirstAndLastPositionOfElementInSortedArray {
    public static int[] searchRange(int[] nums, int target) {
        int size = nums.length;
        int start = 0;
        int end = size-1;
        while (start <= end) {
            int mid = start + (end-start)/2;
            if (nums[mid] == target) {
                int x = mid, y = mid;
                while (x > 0 && nums[x - 1] == target)
                    x--;
                while (y < size - 1 && nums[y + 1] == target)
                    y++;
                return new int[]{x, y};
            } else if (target < nums[mid])
                end = mid-1;
            else
                start = mid+1;
        }
        return new int[]{-1, -1};
    }

    public static void main(String[] args) {
        int[] result = searchRange(new int[] {5,7,7,8,8,10}, 8);
        result = searchRange(new int[] {5,7,7,8,8,10}, 6);
        result = searchRange(new int[] {}, 0);
    }
}
