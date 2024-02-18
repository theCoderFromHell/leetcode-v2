package medium;

public class DivideTwoIntegers {
    public static int divide(int dividend, int divisor) {
        if (dividend == Integer.MIN_VALUE) {
            if (divisor == 1)
                return Integer.MIN_VALUE;
            else if (divisor == -1)
                return Integer.MAX_VALUE;
        }
        int sign = (dividend > 0 && divisor > 0) || (dividend < 0 && divisor < 0) ? 1 : -1;
        dividend = Math.abs(dividend);
        divisor = Math.abs(divisor);
        if (divisor == 1)
            return sign == 1 ? dividend : -dividend;
        int quotient = 0;
        while (dividend - divisor >= 0) {
            dividend -= divisor;
            quotient++;
        }
        return sign == 1 ? quotient : -quotient;
    }

    public static void main(String[] args) {
        System.out.println(divide(10, 3));
        System.out.println(divide(7, -3));
    }
}
