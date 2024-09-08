package hard;

public class MedianOfTwoSortedArrays {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int M = nums1.length;
        int N = nums2.length;
        if (M > N)
            return findMedianSortedArrays(nums2, nums1);
        int start = 0, end = M;
        int total = (M + N + 1)/2;
        while (start <= end) {
            int partition1 = (start + end) / 2;
            int partition2 = total - partition1;
            int aLeft = (partition1 == 0) ? Integer.MIN_VALUE : nums1[partition1-1];
            int aRight = (partition1 == M) ? Integer.MAX_VALUE : nums1[partition1];
            int bLeft = (partition2 == 0) ? Integer.MIN_VALUE : nums2[partition2-1];
            int bRight = (partition2 == N) ? Integer.MAX_VALUE : nums2[partition2];

            if (aLeft <= bRight && bLeft <= aRight) {
                if ((M + N) % 2 == 0)
                    return (Math.max(aLeft, bLeft) + Math.min(aRight, bRight)) / 2.0;
                else
                    return Math.max(aLeft, bLeft);
            } else if (aLeft > bRight)
                end = partition1 - 1;
            else
                start = partition1 + 1;
        }
        return 0.0;
    }

    public static void main(String[] args) {
        MedianOfTwoSortedArrays M = new MedianOfTwoSortedArrays();
        //System.out.println(M.findMedianSortedArrays(new int[]{1,3}, new int[]{2}));
        //System.out.println(M.findMedianSortedArrays(new int[]{1,2}, new int[]{3,4}));
        System.out.println(M.findMedianSortedArrays(new int[]{1,7,8,10}, new int[]{2,5, 17, 19,26,27, 34, 39}));
    }
}
