package medium;

import java.util.Arrays;

public class MagicDictionary {
    TrieNode root;
    public MagicDictionary() {
        this.root = new TrieNode();
    }

    public void buildDict(String[] dictionary) {
        int size = dictionary.length;
        for (int i = 0; i < size; i++) {
            char[] wordArray = dictionary[i].toCharArray();
            insert(root, wordArray, wordArray.length, 0);
        }
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

    public boolean search(String searchWord) {
        char[] wordArray = searchWord.toCharArray();
        return search(root, wordArray, wordArray.length, 0, false);
    }

    private boolean search(TrieNode root, char[] wordArray, int length, int index, boolean charChanged) {
        if (index == length)
            return root.isWord && charChanged;
        if (root.children[wordArray[index] - 'a'] != null) {
            if (search(root.children[wordArray[index] - 'a'], wordArray, length, index + 1, charChanged))
                return true;
        }
        if (charChanged)
            return false;
        boolean found = false;
        for (int i = 0; i < 26; i++) {
            if (i != (wordArray[index] - 'a') && root.children[i] != null) {
                found |= search(root.children[i], wordArray, length, index+1, true);
            }
        }
        return found;
    }

    class TrieNode {
        TrieNode[] children;
        boolean isWord;

        public TrieNode() {
            this.children = new TrieNode[26];
            this.isWord = false;
        }

        @Override
        public String toString() {
            return "TrieNode{" +
                    "children=" + Arrays.toString(children) +
                    ", isWord=" + isWord +
                    '}';
        }
    }

    public static void main(String[] args) {
        MagicDictionary M = new MagicDictionary();
        M.buildDict(new String[]{"hello", "leetcode"});
        System.out.println(M.search("hello"));
        System.out.println(M.search("hhllo"));
        System.out.println(M.search("hell"));
        System.out.println(M.search("leetcoded"));
        System.out.println(M.search("leatcode"));
    }
}
