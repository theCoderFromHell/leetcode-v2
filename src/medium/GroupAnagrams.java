package medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GroupAnagrams {
    public List<List<String>> groupAnagrams(String[] strs) {
        if(strs.length == 0)
            return Arrays.asList(Arrays.asList());
        List<List<String>> result = new ArrayList<>();
        int size = strs.length;
        HashMap<String, ArrayList<Integer>> map = new HashMap<>();
        for (int i = 0; i < size; i++) {
            char[] strChars = strs[i].toCharArray();
            Arrays.sort(strChars);
            String sorted = new String(strChars);
            ArrayList<Integer> strings = map.getOrDefault(sorted, new ArrayList<>());
            strings.add(i);
            map.put(sorted, strings);
        }
        for(String sorted : map.keySet()){
            List<String> list = new ArrayList<>();
            for (int index : map.get(sorted))
                list.add(strs[index]);
            result.add(list);
        }
        return result;
    }

    public static void main(String[] args) {
        GroupAnagrams G = new GroupAnagrams();
        System.out.println(G.groupAnagrams(new String[]{"eat","tea","tan","ate","nat","bat"}));
        System.out.println(G.groupAnagrams(new String[]{""}));
        System.out.println(G.groupAnagrams(new String[]{"a"}));
    }
}
