package hard;

// https://leetcode.com/problems/reverse-pairs/
public class ReversePairs {
    public int reversePairs(int[] nums) {
        int size = nums.length;
        return sortAndCount(nums, 0, size-1);
    }

    private int sortAndCount(int[] nums, int start, int end) {
        if (start >= end)
            return 0;
        int mid = start + (end - start)/2;
        int count = 0;
        count += sortAndCount(nums, start, mid);
        count += sortAndCount(nums, mid+1, end);
        count += countCrossPairs(nums, start, mid, end);
        merge(nums, start, mid, end);
        return count;
    }

    private void merge(int[] nums, int start, int mid, int end) {
        int[] temp = new int[end - start + 1];
        int i = start, j = mid + 1, k = 0;
        while (i <= mid && j <= end) {
            if (nums[i] <= nums[j])
                temp[k++] = nums[i++];
            else
                temp[k++] = nums[j++];
        }
        while (i <= mid)
            temp[k++] = nums[i++];
        while (j <= end)
            temp[k++] = nums[j++];
        System.arraycopy(temp, 0, nums, start, temp.length);
    }

    private int countCrossPairs(int[] nums, int start, int mid, int end) {
        int count = 0;
        int j = mid+1;
        for (int i = start; i <= mid; i++) {
            while (j <= end && nums[i] > 2L * nums[j])
                j++;
            count += (j - (mid + 1));
        }
        return count;
    }

    /*
     * Revision Note — Reverse Pairs (Hard)
     *
     * Pattern: Merge sort with a counting sweep before the merge
     *
     * Key Insight: Every pair (i,j) with i<j is either wholly in the left half, wholly in
     * the right half, or crosses. The two recursive calls cover the first two; the cross
     * case is counted with two pointers while both halves are still SORTED but separate.
     * Since every left index precedes every right index, i<j holds automatically.
     *
     * Gotchas:
     * - 2L * nums[j], never 2 * nums[j]. nums[j] reaches 2^31-1, so the int multiply wraps
     *   negative and the threshold collapses. Discriminating case: [1, 2000000000] -> 0,
     *   but the int version returns 1
     * - Count BEFORE merge. After merging, the two sorted runs become one and the
     *   two-pointer sweep has nothing to walk
     * - The while loop needs `j <= end` FIRST so short-circuit stops the array access;
     *   without it j walks off the segment
     * - j is declared OUTSIDE the i loop and never resets — that monotonicity is what makes
     *   the sweep O(n) per level instead of O(n^2). It holds because the left half is sorted,
     *   so a larger nums[i] can only admit more j's
     * - Strict >, not >=. And merge compares plain nums[i] <= nums[j] with NO doubling —
     *   two different comparisons, easy to cross-contaminate
     *
     * Template:
     *   sortAndCount(lo, hi):
     *     if lo >= hi: return 0
     *     mid = lo + (hi-lo)/2
     *     c = sortAndCount(lo,mid) + sortAndCount(mid+1,hi) + countCross(lo,mid,hi)
     *     merge(lo,mid,hi); return c
     *   countCross: j = mid+1
     *     for i in lo..mid: while j<=hi && nums[i] > 2L*nums[j]: j++
     *                       count += j - (mid+1)
     */
    public static void main(String[] args) {
        ReversePairs R = new ReversePairs();

        System.out.println("Test 1: " + R.reversePairs(new int[]{1, 3, 2, 3, 1})   + " (Expected: 2)");
        System.out.println("Test 2: " + R.reversePairs(new int[]{2, 4, 3, 5, 1})   + " (Expected: 3)");
        System.out.println("Test 3: " + R.reversePairs(new int[]{1})               + " (Expected: 0)"); // single element
        System.out.println("Test 4: " + R.reversePairs(new int[]{1, 2, 3, 4, 5})   + " (Expected: 0)"); // ascending, no pairs
        System.out.println("Test 5: " + R.reversePairs(new int[]{5, 4, 3, 2, 1})   + " (Expected: 4)"); // descending
        System.out.println("Test 6: " + R.reversePairs(new int[]{1, 2000000000})   + " (Expected: 0)"); // 2*nums[j] overflows int
        System.out.println("Test 7: " + R.reversePairs(new int[]{-5, -1})          + " (Expected: 0)"); // negatives: -5 > -2 is false
    }
}
