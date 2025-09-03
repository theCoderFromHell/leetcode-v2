package contests.biweekly_164;

import java.util.*;

public class Solution2 {
    public int score(String[] cards, char x) {
        HashMap<String, Integer> count = new HashMap<>();
        for (String s : cards) {
            if (s.charAt(0) == x || s.charAt(1) == x)
                count.put(s, count.getOrDefault(s, 0) + 1);
        }
        List<StringCount> countList = new ArrayList<>();
        for (String s : count.keySet()) {
            countList.add(new StringCount(s, count.get(s)));
        }
        int result = 0;
        countList.sort((o1, o2) -> {
            return o2.count - o1.count;
        });
        int size = countList.size();
        for (int i = 0; i < size; i++) {
            String s = countList.get(i).s;
            if (!count.containsKey(s))
                continue;
            for (int j = size - 1; j > i; j--) {
                String k = countList.get(j).s;
                if (!count.containsKey(k))
                    continue;
                if (differbyOne(s, k)) {
                    int freq = Math.min(count.get(s), count.get(k));
                    result += freq;
                    count.put(k, count.getOrDefault(k, 0) - freq);
                    if (count.get(k) == 0)
                        count.remove(k);
                    count.put(s, count.getOrDefault(s, 0) - freq);
                    if (count.get(s) == 0) {
                        count.remove(s);
                        break;
                    }
                }
            }
        }
        return result;
    }

    private boolean differbyOne(String s, String k) {
        int count = 0;
        if (s.charAt(0) != k.charAt(0))
            count++;
        if (s.charAt(1) != k.charAt(1))
            count++;
        return count == 1;
    }

    class StringCount {
        String s;
        int count;

        public StringCount(String s, int count) {
            this.s = s;
            this.count = count;
        }
    }

    public static void main(String[] args) {
        Solution2 S = new Solution2();
        System.out.println(S.score(new String[]{"aa","ba","ac","cc","ca"}, 'a'));
        System.out.println(S.score(new String[]{"aa","ab","ba","ac"}, 'a'));
        System.out.println(S.score(new String[]{"aa","ab","ba"}, 'a'));
        System.out.println(S.score(new String[]{"aa","ab","ba","ac"}, 'b'));
        System.out.println(S.score(new String[]{"ce","ca","ca","ee","cd"}, 'c'));
    }
}
