package easy;

import java.util.LinkedList;

public class CrawlerLogFolder {
    public int minOperations(String[] logs) {
        int count = 0;
        for (String log : logs) {
            switch (log) {
                case "../" :
                    if (count > 0)
                        count--;
                case "./" :
                    break;
                default:
                    count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        CrawlerLogFolder c = new CrawlerLogFolder();
        System.out.println(c.minOperations(new String[]{"d1/","d2/","../","d21/","./"}));
        System.out.println(c.minOperations(new String[]{"d1/","d2/","./","d3/","../","d31/"}));
        System.out.println(c.minOperations(new String[]{"d1/","../","../","../"}));

    }
}
