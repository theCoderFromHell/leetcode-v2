package medium;

import java.util.Arrays;

public class Trie {
    TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        char[] wordCharacters = word.toCharArray();
        insert(root, wordCharacters, 0, wordCharacters.length);

    }

    private void insert(TrieNode root, char[] wordCharacters, int index, int length) {
        if (root.children[wordCharacters[index] - 'a'] == null)
            root.children[wordCharacters[index] - 'a'] = new TrieNode();
        if (index == length-1) {
            root.children[wordCharacters[index] - 'a'].isWord = true;
            return;
        }
        insert(root.children[wordCharacters[index] - 'a'], wordCharacters, index+1, length);
    }

    public boolean search(String word) {
        char[] wordCharacters = word.toCharArray();
        return search(root, wordCharacters, 0, wordCharacters.length);
    }

    private boolean search(TrieNode root, char[] wordCharacters, int index, int length) {
        if (root.children[wordCharacters[index] - 'a'] == null)
            return false;
        if (index == length-1)
            return root.children[wordCharacters[index] - 'a'].isWord;
        return search(root.children[wordCharacters[index] - 'a'], wordCharacters, index+1, length);
    }

    public boolean startsWith(String prefix) {
        char[] wordCharacters = prefix.toCharArray();
        return startsWith(root, wordCharacters, 0, wordCharacters.length);
    }

    private boolean startsWith(TrieNode root, char[] wordCharacters, int index, int length) {
        if (root.children[wordCharacters[index] - 'a'] == null)
            return false;
        if (index == length-1)
            return true;
        return startsWith(root.children[wordCharacters[index] - 'a'], wordCharacters, index+1, length);
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
                    "isWord=" + isWord +
                    ", children=" + Arrays.toString(children) +
                    '}';
        }
    }

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("apple");
        System.out.println(trie.search("apple"));
        System.out.println(trie.search("app"));
        System.out.println(trie.startsWith("app"));
        trie.insert("app");
        System.out.println(trie.search("app"));



    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */