package interviews.oa.hackerrank;

import java.util.*;

public class SubarrayBitwiseOR {
    public int countSubarraysV1(int[] arr) {
        Set<Integer> elements = new HashSet<>();
        for (int num : arr)
            elements.add(num);
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            int currentOR = arr[i];
            if (elements.contains(currentOR))
                count++;
            for (int j = i + 1; j < arr.length; j++) {
                currentOR |= arr[j];
                if (elements.contains(currentOR))
                    count++;
                if (currentOR == (currentOR | arr[j]))
                    break;
            }
        }
        return count;
    }

    public int countSubarrays(int[] arr) {
        int n = arr.length;
        int totalCount = 0;
        Map<Integer, List<Integer>> prev = new HashMap<>();
        for (int i = 0; i < n; i++) {
            Map<Integer, List<Integer>> curr = new HashMap<>();
            curr.put(arr[i], new ArrayList<>(List.of(i)));
            for (Map.Entry<Integer, List<Integer>> entry : prev.entrySet()) {
                int newOr = entry.getKey() | arr[i];
                curr.computeIfAbsent(newOr, k -> new ArrayList<>()).addAll(entry.getValue());
            }
            for (Map.Entry<Integer, List<Integer>> entry : curr.entrySet()) {
                int orVal = entry.getKey();
                for (int start : entry.getValue()) {
                    for (int k = start; k <= i; k++) {
                        if (arr[k] == orVal) {
                            totalCount++;
                            System.out.print("Valid subarray: [");
                            for (int m = start; m <= i; m++) {
                                System.out.print(arr[m]);
                                if (m < i) System.out.print(", ");
                            }
                            System.out.println("]");
                            break;
                        }
                    }
                }
            }
            prev = curr;
        }
        return totalCount;
    }

    public static void main(String[] args) {
        int[] arr = {1, 6, 7};
        SubarrayBitwiseOR S = new SubarrayBitwiseOR();
        System.out.println(S.countSubarrays(arr));
    }
}
