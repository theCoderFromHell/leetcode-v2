package medium;

import java.util.Arrays;
import java.util.List;

public class MinimumProcessingTime {
    public int minProcessingTime(List<Integer> processorTime, List<Integer> tasks) {
        int processorsSize = processorTime.size();
        int taskSize = tasks.size();
        int[] processors = new int[processorsSize];
        int[] allTasks = new int[taskSize];
        for (int i = 0; i < processorsSize; i++)
            processors[i] = processorTime.get(i);
        for (int i = 0; i < taskSize; i++)
            allTasks[i] = tasks.get(i);
        Arrays.sort(processors);
        Arrays.sort(allTasks);
        int time = 0;
        int taskIndex = taskSize-1;
        for (int i = 0; i < processorsSize; i++) {
            time = Math.max(time, processors[i] + allTasks[taskIndex]);
            taskIndex = taskIndex - 4;
        }
        return time;
    }

    public static void main(String[] args) {
        MinimumProcessingTime M = new MinimumProcessingTime();
        System.out.println(M.minProcessingTime(Arrays.asList(8, 10), Arrays.asList(2,2,3,1,8,7,4,5)));
        System.out.println(M.minProcessingTime(Arrays.asList(10,20), Arrays.asList(2,3,1,2,5,8,4,3)));
    }
}
