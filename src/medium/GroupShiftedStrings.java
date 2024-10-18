package medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GroupShiftedStrings {
    public List<List<String>> groupStrings(String[] strings) {
        int N = strings.length;
        HashMap<String, List<String>> groups = new HashMap<>();
        for (int i = 0; i < N; i++) {
            String curr = strings[i];
            int length = curr.length();
            int baseDiff = curr.charAt(0) - 'a';
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < length; j++) {
                char c = (char)((((curr.charAt(j) - 'a') - baseDiff + 26) % 26) + 'a');
                sb.append(c);
            }
            String base = sb.toString();
            List<String> group = groups.getOrDefault(base, new ArrayList<>());
            group.add(curr);
            groups.put(base, group);
        }
        List<List<String>> result = new ArrayList<>();
        for (String base : groups.keySet())
            result.add(groups.get(base));
        return result;
    }

    public static void main(String[] args) {
        GroupShiftedStrings G = new GroupShiftedStrings();
        System.out.println(G.groupStrings(new String[]{"abc","bcd","acef","xyz","az","ba","a","z"}));
        System.out.println(G.groupStrings(new String[]{"a"}));
    }
}
