package medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RearrangeArrayElementsBySign {
    public int[] rearrangeArrayV1(int[] nums) {
        int size = nums.length;
        List<Integer> positives = new ArrayList<>();
        List<Integer> negatives = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (nums[i] > 0)
                positives.add(nums[i]);
            else
                negatives.add(nums[i]);
        }
        boolean positiveFlag = true;
        int p = 0, n = 0;
        for (int i = 0; i < size; i++) {
            if (positiveFlag)
                nums[i] = positives.get(p++);
            else
                nums[i] = negatives.get(n++);
            positiveFlag = !positiveFlag;
        }
        return nums;
    }

    public int[] rearrangeArray(int[] nums) {
        int size = nums.length;
        int[] result = new int[size];
        int pIndex = 0;
        int nIndex = 1;
        for (int i = 0; i < size; i++) {
            if (nums[i] > 0) {
                result[pIndex] = nums[i];
                pIndex += 2;
            } else {
                result[nIndex] = nums[i];
                nIndex += 2;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        RearrangeArrayElementsBySign R = new RearrangeArrayElementsBySign();
        System.out.println(Arrays.toString(R.rearrangeArray(new int[]{3,1,-2,-5,2,-4})));
        System.out.println(Arrays.toString(R.rearrangeArray(new int[]{-1,1})));
        System.out.println(Arrays.toString(R.rearrangeArray(new int[]{-1, -1, 1, 1})));
    }
}
