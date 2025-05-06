package medium;

import common.ListNode;

import java.util.ArrayList;
import java.util.List;

public class FindTheMinimumAndMaximumNumberOfNodesBetweenCriticalPoints {
    int minGap = Integer.MAX_VALUE;
    int maxGap = -1;
    public int[] nodesBetweenCriticalPoints(ListNode head) {
        if (head == null || head.next == null || head.next.next == null)
            return new int[]{-1,-1};
        List<Integer> criticalPoints = new ArrayList<>();
        solve(head, null, criticalPoints, 0);
        return new int[]{minGap == Integer.MAX_VALUE ? -1 : minGap, maxGap};
    }

    private void solve(ListNode head, ListNode previous, List<Integer> criticalPoints, int index) {
        if (head == null) {
            if (!criticalPoints.isEmpty() && criticalPoints.size() > 1)
                maxGap = criticalPoints.getLast() - criticalPoints.getFirst();
            return;
        }
        if (previous != null && head.next != null) {
            if (previous.val < head.val && head.val > head.next.val || previous.val > head.val && head.val < head.next.val) {
                if (!criticalPoints.isEmpty())
                    minGap = Math.min(minGap, index - criticalPoints.getLast());
                criticalPoints.add(index);
            }
        }
        solve(head.next, head, criticalPoints, index+1);
    }
}
