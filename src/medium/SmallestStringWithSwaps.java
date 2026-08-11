package medium;

import java.util.*;

// https://leetcode.com/problems/smallest-string-with-swaps/
public class SmallestStringWithSwaps {
    int[] parent;
    int[] rank;
    public String smallestStringWithSwaps(String s, List<List<Integer>> pairs) {
        int size = pairs.size();
        int N = s.length();
        parent = new int[N];
        rank = new int[N];
        for (int i = 0; i < N; i++) {
            parent[i] = i;
            rank[i] = 1;
        }
        for (int i = 0; i < size; i++) {
            union(pairs.get(i).get(0), pairs.get(i).get(1));
        }
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < N; i++) {
            int root = find(i);
            map.computeIfAbsent(root, integer -> new ArrayList<>()).add(i);
        }
        StringBuilder sb = new StringBuilder(s);
        for (int root : map.keySet()) {
            List<Integer> group = map.get(root);
            int length = group.size();
            group.sort(Comparator.naturalOrder());
            int[] characters = new int[length];
            for (int i = 0; i < length; i++) {
                characters[i] = s.charAt(group.get(i)) - 'a';
            }
            Arrays.sort(characters);
            for (int i = 0; i < length; i++) {
                sb.setCharAt(group.get(i), (char)(characters[i] + 'a'));
            }
        }
        return sb.toString();
    }

    /*
     * Revision Note — Smallest String With Swaps (Medium)
     * Pattern: Union-Find to group connected indices, then sort-and-place within each group
     * Key Insight: "Swap any pair any number of times" means transitively connected indices
     *              form a free-rearrangement group — sort chars within each component and
     *              place them back at sorted indices for the lexicographically smallest result.
     * Gotchas:
     *   - Use nodes.get(i) (not i) when collecting characters — i is just the loop counter,
     *     not the actual index in the string.
     *   - Initialize StringBuilder from s, not empty — setCharAt on empty throws AIOOB.
     *   - Sort both the index list and the character array independently, then zip them:
     *     smallest char → smallest index.
     *   - Local variable shadowing the parent[] field causes confusion; name it root.
     */
    public static void main(String[] args) {
        SmallestStringWithSwaps S = new SmallestStringWithSwaps();

        // Test 1: LeetCode example 1
        System.out.println("Test 1: " + S.smallestStringWithSwaps("dcab", List.of(List.of(0,3), List.of(1,2))) + " (Expected: bacd)");

        // Test 2: LeetCode example 2 — extra pair connects both components
        System.out.println("Test 2: " + S.smallestStringWithSwaps("dcab", List.of(List.of(0,3), List.of(1,2), List.of(0,2))) + " (Expected: abcd)");

        // Test 3: LeetCode example 3 — transitive chain covers all indices
        System.out.println("Test 3: " + S.smallestStringWithSwaps("cba", List.of(List.of(0,1), List.of(1,2))) + " (Expected: abc)");

        // Test 4: empty pairs — no swaps possible, string unchanged
        System.out.println("Test 4: " + S.smallestStringWithSwaps("dcab", List.of()) + " (Expected: dcab)");

        // Test 5: single character
        System.out.println("Test 5: " + S.smallestStringWithSwaps("a", List.of()) + " (Expected: a)");

        // Test 6: all indices in one component — full sort
        System.out.println("Test 6: " + S.smallestStringWithSwaps("zyxw", List.of(List.of(0,1), List.of(1,2), List.of(2,3))) + " (Expected: wxyz)");
    }

    private void union(int a, int b) {
        int parentA = find(a);
        int parentB = find(b);
        if (parentA != parentB) {
            if (rank[parentA] > rank[parentB]) {
                parent[parentB] = parentA;
                rank[parentA] += rank[parentB];
            } else {
                parent[parentA] = parentB;
                rank[parentB] += rank[parentA];
            }
        }
    }

    private int find(int node) {
        if (parent[node] != node)
            parent[node] = find(parent[node]);
        return parent[node];
    }
}
