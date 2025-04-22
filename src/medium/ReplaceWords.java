package medium;

import java.util.Arrays;
import java.util.List;

public class ReplaceWords {
    TrieNode root;
    public String replaceWords(List<String> dictionary, String sentence) {
        root = new TrieNode();
        int size = dictionary.size();
        for (int i = 0; i < size; i++) {
            char[] wordArray = dictionary.get(i).toCharArray();
            insert(root, wordArray, wordArray.length, 0);
        }
        String[] words = sentence.split(" ");
        int length = words.length;
        StringBuilder result =new StringBuilder();
        for (int i = 0; i < length; i++) {
            char[] wordArray = words[i].toCharArray();
            String wordRoot = exists(root, wordArray, wordArray.length, 0);
            if (wordRoot != null && !wordRoot.isBlank())
                result.append(wordRoot);
            else
                result.append(words[i]);
            if (i != length-1)
                result.append(" ");
        }
        return result.toString();
    }

    private String exists(TrieNode root, char[] wordArray, int length, int index) {
        if (root == null || index == length-1)
            return null;
        if (root.isWord)
            return new String(wordArray, 0, index);
        return exists(root.children[wordArray[index] - 'a'], wordArray, length, index+1);
    }

    private void insert(TrieNode root, char[] wordArray, int length, int index) {
        if (index == length) {
            root.isWord = true;
            return;
        }
        if (root.children[wordArray[index] - 'a'] == null)
            root.children[wordArray[index] - 'a'] = new TrieNode();
        insert(root.children[wordArray[index] - 'a'], wordArray, length, index+1);
    }

    class TrieNode {
        TrieNode[] children;
        boolean isWord;

        public TrieNode() {
            this.children = new TrieNode[26];
            this.isWord = false;
        }
    }

    public static void main(String[] args) {
        ReplaceWords R = new ReplaceWords();
//        System.out.println(R.replaceWords(Arrays.asList(), ""));
        System.out.println(R.replaceWords(Arrays.asList("cat","bat","rat"), "the cattle was rattled by the battery"));
        System.out.println(R.replaceWords(Arrays.asList("a","b","c"), "aadsfasf absbs bbab cadsfafs"));
    }
}
