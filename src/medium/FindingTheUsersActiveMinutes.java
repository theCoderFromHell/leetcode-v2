package medium;

import java.util.HashMap;
import java.util.HashSet;

public class FindingTheUsersActiveMinutes {
    public int[] findingUsersActiveMinutes(int[][] logs, int k) {
        int size = logs.length;
        HashMap<Integer,HashSet<Integer>> userLog = new HashMap<>();
        for (int i = 0; i < size; i++) {
            int userId = logs[i][0];
            int time = logs[i][1];
            HashSet<Integer> minutes = userLog.getOrDefault(userId, new HashSet<>());
            minutes.add(time);
            userLog.put(userId, minutes);
        }
        HashMap<Integer,Integer> uamCount = new HashMap<>();
        for (int userId : userLog.keySet()) {
            int count = userLog.get(userId).size();
            uamCount.put(count, uamCount.getOrDefault(count, 0) + 1);
        }
        int[] result = new int[k];
        for (int i = 0; i < k; i++)
            result[i] = uamCount.getOrDefault(i + 1, 0);
        return result;
    }
}
