package medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PartitionLabels {

    public List<Integer> partitionLabels(String s) {
        List<Integer> result = new ArrayList<>();
        int N = s.length();
        int[] lastIndex = new int[26];
        for (int i = 0; i < N; i++)
            lastIndex[s.charAt(i) - 'a'] = i;
        int start = 0;
        int end = lastIndex[s.charAt(start) - 'a'];
        int index = 0;
        while (index < N) {
            end = Math.max(end, lastIndex[s.charAt(index) - 'a']);
            if (index == end) {
                result.add(end - start + 1);
                start = end + 1;
            }
            index++;
        }
        return result;
    }


    public List<Integer> partitionLabelsV2(String s) {
        List<Integer> result = new ArrayList<>();
        int N = s.length();
        HashMap<Character, Integer> lastIndex = new HashMap<>();
        for (int i = 0; i < N; i++)
            lastIndex.put(s.charAt(i), i);
        int start = 0;
        int end = lastIndex.get(s.charAt(start));
        int index = 0;
        while (index < N) {
            end = Math.max(end, lastIndex.get(s.charAt(index)));
            if (index == end) {
                result.add(end - start + 1);
                start = end + 1;
            }
            index++;
        }
        return result;
    }

    public static void main(String[] args) {
        PartitionLabels P = new PartitionLabels();
        System.out.println(P.partitionLabels("ababcbacadefegdehijhklij"));
        System.out.println(P.partitionLabels("eccbbbbdec"));

    }
}
