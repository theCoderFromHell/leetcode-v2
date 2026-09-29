package medium;

import java.util.HashMap;
import java.util.HashSet;

// https://leetcode.com/problems/unique-word-abbreviation/
public class UniqueWordAbbreviation {
    static class ValidWordAbbr {
        HashMap<String, HashSet<String>> abbreviations;
        public ValidWordAbbr(String[] dictionary) {
            this.abbreviations = new HashMap<>();
            int size = dictionary.length;
            for (int i = 0; i < size; i++) {
                String abbreviation = computeAbbreviation(dictionary[i]);
                abbreviations.computeIfAbsent(abbreviation, k -> new HashSet<>()).add(dictionary[i]);
            }
        }

        private String computeAbbreviation(String word) {
            String abbreviation = word;
            int length = abbreviation.length();
            if (length > 2)
                abbreviation = word.charAt(0) + String.valueOf(length - 2) + word.charAt(length - 1);
            return abbreviation;
        }

        public boolean isUnique(String word) {
            String abbreviation = computeAbbreviation(word);
            return (
                    !abbreviations.containsKey(abbreviation) ||
                            (abbreviations.get(abbreviation).size() == 1 && abbreviations.get(abbreviation).contains(word)));
        }
    }

    /*
     * Revision Note — Unique Word Abbreviation (Medium)
     *
     * Pattern: Canonical-key map, abbreviation -> SET of distinct words
     *
     * Key Insight: Map each abbreviation to the set of DISTINCT dictionary words producing it.
     * isUnique(word) is true when either no word shares the abbreviation, or exactly one
     * distinct word does and it is `word` itself.
     *
     * Gotchas:
     * - Set, NOT List or a counter. This is why acceptance sits near 28%: the dictionary may
     *   contain duplicates, and ["deer","deer"] must still report isUnique("deer") == true.
     *   A count-based map sees 2 and wrongly returns false; a HashSet collapses it to 1
     * - Words of length <= 2 are their OWN abbreviation. Applying first+(len-2)+last to "a"
     *   yields "a-1a" with a negative middle count — guard with `if (length > 2)`
     * - char + String + char works only because the FIRST `+` has a String operand, so it is
     *   concatenation, not integer addition. `char + char + String` would silently sum the chars
     *
     * Template:
     *   build:  map.computeIfAbsent(abbr(w), k -> new HashSet<>()).add(w)
     *   query:  set = map.get(abbr(word))
     *           return set == null || (set.size() == 1 && set.contains(word))
     *   abbr:   len <= 2 ? word : first + (len-2) + last
     */
    public static void main(String[] args) {
        // LeetCode example
        ValidWordAbbr V = new ValidWordAbbr(new String[]{"deer", "door", "cake", "card"});
        System.out.println("Test 1: " + V.isUnique("dear") + " (Expected: false)"); // deer is also d2r
        System.out.println("Test 2: " + V.isUnique("cart") + " (Expected: true)");  // nothing is c2t
        System.out.println("Test 3: " + V.isUnique("cane") + " (Expected: false)"); // cake is also c2e
        System.out.println("Test 4: " + V.isUnique("make") + " (Expected: true)");  // nothing is m2e
        System.out.println("Test 5: " + V.isUnique("cake") + " (Expected: true)");  // c2e is cake itself

        // duplicates in the dictionary — the 28% trap
        ValidWordAbbr V2 = new ValidWordAbbr(new String[]{"deer", "deer"});
        System.out.println("Test 6: " + V2.isUnique("deer") + " (Expected: true)");

        // two distinct words sharing an abbreviation, query is one of them
        ValidWordAbbr V3 = new ValidWordAbbr(new String[]{"deer", "door"});
        System.out.println("Test 7: " + V3.isUnique("deer") + " (Expected: false)");

        // empty dictionary
        ValidWordAbbr V4 = new ValidWordAbbr(new String[]{});
        System.out.println("Test 8: " + V4.isUnique("anything") + " (Expected: true)");

        // words of length <= 2 are their own abbreviation
        ValidWordAbbr V5 = new ValidWordAbbr(new String[]{"a", "it"});
        System.out.println("Test 9: " + V5.isUnique("a") + " (Expected: true)");   // "a" itself
        System.out.println("Test 10: " + V5.isUnique("b") + " (Expected: true)");  // nothing is "b"
        System.out.println("Test 11: " + V5.isUnique("ab") + " (Expected: true)"); // nothing is "ab"
    }
}
