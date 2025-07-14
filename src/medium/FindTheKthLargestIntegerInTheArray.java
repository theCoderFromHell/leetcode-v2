package medium;

import java.util.Arrays;

public class FindTheKthLargestIntegerInTheArray {
    public String kthLargestNumber(String[] nums, int k) {
        int size = nums.length;
        Arrays.sort(nums, (o1, o2) -> {
            int size1 = o1.length();
            int size2 = o2.length();
            if (size1 != size2)
                return (size1 - size2);
            int index1 = 0, index2 = 0;
            while (index1 < size1 && index2 < size2) {
                if (o1.charAt(index1) < o2.charAt(index2))
                    return -1;
                else if (o1.charAt(index1) > o2.charAt(index2))
                    return 1;
                index1++;
                index2++;
            }
            return 0;
        });
        return nums[size - k];
    }

    public static void main(String[] args) {
        FindTheKthLargestIntegerInTheArray F = new FindTheKthLargestIntegerInTheArray();
        System.out.println(F.kthLargestNumber(new String[]{"3","6","7","10"}, 4));
        System.out.println(F.kthLargestNumber(new String[]{"2","21","12","1"}, 3));
        System.out.println(F.kthLargestNumber(new String[]{"0","0"}, 2));
    }
}
