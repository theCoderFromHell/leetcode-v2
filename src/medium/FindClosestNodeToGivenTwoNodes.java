package medium;

import java.util.Arrays;

// https://leetcode.com/problems/find-closest-node-to-given-two-nodes/
public class FindClosestNodeToGivenTwoNodes {
    public int closestMeetingNode(int[] edges, int node1, int node2) {
        int size = edges.length;
        int[] distance1 = new int[size];
        int[] distance2 = new int[size];
        Arrays.fill(distance1, -1);
        Arrays.fill(distance2, -1);
        int n = node1;
        int length = 0;
        boolean[] visited = new boolean[size];
        while (n!= -1 && !visited[n]) {
            distance1[n] = length++;
            visited[n] = true;
            n = edges[n];
        }
        n = node2;
        length = 0;
        visited = new boolean[size];
        while (n!= -1 && !visited[n]) {
            distance2[n] = length++;
            visited[n] = true;
            n = edges[n];
        }
        int result = Integer.MAX_VALUE;
        int result_node = Integer.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            if (distance1[i] != -1 && distance2[i] != -1) {
                int d = Math.max(distance1[i], distance2[i]);
                if (d < result || (d == result && i < result_node)) {
                    result = d;
                    result_node = i;
                }
            }
        }
        return result_node == Integer.MAX_VALUE ? -1 : result_node;
    }

    public static void main(String[] args) {
        FindClosestNodeToGivenTwoNodes F = new FindClosestNodeToGivenTwoNodes();
        // Basic — node 2 reachable from both at dist 1 each — 2
        System.out.println(F.closestMeetingNode(new int[]{2, 2, 3, -1}, 0, 1));
        // node2 is the meeting point itself — 2
        System.out.println(F.closestMeetingNode(new int[]{1, 2, -1}, 0, 2));
        // No common reachable node — -1
        System.out.println(F.closestMeetingNode(new int[]{-1, -1}, 0, 1));
        // Cycle: 0->1->0, node2=0 — must not infinite loop — 0
        System.out.println(F.closestMeetingNode(new int[]{1, 0}, 0, 0));
        // Both paths meet at node 2, only common reachable node — 2
        System.out.println(F.closestMeetingNode(new int[]{2, 2, -1}, 0, 1));
        // node1 == node2 — answer is node itself — 1
        System.out.println(F.closestMeetingNode(new int[]{0, 2, -1}, 1, 1));
    }
}
