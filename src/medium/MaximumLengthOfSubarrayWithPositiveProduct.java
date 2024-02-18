package medium;

public class MaximumLengthOfSubarrayWithPositiveProduct {
    public int getMaxLen(int[] nums) {
        int p = 0;
        int n = 0;
        int maxLength = 0;
        for (int value : nums) {
            if (value > 0) {
                p++;
                if (n > 0)
                    n++;
            }
            else if (value < 0) {
                int temp = p;
                p = (n > 0) ? n+1 : 0;
                n = (temp > 0) ? temp+1 : 1;
            } else {
                p = 0;
                n = 0;
            }
            maxLength = Math.max(maxLength, p);
        }
        return maxLength;
    }

    public static void main(String[] args) {

    }
}
