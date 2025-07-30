package medium;

import java.util.Arrays;
import java.util.HashSet;

public class FindThePrefixCommonArrayOfTwoArrays {
    public int[] findThePrefixCommonArray(int[] A, int[] B) {
        int size = A.length;
        HashSet<Integer> a = new HashSet<>();
        HashSet<Integer> b = new HashSet<>();
        HashSet<Integer> common = new HashSet<>();
        int[] result = new int[size];
        int index = 0;
        int count = 0;
        while (index < size) {
            a.add(A[index]);
            b.add(B[index]);
            if (a.contains(B[index]) && !common.contains(B[index])) {
                count++;
                common.add(B[index]);
            }
            if (b.contains(A[index]) && !common.contains(A[index])) {
                count++;
                common.add(A[index]);
            }
            result[index] = count;
            index++;
        }
        return result;
    }

    public static void main(String[] args) {
        FindThePrefixCommonArrayOfTwoArrays F = new FindThePrefixCommonArrayOfTwoArrays();
        System.out.println(Arrays.toString(F.findThePrefixCommonArray(new int[]{1,3,2,4}, new int[]{3,1,2,4})));
        System.out.println(Arrays.toString(F.findThePrefixCommonArray(new int[]{2,3,1}, new int[]{3,1,2})));
    }
}
