package medium;

import java.util.ArrayList;
import java.util.List;

public class LargestTimeForGivenDigits {
    public String largestTimeFromDigits(int[] arr) {
        List<int[]> times = new ArrayList<>();
        generateTimes(arr, arr.length, 0, times);
        times.sort((a, b) -> {
            if (a[0] > b[0])
                return -1;
            else if (b[0] > a[0] )
                return 1;
            else {
                if (a[1] > b[1])
                    return -1;
                else if (b[1] > a[1])
                    return 1;
                else
                    return 0;
            }
        });
        if (times.isEmpty())
            return "";
        return ((times.getFirst()[0] < 10) ? ("0" + times.getFirst()[0]) : times.getFirst()[0])  + ":" +
                ((times.getFirst()[1] < 10) ? ("0" + times.getFirst()[1]) : times.getFirst()[1]);

    }

    private void generateTimes(int[] arr, int length, int index, List<int[]> times) {
        if (index == length) {
            if (10*arr[0] + arr[1] <= 23 && 10*arr[2] + arr[3] <= 59)
                times.add(new int[]{10*arr[0] + arr[1], 10*arr[2] + arr[3]});
            return;
        }
        for (int i = index; i < length; i++) {
            swap(arr, index, i);
            generateTimes(arr, length, index+1, times);
            swap(arr, index, i);
        }
    }

    private void swap(int[] arr, int index, int i) {
        int temp = arr[index];
        arr[index] = arr[i];
        arr[i] = temp;
    }

    public static void main(String[] args) {
        LargestTimeForGivenDigits L = new LargestTimeForGivenDigits();
        System.out.println(L.largestTimeFromDigits(new int[]{1,2,3,4}));
        System.out.println(L.largestTimeFromDigits(new int[]{5,5,5,5}));
    }
}
