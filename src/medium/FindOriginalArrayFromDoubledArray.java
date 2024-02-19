package medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindOriginalArrayFromDoubledArray {
    public static int[] findOriginalArray(int[] changed) {
        if (changed.length % 2 == 1)
            return new int[]{};
        int N = changed.length;
        Arrays.sort(changed);
        int[] result = new int[N/2];
        int index = 0;
        boolean[] hash = new boolean[N];
        for (int i = 0; i < N; i++) {
            if (changed[i] % 2 == 1 || !hash[i]) {
                int idx = search(changed, i+1, N-1, 2 * changed[i], hash);
                if (idx == -1)
                    return new int[]{};
                else {
                    hash[idx] = true;
                    result[index++] = changed[i];
                }
            }
        }
        if (index == N/2)
            return result;
        return new int[]{};
    }

    private static int search(int[] changed, int start, int end, int key, boolean[] hash) {
        int mid;
        while (start <= end) {
            mid = start + (end-start)/2;
            if (!hash[mid] && key == changed[mid]) {
                int idx = search(changed, start, mid-1, key, hash);
                if (idx != -1)
                    return idx;
                return mid;
            }
            else if (key < changed[mid])
                end = mid-1;
            else
                start = mid+1;
        }
        return -1;
    }

    public static void main(String[] args) {
        //System.out.println(findOriginalArray(new int[]{1,3,4,2,6,8}));
        System.out.println(findOriginalArray(new int[]{0,0,0,0}));
    }
}
