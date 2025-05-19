package medium;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class MinimumGeneticMutation {
    public int minMutation(String startGene, String endGene, String[] bank) {
        HashSet<String> validBank = new HashSet<>(Arrays.asList(bank));
        validBank.add(startGene);
        Queue<String> queue = new LinkedList<>();
        HashSet<String> visited = new HashSet<>();
        queue.add(startGene);
        visited.add(startGene);
        char[] validChars = new char[]{'A', 'C', 'G', 'T'};
        int count = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                String front = queue.poll();
                if (front.equals(endGene))
                    return count;
                for (int i = 0; i < 8; i++) {
                    StringBuilder sb = new StringBuilder(front);
                    for (int j = 0; j < 4; j++) {
                        sb.setCharAt(i, validChars[j]);
                        String next = sb.toString();
                        if (!visited.contains(next) && validBank.contains(next)) {
                            queue.add(next);
                            visited.add(next);
                        }
                    }
                }
            }
            count++;
        }
        return -1;
    }

    public static void main(String[] args) {
        MinimumGeneticMutation M = new MinimumGeneticMutation();
        System.out.println(M.minMutation("AACCGGTT", "AACCGGTA", new String[]{"AACCGGTA"}));
        System.out.println(M.minMutation("AACCGGTT", "AAACGGTA", new String[]{"AACCGGTA","AACCGCTA","AAACGGTA"}));
    }
}
