package medium;

public class GreatestSumDivisibleByThree {
    public int maxSumDivThree(int[] nums) {
        int size = nums.length;
        Integer zero = 0, one = null, two = null;
        for (int i = 0; i < size; i++) {
            int number = nums[i];
            if (number % 3 == 0) {
                zero += number;
                if (one != null)
                    one += number;
                if (two != null)
                    two += number;
            } else if (number % 3 == 1) {
                Integer temp = two;
                if (one != null)
                    two = two == null ? one + number : Math.max(two, one + number);
                one = one == null ? zero + number : Math.max(one, zero + number);
                if (temp != null)
                    zero = Math.max(zero, temp + number);
            } else {
                Integer temp = one;
                if (two != null)
                    one = one == null ? two + number : Math.max(one, two + number);
                two = two == null ? zero + number : Math.max(two, zero + number);
                if (temp != null)
                    zero = Math.max(zero, temp + number);
            }
        }
        return zero;
    }

    public static void main(String[] args) {
        GreatestSumDivisibleByThree G = new GreatestSumDivisibleByThree();
        System.out.println(G.maxSumDivThree(new int[]{13,21,7,27,40,18,37,7,31,5}));
        System.out.println(G.maxSumDivThree(new int[]{3,6,5,1,8}));
        System.out.println(G.maxSumDivThree(new int[]{4}));
        System.out.println(G.maxSumDivThree(new int[]{1,2,3,4,4}));
    }
}
