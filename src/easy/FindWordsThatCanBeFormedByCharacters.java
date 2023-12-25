package easy;

public class FindWordsThatCanBeFormedByCharacters {
    public static int countCharacters(String[] words, String chars) {
        if (chars == null || chars.isEmpty() || chars.isBlank())
            return 0;
        if (words == null || words.length == 0)
            return 0;
        int result = 0;
        int N = words.length;
        int[] charsMap = new int[26];
        for (char c : chars.toCharArray())
            charsMap[c - 'a']++;
        for (int i = 0; i < N; i++) {
            int[] wordsMap = new int[26];
            boolean wordMatch = true;
            for (char c : words[i].toCharArray())
                wordsMap[c - 'a']++;
            for (int j = 0; j < 26; j++) {
                if(wordsMap[j] > charsMap[j]) {
                    wordMatch = false;
                    break;
                }
            }
            if (wordMatch)
                result += words[i].length();
        }
        return result;
    }
    public static void main(String[] args) {
        System.out.println(countCharacters(new String[]{
                "cat","bt","hat","tree"
        }, "atach"));
        System.out.println(countCharacters(new String[]{
                "hello","world","leetcode"
        }, "welldonehoneyr"));


    }
}
