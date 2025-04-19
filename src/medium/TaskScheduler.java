package medium;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class TaskScheduler {
    public int leastInterval(char[] tasks, int n) {
        if(tasks == null || tasks.length == 0)
            return 0;
        if (n == 0)
            return tasks.length;
        PriorityQueue<Task> heap = new PriorityQueue<>((o1, o2) -> {
            if (o1.count == o2.count)
                return o1.nextAvailable - o2.nextAvailable;
            return o2.count - o1.count;
        });
        int[] count = new int[26];
        for (char task : tasks)
            count[task - 'A']++;
        for (int i = 0; i < 26; i++) {
            if (count[i] > 0)
                heap.add(new Task((char) (i + 'A'), count[i], 0));
        }
        int steps = 0;
        while (!heap.isEmpty()) {
            List<Task> taskList = new ArrayList<>();
            while (!heap.isEmpty() && heap.peek().nextAvailable > steps)
                taskList.add(heap.poll());
            if (!heap.isEmpty()) {
                Task t = heap.poll();
                if (t.count > 1)
                    heap.add(new Task(t.task, t.count-1, steps + n + 1));
            }
            steps++;
            heap.addAll(taskList);
        }
        return steps;
    }

    public static void main(String[] args) {
        TaskScheduler T = new TaskScheduler();
        System.out.println(T.leastInterval(new char[]{'A', 'A', 'A', 'B', 'B', 'B'}, 2));
    }
}
class Task {
    char task;
    int count;
    int nextAvailable;

    public Task(char task, int count, int nextAvailable) {
        this.task = task;
        this.count = count;
        this.nextAvailable = nextAvailable;
    }

    @Override
    public String toString() {
        return "Task{" +
                "task=" + task +
                ", count=" + count +
                ", nextAvailable=" + nextAvailable +
                '}';
    }
}
