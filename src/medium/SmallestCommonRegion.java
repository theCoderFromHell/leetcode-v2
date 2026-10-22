package medium;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

// https://leetcode.com/problems/smallest-common-region/
public class SmallestCommonRegion {
    public String findSmallestRegion(List<List<String>> regions, String region1, String region2) {
        int size = regions.size();
        HashMap<String, Node> nodes = new HashMap<>();
        for (int i = 0; i < size; i++) {
            List<String> region = regions.get(i);
            Node node = nodes.computeIfAbsent(region.getFirst(), Node::new);
            int length = region.size();
            for (int j = 1; j < length; j++) {
                Node child = nodes.computeIfAbsent(region.get(j), Node::new);
                child.parent = node;
            }
        }
        Node p = nodes.get(region1);
        Node q = nodes.get(region2);
        if (p.parent == null)
            return region1;
        if (q.parent== null)
            return region2;
        HashSet<String> pathToTop = new HashSet<>();
        while (p != null) {
            pathToTop.add(p.name);
            p = p.parent;
        }

        while (q != null) {
            if (pathToTop.contains(q.name))
                return q.name;
            q = q.parent;
        }
        return null;
    }

    /*
     * Revision Note - Smallest Common Region (Medium, Premium)
     *
     * Pattern: LCA in an n-ary tree given only as parent -> children lists. Build a child -> parent
     * map, then walk upward: no root object and no top-down recursion needed.
     *
     * Key Insight: "smallest region containing both" = lowest common ancestor, where a region
     * counts as its own ancestor. Put region1 and ALL its ancestors in a set; the first region on
     * region2's upward walk that's in the set is the answer.
     *
     * Gotchas:
     * - Start the first walk at region1 ITSELF, not its parent. Starting at the parent dropped
     *   region1 from the set, so "North America contains Quebec" walked past it to Earth.
     * - Register every child in the map as it's created. A child built but never put() is lost:
     *   its parent link disappears when it later shows up as a list head (Test 6), and leaf
     *   lookups like nodes.get("Quebec") return null.
     * - Lists can come in any order, so a name may appear as a child before or after its own list.
     * - With the walk starting at region1, the root and "one contains the other" cases need no
     *   special branch: the root early returns are shortcuts, not needed for correctness. (The
     *   first draft checked q.name == null, which is never true; the check is on q.parent.)
     * - computeIfAbsent(name, Node::new) is the get-or-create in one call, and it registers the
     *   node, so the unregistered-child bug can't happen.
     *
     * Complexity: O(N) time to build the map, N = total names (<= 2e5), plus O(depth) per walk.
     * O(N) space for the map and set.
     *
     * Alternative (no set): a Map<String, String> parent is enough (no Node class), then the
     * 160-style two-pointer: walk a and b upward, jump each to the other's start when it runs
     * off the top; they meet at the LCA after at most depth(a) + depth(b) steps. O(1) extra
     * beyond the map.
     *
     * Template:
     *   parent = {}; for list: for child in list[1:]: parent[child] = list[0]
     *   seen = {}; for x = r1; x != null; x = parent[x]: seen.add(x)
     *   for y = r2; ; y = parent[y]: if y in seen: return y
     */

    public static void main(String[] args) {
        SmallestCommonRegion S = new SmallestCommonRegion();
        List<List<String>> world = List.of(
                List.of("Earth", "North America", "South America"),
                List.of("North America", "United States", "Canada"),
                List.of("United States", "New York", "Boston"),
                List.of("Canada", "Ontario", "Quebec"),
                List.of("South America", "Brazil"));

        // Test 1: LeetCode example 1
        System.out.println("Test 1: " + S.findSmallestRegion(world, "Quebec", "New York") + " (Expected: North America)");

        // Test 2: LeetCode example 2
        System.out.println("Test 2: " + S.findSmallestRegion(world, "Canada", "South America") + " (Expected: Earth)");

        // Test 3: region1 contains region2: region1 must be in its own ancestor set (the start-at-parent bug returned Earth)
        System.out.println("Test 3: " + S.findSmallestRegion(world, "North America", "Quebec") + " (Expected: North America)");

        // Test 4: region2 is the root: a region contains itself, so the root is the answer
        System.out.println("Test 4: " + S.findSmallestRegion(world, "Brazil", "Earth") + " (Expected: Earth)");

        // Test 5: siblings: the answer is their shared direct parent
        System.out.println("Test 5: " + S.findSmallestRegion(world, "Ontario", "Quebec") + " (Expected: Canada)");

        // Test 6: lists not given top-down: C is a child before its own list, so it must already be in the map (the unregistered-child bug)
        System.out.println("Test 6: " + S.findSmallestRegion(List.of(List.of("B", "C"), List.of("C", "D"), List.of("A", "B", "X")), "D", "X") + " (Expected: A)");

        // Test 7: region2 contains region1, deep inside: the upward walk from region2 hits region2 itself
        System.out.println("Test 7: " + S.findSmallestRegion(world, "Boston", "United States") + " (Expected: United States)");

        // Test 8: region1 is the root
        System.out.println("Test 8: " + S.findSmallestRegion(world, "Earth", "Quebec") + " (Expected: Earth)");
    }

    class Node {
        String name;
        Node parent;

        public Node(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "name='" + name + '\'' +
                    ", parent=" + ((parent == null) ? "null" : parent.name) +
                    '}';
        }
    }
}
