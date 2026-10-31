package medium;

import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

// https://leetcode.com/problems/design-task-manager/
public class DesignTaskManager {

    static class TaskManager {
        PriorityQueue<int[]> tasksByPriority;
        HashMap<Integer, int[]> tasksById;
        public TaskManager(List<List<Integer>> tasks) {
            this.tasksById = new HashMap<>();
            this.tasksByPriority = new PriorityQueue<>((a, b) -> {
                if (b[2] == a[2])
                    return b[1] - a[1];
                return b[2] - a[2];
            });
            int size = tasks.size();
            for (int i = 0; i < size; i++) {
                int[] task = new int[]{tasks.get(i).get(0), tasks.get(i).get(1), tasks.get(i).get(2)};
                tasksByPriority.add(task);
                tasksById.put(tasks.get(i).get(1), task);
            }
        }

        public void add(int userId, int taskId, int priority) {
            int[] task = new int[]{userId, taskId, priority};
            tasksByPriority.add(task);
            tasksById.put(taskId, task);

        }

        public void edit(int taskId, int newPriority) {
            int userId = tasksById.get(taskId)[0];
            int[] task = new int[]{userId, taskId, newPriority};
            tasksById.put(taskId, task);
            tasksByPriority.add(task);
        }

        public void rmv(int taskId) {
            tasksById.remove(taskId);
        }

        public int execTop() {
            if (tasksByPriority.isEmpty())
                return -1;
            while (!tasksByPriority.isEmpty()) {
                int[] top = tasksByPriority.peek();
                int[] current = tasksById.get(top[1]);
                if (current != top)
                    tasksByPriority.poll();
                else
                    break;
            }
            if (tasksByPriority.isEmpty())
                return -1;
            else {
                tasksById.remove(tasksByPriority.peek()[1]);
                return tasksByPriority.poll()[0];
            }
        }
    }

    /*
     * Revision Note — Design Task Manager (Medium)
     * Pattern: lazy deletion — a max-heap of {userId, taskId, priority} arrays, plus
     *          taskId -> the CURRENT array as the source of truth. The map and the heap share
     *          the same array object for every live task.
     * Key Insight: edit and rmv only touch the map, and the heap is allowed to go stale. Every add
     *              and edit creates a NEW array, so a heap entry is live exactly when
     *              map.get(top[1]) == top — one reference comparison. execTop pops tops until
     *              that holds, so each out-of-date array is discarded exactly once.
     * Gotchas:
     *   - The first version compared fields (exists, priority, user), and both of its bugs came
     *     from that:
     *       - edit(t, samePriority) leaves two arrays with identical fields, so both looked live.
     *         Without deleting the task from the map in execTop, it ran twice (Test 7).
     *       - A four-part `A && (B || C) || D` left the user check D outside the isEmpty() guard.
     *         peek() returned null, giving a NullPointerException once every entry was stale (Test 6).
     *     Identity makes both cases fall out: the old array is simply a different object.
     *   - The identity check only works if the map and the heap hold the SAME array. Building two
     *     arrays with equal contents (as the first version's constructor did) makes every entry look stale.
     *   - A taskId removed and re-added for another user at the same priority leaves a stale
     *     array that matches on taskId and priority. A field check needs the user too; identity
     *     rejects it outright (Test 4.1).
     *   - Deleting the executed task from the map is still right (the task no longer exists, and
     *     the map stays small), though with identity it's no longer what makes Test 7 pass.
     *   - Ties go to the HIGHER taskId (Test 2). userId 0 is a real answer, distinct from -1 (Test 5.2).
     *   - b[2] - a[2] can't overflow here (priorities are in [0, 10^9]), but Integer.compare is the habit.
     * Complexity: add / edit O(log H), rmv O(1), execTop amortised O(log H) — each snapshot is
     *             pushed once and popped at most once. H <= n + calls, which is also the space.
     * Alternative — a TreeSet of live tasks ordered (priority desc, taskId desc) + taskId -> task:
     *   edit = remove, change, re-add (remove BEFORE changing the sort key, or the set can't find
     *   it). O(log n) worst case per call and no stale entries; the taskId tie-break keeps every
     *   entry distinct, so the set never merges two tasks.
     * Template:
     *   add / edit: t = new {u, id, p}; map[id] = t; heap.push(t)     // the SAME object in both
     *   rmv:        map.remove(id)
     *   execTop:    while heap not empty and map[heap.top.id] != heap.top: heap.pop()
     *               if empty: return -1; map.remove(top.id); return heap.pop().user
     */
    public static void main(String[] args) {
        // Test 1: LeetCode example 1, replayed call by call on this instance
        TaskManager T = new TaskManager(List.of(List.of(1, 101, 10), List.of(2, 102, 20), List.of(3, 103, 15)));
        T.add(4, 104, 5);
        T.edit(102, 8);
        System.out.println("Test 1.1: " + T.execTop() + " (Expected: 3)");
        T.rmv(101);
        T.add(5, 105, 15);
        System.out.println("Test 1.2: " + T.execTop() + " (Expected: 5)");

        // Test 2: all tasks share one priority — ties go to the HIGHER taskId: 9, then 5, then 3
        T = new TaskManager(List.of(List.of(1, 5, 7), List.of(2, 9, 7), List.of(3, 3, 7)));
        System.out.println("Test 2.1: " + T.execTop() + " (Expected: 2)");
        System.out.println("Test 2.2: " + T.execTop() + " (Expected: 1)");
        System.out.println("Test 2.3: " + T.execTop() + " (Expected: 3)");
        System.out.println("Test 2.4: " + T.execTop() + " (Expected: -1)");

        // Test 3: one task's priority edited twice — stale snapshots of task 10 at priorities 5 and 9 stay
        // in the heap, and the 9 outranks task 20, so it must be recognised as stale and discarded
        T = new TaskManager(List.of(List.of(1, 10, 5), List.of(2, 20, 6)));
        T.edit(10, 9);
        T.edit(10, 1);
        System.out.println("Test 3.1: " + T.execTop() + " (Expected: 2)");
        System.out.println("Test 3.2: " + T.execTop() + " (Expected: 1)");
        System.out.println("Test 3.3: " + T.execTop() + " (Expected: -1)");

        // Test 4: a removed taskId added again for another user at the same priority — the stale {1, 50, 10}
        // matches on taskId and priority, so a field-by-field check also needs the user; the identity check rejects
        // it because it's a different array. Then an executed taskId added again
        T = new TaskManager(List.of(List.of(1, 50, 10)));
        T.rmv(50);
        T.add(2, 50, 10);
        System.out.println("Test 4.1: " + T.execTop() + " (Expected: 2)");
        System.out.println("Test 4.2: " + T.execTop() + " (Expected: -1)");
        T.add(3, 60, 4);
        System.out.println("Test 4.3: " + T.execTop() + " (Expected: 3)");
        T.add(4, 60, 4);
        System.out.println("Test 4.4: " + T.execTop() + " (Expected: 4)");
        System.out.println("Test 4.5: " + T.execTop() + " (Expected: -1)");

        // Test 5: userId, taskId and priority at 0 and at their upper bounds — user 0 is a real answer, not -1,
        // and a 10^9 priority keeps the comparator's subtraction inside int only because priorities are >= 0
        T = new TaskManager(List.of(List.of(0, 0, 0)));
        T.add(100000, 100000, 1000000000);
        System.out.println("Test 5.1: " + T.execTop() + " (Expected: 100000)");
        System.out.println("Test 5.2: " + T.execTop() + " (Expected: 0)");
        System.out.println("Test 5.3: " + T.execTop() + " (Expected: -1)");

        // Test 6: every task removed, so the heap holds only stale entries — popping the last one must
        // end in -1, not a NullPointerException from peek() on an empty heap. The first version's user check
        // sat outside the isEmpty() guard and threw here
        T = new TaskManager(List.of(List.of(2, 1, 4)));
        T.rmv(1);
        System.out.println("Test 6.1: " + T.execTop() + " (Expected: -1)");

        // Test 7: a task edited to the priority it already has — the heap now holds two arrays with identical
        // fields. The first version compared fields, saw both as live, only polled the heap, and returned 7 twice;
        // the identity check sees the original array as stale
        T = new TaskManager(List.of(List.of(7, 1, 4)));
        T.edit(1, 4);
        System.out.println("Test 7.1: " + T.execTop() + " (Expected: 7)");
        System.out.println("Test 7.2: " + T.execTop() + " (Expected: -1)");
    }
}
