package hard;

import java.util.HashSet;
import java.util.Random;

public class GuessTheWord {
    public void findSecretWord(String[] words, Master master) {
        int N = words.length;
        HashSet<String> discarded = new HashSet<>();
        Random random = new Random();
        while (true) {
            int index = random.nextInt(N);
            String s = words[index];
            if (discarded.contains(s))
                continue;
            int matchCount = master.guess(s);
            if (matchCount == 6)
                return;
            for (String c : words) {
                if (s.equals(c) || !sameMatch(s,c, matchCount))
                    discarded.add(c);
            }
        }
    }

    private boolean sameMatch(String s, String c, int matchCount) {
        int count = 0;
        for (int i = 0; i < 6; i++) {
            if (s.charAt(i) == c.charAt(i))
                count++;
        }
        return count == matchCount;
    }


    interface Master {
        default int guess(String word) {
            return 0;
        }
    }
}
