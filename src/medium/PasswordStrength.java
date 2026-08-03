package medium;

import java.util.HashSet;

// https://leetcode.com/problems/password-strength/
public class PasswordStrength {
    public int passwordStrength(String password) {
        int size = password.length();
        HashSet<Character> uniques = new HashSet<>();
        for (int i = 0; i < size; i++) {
            uniques.add(password.charAt(i));
        }
        int strength = 0;
        for (Character c : uniques) {
            if (Character.isLowerCase(c))
                strength += 1;
            else if (Character.isUpperCase(c))
                strength += 2;
            else if (Character.isDigit(c))
                strength += 3;
            else
                strength += 5;
        }
        return strength;
    }

    /*
     * Pattern: HashSet deduplication + character classification
     * Key Insight: Collect unique chars via HashSet so each contributes its
     *              score exactly once, regardless of repetitions in the input.
     * Gotchas:
     *   - char→Character autoboxing with HashSet<Character>; boolean[128] avoids this.
     *   - Special characters fall through to the `else` branch — any char that
     *     isn't lower, upper, or digit scores 5, so the set of specials is implicit.
     */
    public static void main(String[] args) {
        PasswordStrength P = new PasswordStrength();
        System.out.println("Test 1: " + P.passwordStrength("a") + " (Expected: 1)");
        System.out.println("Test 2: " + P.passwordStrength("aA1!") + " (Expected: 11)");
        System.out.println("Test 3: " + P.passwordStrength("aaaa") + " (Expected: 1)");
        System.out.println("Test 4: " + P.passwordStrength("aAbB12!!") + " (Expected: 17)");
        System.out.println("Test 5: " + P.passwordStrength("") + " (Expected: 0)");
        System.out.println("Test 6: " + P.passwordStrength("ABCDE") + " (Expected: 10)");
    }
}
