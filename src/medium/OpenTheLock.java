package medium;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class OpenTheLock {
    public int openLock(String[] deadends, String target) {
        HashSet<String> visited = new HashSet<>(Arrays.asList(deadends));
        if (visited.contains("0000"))
            return -1;
        Queue<Pattern> queue = new LinkedList<>();
        queue.add(new Pattern("0000", 0));
        visited.add("0000");
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                Pattern current = queue.poll();
                if (current != null && current.pattern.equals(target))
                    return current.stepCount;
                for (int i = 0; i < 4; i++) {
                    StringBuilder sb = new StringBuilder(current.pattern);
                    sb.setCharAt(i, getNextChar(current.pattern.charAt(i)));
                    if (!visited.contains(sb.toString())) {
                        queue.add(new Pattern(sb.toString(), current.stepCount + 1));
                        visited.add(sb.toString());
                    }

                    sb = new StringBuilder(current.pattern);
                    sb.setCharAt(i, getPrevChar(current.pattern.charAt(i)));
                    if (!visited.contains(sb.toString())) {
                        queue.add(new Pattern(sb.toString(), current.stepCount + 1));
                        visited.add(sb.toString());
                    }
                }
            }
        }
        return -1;
    }

    private char getNextChar(char c) {
        if (c == '9')
            return '0';
        return (char)(c+1);
    }

    private char getPrevChar(char c) {
        if (c == '0')
            return '9';
        return (char)(c-1);
    }

    class Pattern {
        String pattern;
        int stepCount;

        public Pattern(String pattern, int stepCount) {
            this.stepCount = stepCount;
            this.pattern = pattern;
        }

        @Override
        public String toString() {
            return "Pattern{" +
                    "pattern='" + pattern + '\'' +
                    ", stepCount=" + stepCount +
                    '}';
        }
    }

    public static void main(String[] args) {
        OpenTheLock O = new OpenTheLock();
        System.out.println(O.openLock(new String[]{"0201","0101","0102","1212","2002"}, "0202"));
        System.out.println(O.openLock(new String[]{"8888"}, "0009"));
    }

}
