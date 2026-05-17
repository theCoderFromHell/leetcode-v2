package common;

import java.util.Arrays;

public class RemoveSpacesFromLeetcodeQuestionName {

    private String removeSpacesFromLeetcodeQuestionName(String leetCodeQuestionName) {
        int length = leetCodeQuestionName.length();
        char[] result = new char[length];
        int j = 0;
        for (int i = 0; i < length; i++) {
            if (Character.isAlphabetic(leetCodeQuestionName.charAt(i)))
                result[j++] = leetCodeQuestionName.charAt(i);
            else if (i < length-1)
                result[j++] = Character.toUpperCase(leetCodeQuestionName.charAt(++i));
        }
        return new String(Arrays.copyOfRange(result, 0, j));
    }

    public static void main(String[] args) {
        RemoveSpacesFromLeetcodeQuestionName R = new RemoveSpacesFromLeetcodeQuestionName();
        System.out.println(
                R.removeSpacesFromLeetcodeQuestionName(
                        "Arithmetic Subarrays"));
    }
}
