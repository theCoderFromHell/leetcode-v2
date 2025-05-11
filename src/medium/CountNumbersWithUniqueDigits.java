package medium;

public class CountNumbersWithUniqueDigits {
    public int countNumbersWithUniqueDigits(int n) {
        if (n == 0)
            return 1;
        if (n == 1)
            return 10;
        int total = 10;
        for (int digits = 2; digits <= n ; digits++) {
            int currProduct = 9;
            int curr = 9;
            int i = digits-1;
            while (i-- > 0) {
                currProduct *= curr;
                curr--;
            }
            total += currProduct;
        }
        return total;
    }

    public static void main(String[] args) {
        CountNumbersWithUniqueDigits C = new CountNumbersWithUniqueDigits();
        System.out.println(C.countNumbersWithUniqueDigits(0));
        System.out.println(C.countNumbersWithUniqueDigits(1));
        System.out.println(C.countNumbersWithUniqueDigits(2));
        System.out.println(C.countNumbersWithUniqueDigits(3));
        System.out.println(C.countNumbersWithUniqueDigits(4));
    }
}
