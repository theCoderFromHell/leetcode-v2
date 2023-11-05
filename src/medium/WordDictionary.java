package medium;

public class WordDictionary {

    Trie root;
    public WordDictionary() {
        this.root = new Trie('.');
    }

    public void addWord(String word) {
        int length = word.length();
        addWord(root, word.toCharArray(), 0, length);
    }

    private void addWord(Trie root, char[] word, int index, int length) {
        if (index == length)
            return;
        Trie node = (root.children[word[index] - 'a'] == null) ? new Trie(word[index]) : root.children[word[index] - 'a'];
        root.children[word[index] - 'a'] = node;
        if (index == length-1)
            node.isWord = true;
        addWord(node, word, index+1, length);
    }

    public boolean search(String word) {
        int length = word.length();
        return searchWord(root, word.toCharArray(), 0, length);
    }

    private boolean searchWord(Trie root, char[] word, int index, int length) {
        if (index == length)
            return true;
        if (word[index] == '.') {
            for (int i = 0; i < 26; i++) {
                Trie node = root.children[i];
                if (node == null)
                    continue;
                if (index == length - 1 && !node.isWord)
                    continue;
                if (searchWord(node, word, index+1, length))
                    return true;
            }
        } else {
            Trie node = root.children[word[index] - 'a'];
            if (node == null)
                return false;
            if (index == length - 1 && !node.isWord)
                return false;
            return searchWord(node, word, index + 1, length);
        }
        return false;
    }

    public static void main(String[] args) {
        WordDictionary dictionary = new WordDictionary();
        String word;
        dictionary.addWord("at");
        dictionary.addWord("and");
        dictionary.addWord("an");
        dictionary.addWord("add");

        word = "a";
        System.out.println(dictionary.search(word) ? "Found the word : " + word : "Can not find the word :  " + word);

        word = ".at";
        System.out.println(dictionary.search(word) ? "Found the word : " + word : "Can not find the word :  " + word);


        dictionary.addWord("bat");

        word = ".at";
        System.out.println(dictionary.search(word) ? "Found the word : " + word : "Can not find the word :  " + word);

        word = "an.";
        System.out.println(dictionary.search(word) ? "Found the word : " + word : "Can not find the word :  " + word);

        word = "a.d.";
        System.out.println(dictionary.search(word) ? "Found the word : " + word : "Can not find the word :  " + word);

        word = "b.";
        System.out.println(dictionary.search(word) ? "Found the word : " + word : "Can not find the word :  " + word);

        word = "a.d";
        System.out.println(dictionary.search(word) ? "Found the word : " + word : "Can not find the word :  " + word);

        word = ".";
        System.out.println(dictionary.search(word) ? "Found the word : " + word : "Can not find the word :  " + word);

    }
}

class Trie {
    char character;
    Trie[] children;

    boolean isWord;

    public Trie(char character) {
        this.character = character;
        this.children = new Trie[26];
        for (int i = 0; i < 26; i++) {
            children[i] = null;
        }
        this.isWord = false;
    }
}
