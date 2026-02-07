package medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class LexicographicallySmallestEquivalentString {
    public String smallestEquivalentString(String s1, String s2, String baseStr) {
        int length = s1.length();
        HashMap<Integer,List<Integer>> charMap = new HashMap<>();
        for (int c = 'a'; c <= 'z'; c++)
            charMap.put(c, new ArrayList<>());
        for (int i = 0; i < length; i++) {
            charMap.get((int)s1.charAt(i)).add((int)s2.charAt(i));
            charMap.get((int)s2.charAt(i)).add((int)s1.charAt(i));
        }
        HashSet<Integer> visited = new HashSet<>();
        HashMap<Integer,Integer> minChar = new HashMap<>();
        for (int c = 'a'; c <= 'z'; c++) {
            if (visited.contains(c))
                continue;
            List<Integer> current = new ArrayList<>();
            dfs(c, current, charMap, visited);
            int minCharInList = c;
            for (int ch : current)
                if (ch < minCharInList)
                    minCharInList = ch;
            for (int ch : current)
                minChar.put(ch, minCharInList);
        }
        StringBuilder sb = new StringBuilder();
        int size = baseStr.length();
        for (int i = 0; i < size; i++)
            sb.append((char)(int)minChar.get((int)baseStr.charAt(i)));
        return sb.toString();
    }

    private void dfs(int c, List<Integer> current, HashMap<Integer,List<Integer>> charMap,
            HashSet<Integer> visited) {
        visited.add(c);
        current.add(c);
        List<Integer> neighbours = charMap.getOrDefault(c, new ArrayList<>());
        for (int ch : neighbours) {
            if (!visited.contains(ch))
                dfs(ch, current, charMap, visited);
        }
    }

    public static void main(String[] args) {
        LexicographicallySmallestEquivalentString L = new LexicographicallySmallestEquivalentString();
        
        System.out.println("Test 1: " + L.smallestEquivalentString("parker", "morris", "parser"));
        System.out.println("Test 2: " + L.smallestEquivalentString("hello", "world", "hold"));
        System.out.println("Test 3: " + L.smallestEquivalentString("leetcode", "programs", "sourcecode"));
        System.out.println("Test 4: " + L.smallestEquivalentString("abc", "cde", "eed"));
        System.out.println("Test 5: " + L.smallestEquivalentString("a", "b", "ab"));
    }
}
