package medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class FindTheMostCommonResponse {
    public String findCommonResponse(List<List<String>> responses) {
        int size = responses.size();
        List<HashSet<String>> responsesByDay = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            HashSet<String> uniqueResponses = new HashSet<>(responses.get(i));
            responsesByDay.add(uniqueResponses);
        }
        int maxFrequency = 0;
        String result = null;
        HashMap<String, Integer> frequency = new HashMap<>();
        for (HashSet<String> uniqueResponses : responsesByDay) {
            for (String s : uniqueResponses) {
                frequency.put(s, frequency.getOrDefault(s, 0) + 1);
                int currentFrequency = frequency.get(s);
                if (currentFrequency > maxFrequency || (currentFrequency == maxFrequency && (result == null || s.compareTo(result) < 0))) {
                    maxFrequency = currentFrequency;
                    result = s;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        FindTheMostCommonResponse F = new FindTheMostCommonResponse();

        System.out.println("Test 1: " + F.findCommonResponse(List.of(List.of("yes", "no", "maybe"), List.of("yes", "maybe"), List.of("yes", "no"))) + " (Expected: yes)");
        System.out.println("Test 2: " + F.findCommonResponse(List.of(List.of("apple", "banana"), List.of("banana", "cherry"), List.of("apple", "banana"))) + " (Expected: banana)");
        System.out.println("Test 3: " + F.findCommonResponse(List.of(List.of("a", "b", "c"), List.of("d", "e", "f"), List.of("g", "h", "i"))) + " (Expected: a)");
        System.out.println("Test 4: " + F.findCommonResponse(List.of(List.of("cat", "dog"), List.of("cat", "dog"), List.of("cat", "dog"))) + " (Expected: cat)");
        System.out.println("Test 5: " + F.findCommonResponse(List.of(List.of("x"), List.of("y"), List.of("z"))) + " (Expected: x)");
        System.out.println("Test 6: " + F.findCommonResponse(List.of(List.of("hello", "world"), List.of("hello", "hello", "world"), List.of("world"))) + " (Expected: world)");
        System.out.println("Test 7: " + F.findCommonResponse(List.of(List.of("beta", "alpha"), List.of("alpha", "beta"), List.of("beta"))) + " (Expected: beta)");
        System.out.println("Test 8: " + F.findCommonResponse(List.of(List.of("zebra", "apple", "banana"), List.of("zebra", "cherry"), List.of("zebra", "date"))) + " (Expected: zebra)");
        System.out.println("Test 9: " + F.findCommonResponse(List.of(List.of("code", "code", "test"), List.of("code"), List.of("test"))) + " (Expected: code)");
    }
}
