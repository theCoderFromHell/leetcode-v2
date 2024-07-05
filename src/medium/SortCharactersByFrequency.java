package medium;

import java.util.*;

public class SortCharactersByFrequency {
    public String frequencySort(String s) {
        int N = s.length();
        int[] hash = new int[62];
        for (int i = 0; i < N; i++) {
           if (Character.isDigit(s.charAt(i)))
               hash[(s.charAt(i) - '0')]++;
            else if (Character.isUpperCase(s.charAt(i)))
                hash[(s.charAt(i) - 'A') + 36]++;
            else
                hash[(s.charAt(i) - 'a') + 10]++;
        }
        List<CharCount> charCountList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if (hash[i] > 0)
                charCountList.add(new CharCount((char) (i + '0'), hash[i]));
        }
        for (int i = 10; i < 36; i++) {
            if (hash[i] > 0)
                charCountList.add(new CharCount((char) (i - 10 + 'a'), hash[i]));
        }
        for (int i = 36; i < 62; i++) {
            if (hash[i] > 0)
                charCountList.add(new CharCount((char) (i - 36 + 'A'), hash[i]));
        }
        Collections.sort(charCountList);
        StringBuilder sb = new StringBuilder();
        for (CharCount cc : charCountList) {
            int count = cc.frequency;
            while (count-- > 0)
                sb.append(cc.character);
        }
        return sb.toString();
    }

    class CharCount implements Comparable{
        char character;
        int frequency;

        public CharCount(char character, int frequency) {
            this.character = character;
            this.frequency = frequency;
        }

        @Override
        public String toString() {
            return "CharCount{" +
                    "character=" + character +
                    ", frequency=" + frequency +
                    '}';
        }

        @Override
        public int compareTo(Object o) {
            CharCount cc = (CharCount) o;
            return (cc.frequency - this.frequency);
        }
    }

    public static void main(String[] args) {
        SortCharactersByFrequency S = new SortCharactersByFrequency();
        System.out.println(S.frequencySort("pTa8tT88Tp2Q8Vd1V8Vq888YT49T"));
    }
}
