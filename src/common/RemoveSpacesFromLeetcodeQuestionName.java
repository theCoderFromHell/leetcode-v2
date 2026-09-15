package common;

import java.util.regex.Pattern;

public class RemoveSpacesFromLeetcodeQuestionName {

    private static final Pattern PROBLEM_NUMBER_PREFIX = Pattern.compile("^\\s*\\d+\\.\\s+");
    // Joiners are dropped without capitalizing what follows: "K-th" -> "Kth", "Pascal's" -> "Pascals"
    private static final Pattern JOINERS = Pattern.compile("['’-]");
    private static final Pattern WORD_SEPARATORS = Pattern.compile("[^A-Za-z0-9]+");
    private static final String[] DIGIT_WORDS =
            {"Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine"};

    public String removeSpacesFromLeetcodeQuestionName(String leetCodeQuestionName) {
        String title = PROBLEM_NUMBER_PREFIX.matcher(leetCodeQuestionName).replaceFirst("");
        title = JOINERS.matcher(title).replaceAll("");
        StringBuilder name = new StringBuilder();
        for (String word : WORD_SEPARATORS.split(title)) {
            if (word.isEmpty())
                continue;
            name.append(Character.toUpperCase(word.charAt(0))).append(word, 1, word.length());
        }
        return spellOutLeadingDigits(name);
    }

    // A Java class name cannot start with a digit: "3Sum" -> "ThreeSum", matching FourSum / ThreeSumClosest
    private String spellOutLeadingDigits(StringBuilder name) {
        StringBuilder spelled = new StringBuilder();
        int i = 0;
        while (i < name.length() && Character.isDigit(name.charAt(i)))
            spelled.append(DIGIT_WORDS[name.charAt(i++) - '0']);
        return spelled.append(name, i, name.length()).toString();
    }

    public static void main(String[] args) {
        RemoveSpacesFromLeetcodeQuestionName R = new RemoveSpacesFromLeetcodeQuestionName();
        if (args.length > 0) {
            System.out.println(R.removeSpacesFromLeetcodeQuestionName(String.join(" ", args)));
            return;
        }
        System.out.println("Test 1: " + R.removeSpacesFromLeetcodeQuestionName("Maximum Score From Removing Stones") + " (Expected: MaximumScoreFromRemovingStones)");
        System.out.println("Test 2: " + R.removeSpacesFromLeetcodeQuestionName("Course Schedule II") + " (Expected: CourseScheduleII)");
        System.out.println("Test 3: " + R.removeSpacesFromLeetcodeQuestionName("K-th Symbol in Grammar") + " (Expected: KthSymbolInGrammar)");
        System.out.println("Test 4: " + R.removeSpacesFromLeetcodeQuestionName("Find Root of N-Ary Tree") + " (Expected: FindRootOfNAryTree)");
        System.out.println("Test 5: " + R.removeSpacesFromLeetcodeQuestionName("Largest 3-Same-Digit Number in String") + " (Expected: Largest3SameDigitNumberInString)");
        System.out.println("Test 6: " + R.removeSpacesFromLeetcodeQuestionName("3Sum Closest") + " (Expected: ThreeSumClosest)");
        System.out.println("Test 7: " + R.removeSpacesFromLeetcodeQuestionName("4Sum") + " (Expected: FourSum)");
        System.out.println("Test 8: " + R.removeSpacesFromLeetcodeQuestionName("Two Sum II - Input Array Is Sorted") + " (Expected: TwoSumIIInputArrayIsSorted)");
        System.out.println("Test 9: " + R.removeSpacesFromLeetcodeQuestionName("Pow(x, n)") + " (Expected: PowXN)");
        System.out.println("Test 10: " + R.removeSpacesFromLeetcodeQuestionName("Pascal's Triangle") + " (Expected: PascalsTriangle)");
        System.out.println("Test 11: " + R.removeSpacesFromLeetcodeQuestionName("1756. Design Most Recently Used Queue") + " (Expected: DesignMostRecentlyUsedQueue)");
        System.out.println("Test 12: " + R.removeSpacesFromLeetcodeQuestionName("  Unique Email Groups  ") + " (Expected: UniqueEmailGroups)");
        System.out.println("Test 13: " + R.removeSpacesFromLeetcodeQuestionName("132 Pattern") + " (Expected: OneThreeTwoPattern)");
        System.out.println("Test 14: " + R.removeSpacesFromLeetcodeQuestionName("2 Keys Keyboard") + " (Expected: TwoKeysKeyboard)");
    }
}
