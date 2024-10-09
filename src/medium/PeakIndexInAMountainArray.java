package medium;

public class PeakIndexInAMountainArray {
    public int peakIndexInMountainArray(int[] arr) {
        int N = arr.length;
        return solve(arr, 0, N-1, N);
    }

    private int solve(int[] arr, int start, int end, int size) {
        int mid = -1;
        while (start <= end) {
            mid = (end - start)/2 + start;
            if (0 < mid && arr[mid-1] < arr[mid] && mid < size-1 && arr[mid] > arr[mid+1])
                return mid;
            if (mid == 0 || (0 < mid && arr[mid-1] < arr[mid]))
                start = mid + 1;
            else if(mid == size-1 || (mid < size-1 && arr[mid] > arr[mid+1]))
                end = mid - 1;
        }
        return -1;
    }

    public static void main(String[] args) {
        PeakIndexInAMountainArray P = new PeakIndexInAMountainArray();
        System.out.println(P.peakIndexInMountainArray(new int[]{0,1,0}));
        System.out.println(P.peakIndexInMountainArray(new int[]{0,2,1,0}));
        System.out.println(P.peakIndexInMountainArray(new int[]{0,10,5,2}));
        System.out.println(P.peakIndexInMountainArray(new int[]{1,3,8,15,23,19,13,10,8,5,2,1}));
        System.out.println(P.peakIndexInMountainArray(new int[]{3,5,3,2,0}));
    }
}
