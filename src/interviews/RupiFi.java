package interviews;

/*
Array of unique integers, we have to find any local minima

arr = {1, 2, 6, 7, 9, 17, 5}



arr = {5, 4, 3, 2 }


 */


public class RupiFi {

    public static int getLocalMinima(int[] arr) {
        if(arr == null || arr.length == 0)
            return -1;
        if(arr.length == 1)
            return arr[0];
        if(arr.length == 2)
            return Math.min(arr[0], arr[1]);
        int length = arr.length;
        return localMinima(arr, 0, length-1);
    }

    private static int localMinima(int[] arr, int start, int end) {
        if(start <= end) {
            int mid = start + (end-start)/2;
            if(isLocalMinima(arr, mid, start, end))
                return arr[mid];

            if(mid > start && arr[mid-1] <= arr[mid])
                return localMinima(arr, start, mid-1);
            if(mid < end && arr[mid+1] <= arr[mid])
                return localMinima(arr, mid+1, end);
        }
        return -1;
    }

    private static boolean isLocalMinima(int[] arr, int mid, int start, int end) {
        if(mid > start && arr[mid-1] <= arr[mid])
            return false;
        if(mid < end && arr[mid+1] <= arr[mid])
            return false;
        return true;
    }

    public static void main(String[] args) {

    }

}
