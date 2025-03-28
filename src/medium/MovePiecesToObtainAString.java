package medium;

import java.util.ArrayList;
import java.util.List;

public class MovePiecesToObtainAString {

    public static final char UNDERSCORE = '_';
    public boolean canChange(String start, String target) {
        int N = start.length();
        List<int[]> startLetters = new ArrayList<>();
        List<int[]> targetLetters = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            if (start.charAt(i) != UNDERSCORE)
                startLetters.add(new int[]{start.charAt(i) == 'L' ? 0 : 1, i});
            if (target.charAt(i) != UNDERSCORE)
                targetLetters.add(new int[]{target.charAt(i) == 'L' ? 0 : 1, i});
        }
        if (startLetters.size() != targetLetters.size())
            return false;
        int size = startLetters.size();
        for (int i = 0; i < size; i++) {
            if (startLetters.get(i)[0] != targetLetters.get(i)[0])
                return false;
            int letter = startLetters.get(i)[0];
            if (letter == 0 && startLetters.get(i)[1] < targetLetters.get(i)[1])
                return false;
            if (letter == 1 && startLetters.get(i)[1] > targetLetters.get(i)[1])
                return false;
        }
        return true;
    }
}
