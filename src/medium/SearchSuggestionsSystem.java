package medium;

import java.util.*;

public class SearchSuggestionsSystem {
    static PrefixTrie root;
    public static List<List<String>> suggestedProducts(String[] products, String searchWord) {
        root = new PrefixTrie();
        Arrays.sort(products);
        for (String word : products)
            addToTrie(root, word.toCharArray(), 0, word.length());
        List<List<String>> result = new ArrayList<>();
        char[] searchWordArray = searchWord.toCharArray();
        int size = searchWordArray.length;
        for (int i = 0; i < size; i++) {
            List<String> words = findWords(root, searchWordArray, 0, i);
            if (words == null)
                result.add(new ArrayList<>());
            else if (words.size() > 3) {
                result.add(Arrays.asList(words.get(0), words.get(1), words.get(2)));
            } else
                result.add(words);
        }
        return result;
    }

    private static List<String> findWords(PrefixTrie root, char[] word, int index, int length) {
        if (root.children[word[index] - 'a'] == null)
            return null;
        if (index == length)
            return root.children[word[index] - 'a'].words;
        return findWords(root.children[word[index] - 'a'], word, index+1, length);
    }

    private static void addToTrie(PrefixTrie root, char[] word, int index, int length) {
        if (index == length)
            return;
        root.children[word[index] - 'a'] = (root.children[word[index] - 'a'] != null)
                ? root.children[word[index] - 'a']
                : new PrefixTrie();
        root.children[word[index] - 'a'].words.add(String.valueOf(word));
        if (index != length-1)
            addToTrie(root.children[word[index] - 'a'], word, index+1, length);
    }

    public static void main(String[] args) {
        System.out.println(suggestedProducts(new String[]{"mobile","mouse","moneypot","monitor","mousepad"}, "mouse"));
    }

}
class PrefixTrie {
    PrefixTrie[] children;
    List<String> words;

    public PrefixTrie() {
        this.children = new PrefixTrie[26];
        words = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "PrefixTrie{" +
                "children=" + Arrays.toString(children) +
                ", words=" + words +
                '}';
    }
}
