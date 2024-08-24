package medium;

import java.util.*;


public class SmallestSubsequenceOfDistinctCharacters {
    public String smallestSubsequence(String s) {
        int N = s.length();
        boolean[] visited = new boolean[26];
        Stack<Character> stack = new Stack<>();
        HashMap<Character, Integer> lastSeen = new HashMap<>();
        for (int i = 0; i < N; i++)
            lastSeen.put(s.charAt(i), i);
        for (int i = 0; i < N; i++) {
            char c = s.charAt(i);
            if (visited[c - 'a'])
                continue;
            while (!stack.empty() && c < stack.peek() && lastSeen.get(stack.peek()) > i) {
                visited[stack.peek() - 'a'] = false;
                stack.pop();
            }
            visited[c - 'a'] = true;
            stack.push(c);
        }
        StringBuilder sb = new StringBuilder();
        for (Character c : stack)
            sb.append(c);
        return sb.toString();
    }

    // This code works but gives TLE on Leetcode
    public String smallestSubsequenceV2(String s) {
        HashMap<Integer, List<Integer>> chars = new HashMap();
        int N = s.length();
        int count = 0;
        for (int i = 0; i < N; i++) {
            if (!chars.containsKey(s.charAt(i) - 'a'))
                count++;
            List<Integer> indices = chars.getOrDefault(s.charAt(i) - 'a', new ArrayList<>());
            indices.add(i);
            chars.put(s.charAt(i) - 'a', indices);
        }
        StringBuilder sb = new StringBuilder();
        boolean[] visited = new boolean[26];
        findLexicographicallySmallest(chars, sb, 0, 0, N, count, visited);
        return sb.toString();
    }

    private boolean findLexicographicallySmallest(HashMap<Integer, List<Integer>> chars, StringBuilder sb, int currentChar, int index, int N, int count, boolean[] visited) {
        if (sb.length() == count)
            return true;
        if (index >= N)
            return false;
        for (int i = 0; i < 26 ; i++) {
            if (!chars.containsKey(i) || visited[i])
                continue;
            int find = findIndex(chars, i, index, N-1);
            if (find == -1)
                continue;
            sb.append((char)(i + 97));
            visited[i] = true;
            if (findLexicographicallySmallest(chars, sb, currentChar+1, find+1, N, count, visited))
                return true;
            visited[i] = false;
            sb.deleteCharAt(sb.length()-1);
        }
        return false;
    }

    private int findIndex(HashMap<Integer, List<Integer>> chars, int currentChar, int index, int N) {
        if (index > N)
            return -1;
        List<Integer> indices = chars.get(currentChar);
        if (indices.isEmpty())
            return -1;
        int low = 0, high = indices.size()-1;
        int mid;

        while ( low <= high) {
            if (low == high)
                return (indices.get(high) >= index) ? indices.get(high) : -1;
            mid = (high - low)/2 + low;
            if (indices.get(mid) < index)
                low = mid + 1;
            else if (indices.get(mid) >= index)
                high = mid;
        }
        return (indices.get(high) >= index) ? indices.get(high) : -1;
    }

    public static void main(String[] args) {
        SmallestSubsequenceOfDistinctCharacters S = new SmallestSubsequenceOfDistinctCharacters();
        System.out.println(S.smallestSubsequence("cbacdcbc"));
        System.out.println(S.smallestSubsequence("bcabc"));
    }
}
