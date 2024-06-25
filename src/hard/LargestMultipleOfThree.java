package hard;

import java.util.Arrays;

public class LargestMultipleOfThree {

    public String largestMultipleOfThree(int[] digits) {
        int sumOfDigits = 0;
        int[] digitCount = new int[10];
        for (int digit : digits) {
            sumOfDigits += digit;
            digitCount[digit]++;
        }
        if (sumOfDigits == 0)
            return "0";
        int[] first;
        int[] second;
        while (sumOfDigits > 0 && sumOfDigits % 3 != 0) {
            boolean deducted = false;
            if (sumOfDigits % 3 == 1) {
                first = new int[]{1, 4, 7};
                second = new int[]{2, 5, 8};
            } else {
                first = new int[]{2, 5, 8};
                second = new int[]{1, 4, 7};
            }
            for (int value : first) {
                if (!deducted && digitCount[value] > 0) {
                    digitCount[value]--;
                    sumOfDigits -= value;
                    deducted = true;
                }
            }
            for (int value : second) {
                if (!deducted && digitCount[value] > 0) {
                    digitCount[value]--;
                    sumOfDigits -= value;
                    deducted = true;
                }
            }
            if (!deducted)
                break;
        }
        if (sumOfDigits == 0 && digitCount[0] > 0)
            return "0";
        if (sumOfDigits == 0)
            return  "";
        StringBuilder sb = new StringBuilder();
        for (int i = 9; i >= 0; i--) {
            while (digitCount[i] > 0) {
                sb.append((char) (i + '0'));
                digitCount[i]--;
            }
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        LargestMultipleOfThree L = new LargestMultipleOfThree();
        System.out.println(L.largestMultipleOfThree(new int[]{8,6,7,1,0}));
        System.out.println(L.largestMultipleOfThree(new int[]{8,1,7,6,7}));
        System.out.println(L.largestMultipleOfThree(new int[]{8,1,9}));
        System.out.println(L.largestMultipleOfThree(new int[]{3,9,8,1,9,5,0,4,2,7,5}));
    }
}
