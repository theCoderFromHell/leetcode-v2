package medium;

public class SumOfSquareNumbers {
    public boolean judgeSquareSum(int c) {
        if (c == 0)
            return true;
        int high = (int) Math.sqrt(c);
        if (high * high == c)
            return true;
        int low = (int) Math.sqrt(c - high * high);
        long sum;
        while (low <= high) {
            sum = (long) low * low + (long) high * high;
            if (sum == c)
                return true;
            if (sum < c) {
                low = (int) Math.sqrt(c - high * high);
                sum = (long) low * low + (long) high * high;
                if (sum == c)
                    return true;
                low++;
            }
            else
                high--;
        }
        return false;
    }

    public static void main(String[] args) {
        SumOfSquareNumbers S = new SumOfSquareNumbers();
        System.out.println(S.judgeSquareSum(2147483600));
        System.out.println(S.judgeSquareSum(5));
        System.out.println(S.judgeSquareSum(3));
        System.out.println(S.judgeSquareSum(4));
        System.out.println(S.judgeSquareSum(169));
    }
}
