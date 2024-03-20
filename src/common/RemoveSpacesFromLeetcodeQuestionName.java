package common;

import java.util.Arrays;

public class RemoveSpacesFromLeetcodeQuestionName {

    private static String removeSpacesFromLeetcodeQuestionName(String leetcodeQuestionName) {
        int length = leetcodeQuestionName.length();
        char[] result = new char[length];
        int j = 0;
        for (int i = 0; i < length; i++) {
            if (Character.isAlphabetic(leetcodeQuestionName.charAt(i)))
                result[j++] = leetcodeQuestionName.charAt(i);
            else if (i < length-1)
                result[j++] = Character.toUpperCase(leetcodeQuestionName.charAt(++i));
        }
        return new String(Arrays.copyOfRange(result, 0, j));
    }

    public static void main(String[] args) {
        System.out.print(removeSpacesFromLeetcodeQuestionName("Merge In Between Linked Lists"));
    }
}
