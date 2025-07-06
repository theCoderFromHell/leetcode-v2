package medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class SentenceSimilarityII {
    public boolean areSentencesSimilarTwo(String[] sentence1, String[] sentence2, List<List<String>> similarPairs) {
        if (sentence1.length != sentence2.length)
            return false;
        int size = similarPairs.size();
        HashMap<String, List<String>> adjList = new HashMap<>();
        for (int i = 0; i < size; i++) {
            List<String> edge = similarPairs.get(i);

            List<String> nodes = adjList.getOrDefault(edge.get(0), new ArrayList<>());
            nodes.add(edge.get(1));
            adjList.put(edge.get(0), nodes);

            nodes = adjList.getOrDefault(edge.get(1), new ArrayList<>());
            nodes.add(edge.get(0));
            adjList.put(edge.get(1), nodes);
        }

        int length = sentence1.length;
        for (int i = 0; i < length; i++) {
            HashSet<String> visited = new HashSet<>();
            if (!dfs(sentence1[i], sentence2[i], adjList, visited))
                return false;
        }
        return true;
    }

    private boolean dfs(String source, String destination, HashMap<String, List<String>> adjList, HashSet<String> visited) {
        visited.add(source);
        if (source.equals(destination))
            return true;
        List<String> neighbours = adjList.get(source);
        if (neighbours == null || neighbours.isEmpty())
            return false;
        for (String neighbour : neighbours) {
            if (!visited.contains(neighbour) && dfs(neighbour, destination, adjList, visited))
                return true;
        }
        return false;
    }

    public static void main(String[] args) {
        SentenceSimilarityII S = new SentenceSimilarityII();
        System.out.println(S.areSentencesSimilarTwo(new String[]{"great","acting","skills"}, new String[]{"fine","drama","talent"}, List.of(
                List.of("great","good"), List.of("fine","good"), List.of("drama","acting"), List.of("skills","talent")
        )));
        System.out.println(S.areSentencesSimilarTwo(new String[]{"I","love","leetcode"}, new String[]{"I","love","onepiece"}, List.of(
                List.of("manga","onepiece"), List.of("platform","anime"), List.of("leetcode","platform"), List.of("anime","manga")
        )));
        System.out.println(S.areSentencesSimilarTwo(new String[]{"I","love","leetcode"}, new String[]{"I","love","onepiece"}, List.of(
                List.of("manga","hunterXhunter"), List.of("platform","anime"), List.of("leetcode","platform"), List.of("anime","manga")
        )));
    }
}
