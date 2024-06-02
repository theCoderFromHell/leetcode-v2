package medium;

import java.util.HashMap;

public class OptimalPartitionOfString {
    public int partitionString(String s) {
        int N = s.length();
        HashMap<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < 26; i++)
            map.put(i, -1);
        int breaks = 1;
        int start = 0;
        int end = 0;
        while (end < N) {
            int lastSeen = map.get(s.charAt(end) - 'a');
            if (lastSeen >= start) {
                breaks++;
                start = end;
            }
            map.put(s.charAt(end) - 'a', end);
            end++;
        }
        return breaks;
    }

    public static void main(String[] args) {
        OptimalPartitionOfString o = new OptimalPartitionOfString();
        System.out.println(o.partitionString("abacaba"));
        System.out.println(o.partitionString("ssssss"));
    }
}
