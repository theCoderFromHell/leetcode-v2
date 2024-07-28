package hard;

import java.util.*;

public class WordLadder {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        wordList.add(beginWord);
        int N = wordList.size();
        HashMap<String, List<String>> adjList = new HashMap<>();
        for (int i = 0; i < N; i++)
            adjList.put(wordList.get(i), new ArrayList<>());

        for (int i = 0; i < N; i++) {
            for (int j = i+1; j < N; j++) {
                if (differByOnlyOneCharacter(wordList.get(i), wordList.get(j))) {
                    List<String> neighbors1 = adjList.getOrDefault(wordList.get(i), new ArrayList<>());
                    neighbors1.add(wordList.get(j));
                    adjList.put(wordList.get(i), neighbors1);

                    List<String> neighbors2 = adjList.getOrDefault(wordList.get(j), new ArrayList<>());
                    neighbors2.add(wordList.get(i));
                    adjList.put(wordList.get(j), neighbors2);
                }
            }
        }
        int count = 1;
        Queue<String> queue = new LinkedList<>();
        HashSet<String> visited = new HashSet<>();
        queue.add(beginWord);
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                String word = queue.poll();
                visited.add(word);
                if (endWord.equals(word))
                    return count;
                List<String> neighbors = adjList.get(word);
                for (String neighbor : neighbors)
                    if (!visited.contains(neighbor))
                        queue.add(neighbor);
            }
            count++;
        }
        return 0;
    }

    private boolean differByOnlyOneCharacter(String a, String b) {
        int length = a.length();
        int count = 0;
        for (int i = 0; i < length; i++) {
            if (a.charAt(i) != b.charAt(i))
                count++;
            if (count >= 2)
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        WordLadder W = new WordLadder();
        List<String> wordList = new ArrayList<>();
        wordList.add("hot");
        wordList.add("dot");
        wordList.add("dog");
        wordList.add("lot");
        wordList.add("log");
        wordList.add("cog");
        System.out.println(W.ladderLength("hit", "cog",
                wordList));
    }
}
