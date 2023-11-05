package medium;

import java.util.Objects;

public class MinimumNumberOfSwapsToMakeTheStringBalanced {

    public static int minSwaps(String s) {
        if (Objects.isNull(s) || s.isEmpty() || s.isBlank())
            return 0;
        int closingExtra = 0, maxClosingExtra = Integer.MIN_VALUE;
        for (char c : s.toCharArray()) {
            if (c == ']')
                maxClosingExtra = Integer.max(maxClosingExtra, ++closingExtra);
            else
                closingExtra--;
        }
        if (maxClosingExtra <= 0)
            return 0;
        return (maxClosingExtra + 1)/2;
    }

    public static void main(String[] args) {
        System.out.println(minSwaps("][]["));
        System.out.println(minSwaps("]]][[["));
        System.out.println(minSwaps("[]"));

    }
}
