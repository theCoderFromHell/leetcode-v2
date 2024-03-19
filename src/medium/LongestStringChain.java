package medium;

import java.util.*;

public class LongestStringChain {

    public static int longestStrChain(String[] words) {
        int N = words.length;
        HashMap<String,Integer> chain = new HashMap<>();
        HashSet<String> wordsSet = new HashSet<>(List.of(words));
        Arrays.sort(words, Comparator.comparing(String::length));
        int maxChainLength = 1;
        for (String word : words) {
            int length = word.length();
            int maxLength = chain.getOrDefault(word, 1);
            for (int j = 0; j < length; j++) {
                StringBuilder sb = new StringBuilder(word);
                String pre = sb.deleteCharAt(j).toString();
                if (wordsSet.contains(pre))
                    maxLength = Math.max(maxLength, chain.get(pre) + 1);
            }
            chain.put(word, maxLength);
            maxChainLength = Math.max(maxChainLength, maxLength);
        }
        return maxChainLength;
    }

    public static int longestStrChainV2(String[] words) {
        int N = words.length;
        HashMap<String,Integer> chain = new HashMap<>();
        HashSet<String> wordsSet = new HashSet<>(List.of(words));
        int maxLength = 0;
        for (int i = 0; i < N; i++) {
            if (!chain.containsKey(words[i]))
                maxLength = Math.max(maxLength, dfs(words[i], chain, wordsSet));
        }
        return maxLength;
    }

    private static int dfs(String word, HashMap<String, Integer> chain, HashSet<String> wordsSet) {
        int length = word.length();
        int maxLength = chain.getOrDefault(word, 1);
        for (int j = 0; j < length; j++) {
            StringBuilder sb = new StringBuilder(word);
            String pre = sb.deleteCharAt(j).toString();
            if (wordsSet.contains(pre)) {
                int curr = (chain.containsKey(pre)) ? chain.get(pre) + 1 : dfs(pre, chain, wordsSet) + 1;
                maxLength = Math.max(maxLength, curr);
            }
        }
        chain.put(word, maxLength);
        return maxLength;
    }

    public static void main(String[] args) {
        System.out.println(longestStrChain(new String[]{"a","b","ba","bca","bda","bdca"}));
        System.out.println(longestStrChain(new String[]{"xbc","pcxbcf","xb","cxbc","pcxbc"}));
        System.out.println(longestStrChain(new String[]{"abcd","dbqca"}));
    }
}
