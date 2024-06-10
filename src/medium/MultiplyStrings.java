package medium;

import java.util.ArrayList;
import java.util.List;

public class MultiplyStrings {
    public String multiply(String num1, String num2) {
        if(isZero(num1) || isZero(num2))
            return "0";
        int M = num1.length();
        int N = num2.length();
        int zeros = 0;
        int maxLength = 0;
        List<String> numbers = new ArrayList<>();
        for (int i = M-1; i >= 0; i--) {
            int a = num1.charAt(i) - '0';
            int remainder = 0;
            StringBuilder num = new StringBuilder();
            for (int j = N-1; j >= 0; j--) {
                int b = num2.charAt(j) - '0';
                num.append((a * b + remainder) % 10);
                remainder = (a * b + remainder)/10;
            }
            if (remainder > 0)
                num.append(remainder);
            int m = zeros;
            while (m > 0) {
                num.insert(0, "0");
                m--;
            }
            zeros++;
            numbers.add(num.toString());
            maxLength = Math.max(maxLength, num.length());
        }
        return sumOfStrings(numbers, maxLength);
    }

    private String sumOfStrings(List<String> numbers, int maxLength) {
        StringBuilder res = new StringBuilder();
        int remainder = 0;
        for (int i = 0; i < maxLength; i++) {
            int sum = 0;
            for (String number : numbers) {
                int num = number.length() <= i ? 0 : number.charAt(i) - '0';
                sum += num;
            }
            res.append((sum + remainder)%10);
            remainder = (sum + remainder)/10;
        }
        if (remainder > 0)
            res.append(remainder);
        return res.reverse().toString();
    }

    private boolean isZero(String num1) {
        return num1 == null || num1.isBlank() || num1.isEmpty() || num1.equals("0");
    }
}
