package medium;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConstructSmallestNumberFromDIString {
    public String smallestNumber(String pattern) {
        int size = pattern.length();
        List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        for (int i = 0; i < size; i++) {
            numbers.add(i+2);
        }
        int start;
        int end = 0;
        while (end < size) {
            if (pattern.charAt(end) == 'D') {
                start = end;
                while (end < size && pattern.charAt(end) == 'D') {
                    end++;
                }
                Collections.reverse(numbers.subList(start, end + 1));
            }
            else
                end++;
        }
        StringBuilder sb = new StringBuilder();
        for (int number : numbers)
            sb.append(number);
        return sb.toString();
    }

    public static void main(String[] args) {
        ConstructSmallestNumberFromDIString C = new ConstructSmallestNumberFromDIString();
        System.out.println(C.smallestNumber("IIIDIDDD"));
        System.out.println(C.smallestNumber("DDD"));
        System.out.println(C.smallestNumber(""));
    }
}
