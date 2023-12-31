package medium;

public class FactorialTrailingZeroes {
    public static int trailingZeroes(int n) {
        int result = 0;
        while (n > 0) {
            result += (n/5);
            n /= 5;
        }
        return result;
    }
    public static void main(String[] args) {
        System.out.println(trailingZeroes(4));
        System.out.println(trailingZeroes(5));
        System.out.println(trailingZeroes(10));
        System.out.println(trailingZeroes(100));


    }
}
