package medium;

import java.util.HashMap;

public class LargestPalindromicNumber {
    public String largestPalindromic(String num) {
        if (num == null || num.isEmpty() || num.isBlank())
            return "";
        HashMap<Integer,Integer> digitCount = new HashMap<>();
        int[] digitsEvenCount = new int[10];
        int[] digitsOddCount = new int[10];
        for (char digit : num.toCharArray())
            digitCount.put(digit - '0', digitCount.getOrDefault(digit - '0', 0) + 1);

        for (int i = 0; i <= 9; i++) {
            int count = digitCount.getOrDefault(i, 0);
            if (count > 0) {
                digitsEvenCount[i] = count/2;
                if (count % 2 == 1)
                    digitsOddCount[i] = 1;
            }
        }
        String middle = "";
        for (int i = 9; i >= 0 ; i--) {
            if (digitsOddCount[i] > 0) {
                middle = String.valueOf((char) (i + '0'));
                break;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 9; i >= 0; i--) {
            while (digitsEvenCount[i] > 0) {
                if (!sb.isEmpty() || i != 0)
                    sb.append((char) (i + '0'));
                digitsEvenCount[i]--;
            }
        }
        StringBuilder reverse = new StringBuilder(sb).reverse();
        String result = new String(sb.append(middle).append(reverse));
        if (result.isEmpty() || result.isBlank())
            return "0";
        return result;
    }

    public static void main(String[] args) {
        LargestPalindromicNumber L = new LargestPalindromicNumber();
        System.out.println(L.largestPalindromic("444947137"));
        System.out.println(L.largestPalindromic("0000"));
    }
}
