package hard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class WordSearchII {
    Trie trie = new Trie('*');
    private int[] dx = {1, -1, 0, 0};
    private int[] dy = {0, 0, 1, -1};
    public List<String> findWords(char[][] board, String[] words) {
        for (String word : words)
            addWords(word);
        int rows = board.length;
        int columns = board[0].length;
        HashSet<String> result = new HashSet<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                boolean[][] visited = new boolean[rows][columns];
                StringBuilder sb = new StringBuilder();
                Trie node = hasWordWithFirstCharacter(trie, board[i][j]);
                if (node != null) {
                    sb.append(board[i][j]);
                    visited[i][j] = true;
                    dfs(board, i, j, rows, columns, node, visited, sb, result);
                }
            }
        }
        List<String> ans = new ArrayList<>();
        ans.addAll(result);
        return ans;
    }

    private void dfs(char[][] board, int x, int y, int M, int N, Trie node, boolean[][] visited,
                        StringBuilder sb, HashSet<String> result) {
        if (node.isWord) {
            result.add(new String(sb));
        }
        for (int i = 0; i < 26; i++) {
            if (node.children[i] != null) {
                for (int k = 0; k < 4; k++)
                    if (x + dx[k] >= 0 && x + dx[k] < M && y + dy[k] >= 0 && y + dy[k] < N && board[x + dx[k]][y + dy[k]] == node.children[i].character)
                        if (!visited[x + dx[k]][y + dy[k]]) {
                            visited[x + dx[k]][y + dy[k]] = true;
                            sb.append(board[x + dx[k]][y + dy[k]]);
                            dfs(board, x + dx[k], y + dy[k], M, N, node.children[i], visited, sb, result);
                            sb.deleteCharAt(sb.length()-1);
                            visited[x + dx[k]][y + dy[k]] = false;
                        }
            }
        }
    }

    public void addWords(String word) {
        if (word == null || word.isBlank())
            return;
        int length = word.length();
        char[] wordChars = word.toCharArray();
        add(trie, wordChars, 0, length);
    }

    private void add(Trie node, char[] wordChars, int index, int length) {
        if (index == length)
            return;
        Trie child = node.children[wordChars[index] - 'a'];
        if (child == null)
            child = new Trie(wordChars[index]);
        node.children[wordChars[index] - 'a'] = child;
        if (index == length-1)
            child.isWord = true;
        add(child, wordChars, index+1, length);
    }

    public Trie hasWordWithFirstCharacter(Trie node, char first) {
        return (node.children[first - 'a']);
    }

    public static void main(String[] args) {
        WordSearchII W = new WordSearchII();
        System.out.println(W.findWords(new char[][] {
                {'o','a','a','n'},
                {'e','t','a','e'},
                {'i','h','k','r'},
                {'i','f','l','v'}
        }, new String[]{"oath","pea","eat","rain"}));
    }

    class Trie {
        char character;
        Trie[] children;
        boolean isWord;

        public Trie(char character) {
            this.character = character;
            this.children = new Trie[26];
            this.isWord = false;
        }

        @Override
        public String toString() {
            return "Trie{" +
                    "character=" + character +
                    ", isWord=" + isWord +
                    '}';
        }
    }
}
