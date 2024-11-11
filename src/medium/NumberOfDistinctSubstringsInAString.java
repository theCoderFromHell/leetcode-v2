package medium;

public class NumberOfDistinctSubstringsInAString {
    public int countDistinct(String s) {
        int length = s.length();
        int result = 0;
        TrieNode root = new TrieNode('*');
        for (int i = 0; i < length; i++) {
            TrieNode curr = root;
            for (int j = i; j < length; j++) {
                if (curr.children[s.charAt(j) - 'a'] == null) {
                    curr.children[s.charAt(j) - 'a'] = new TrieNode(s.charAt(j));
                    result++;
                }
                curr = curr.children[s.charAt(j) - 'a'];
            }
        }
        return result;
    }

    class TrieNode {
        char character;
        TrieNode[] children;

        public TrieNode(char character) {
            this.character = character;
            this.children = new TrieNode[26];
        }
    }

    public static void main(String[] args) {
        NumberOfDistinctSubstringsInAString N = new NumberOfDistinctSubstringsInAString();
        System.out.println(N.countDistinct("aabbaba"));
        System.out.println(N.countDistinct("abcdefg"));
        System.out.println(N.countDistinct("abab"));
    }
}
