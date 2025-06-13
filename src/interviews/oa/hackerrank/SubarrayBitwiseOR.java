package interviews.oa.hackerrank;

import java.util.*;

public class SubarrayBitwiseOR {

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
                            //System.out.print("Valid subarray: [");
                            for (int m = start; m <= i; m++) {
                                //System.out.print(arr[m]);
                                //if (m < i) System.out.print(", ");
                            }
                            //System.out.println("]");
                            break;
                        }
                    }
                }
            }
            prev = curr;
        }
        return totalCount;
    }

    public int countSubarraysBruteForce(int[] arr) {
        int size = arr.length;
        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = i; j < size; j++) {
                int or = 0;
                HashSet<Integer> set = new HashSet<>();
                for (int k = i; k <= j; k++) {
                    or |= arr[k];
                    set.add(arr[k]);
                }
                if (set.contains(or))
                    count++;
            }
        }
        return count;
    }

    public long countValidSubarraysByClaudeSimple(int[] arr) {
        int n = arr.length;
        int count = 0;

        for (int i = 0; i < n; i++) {
            int currentOR = 0;
            Set<Integer> elementsInSubarray = new HashSet<>();

            for (int j = i; j < n; j++) {
                currentOR |= arr[j];
                elementsInSubarray.add(arr[j]);

                if (elementsInSubarray.contains(currentOR)) {
                    count++;
                }
            }
        }

        return count;
    }

    public long countValidSubarraysByClaude(int[] arr) {
        int n = arr.length;
        int count = 0;

        for (int i = 0; i < n; i++) {
            // Key optimization: at most log(max_value) distinct ORs
            Map<Integer, Boolean> distinctORs = new HashMap<>();
            Set<Integer> elements = new HashSet<>();

            for (int j = i; j < n; j++) {
                elements.add(arr[j]);

                // Update all existing OR values
                Map<Integer, Boolean> newORs = new HashMap<>();
                newORs.put(arr[j], true); // Single element

                for (int existingOR : distinctORs.keySet()) {
                    newORs.put(existingOR | arr[j], true);
                }

                distinctORs = newORs;

                // Find the actual OR of current subarray [i..j]
                int actualOR = 0;
                for (int k = i; k <= j; k++) {
                    actualOR |= arr[k];
                }

                if (elements.contains(actualOR)) {
                    count++;
                }
            }
        }

        return count;
    }

    private long countValidSubarraysByChatGPT(int[] arr) {
        int n = arr.length;
        int result = 0;

        Map<Integer, Integer> lastSeen = new HashMap<>(); // num -> last index
        Set<Integer> prev = new HashSet<>();

        for (int i = 0; i < n; i++) {
            lastSeen.put(arr[i], i); // Update position

            Set<Integer> curr = new HashSet<>();
            curr.add(arr[i]);

            for (int val : prev) {
                curr.add(val | arr[i]);
            }

            Set<Integer> next = new HashSet<>();
            for (int val : curr) {
                Integer pos = lastSeen.get(val);
                if (pos != null && pos <= i) {
                    result++;
                }
                next.add(val);
            }

            prev = next;
        }

        return result;
    }

    public long countValidSubarraysByDeepSeek(int[] arr) {
        int n = arr.length;
        long totalCount = 0;

        // For each position, track OR values and their earliest start indices
        List<Map<Integer, Integer>> dp = new ArrayList<>();

        // Precompute value positions for faster lookup
        Map<Integer, TreeSet<Integer>> valuePositions = new HashMap<>();
        for (int i = 0; i < n; i++) {
            valuePositions.computeIfAbsent(arr[i], k -> new TreeSet<>()).add(i);
        }

        for (int i = 0; i < n; i++) {
            Map<Integer, Integer> current = new HashMap<>();
            // Single element subarray
            current.put(arr[i], i);
            totalCount++;  // always valid

            if (i > 0) {
                for (Map.Entry<Integer, Integer> entry : dp.get(i-1).entrySet()) {
                    int newOr = entry.getKey() | arr[i];
                    // Track the earliest start index for this OR
                    current.put(newOr, Math.min(entry.getValue(), current.getOrDefault(newOr, Integer.MAX_VALUE)));
                }
            }

            // Check which OR values exist in their subarrays
            for (Map.Entry<Integer, Integer> entry : current.entrySet()) {
                int orVal = entry.getKey();
                if (orVal == arr[i]) continue; // already counted

                int start = entry.getValue();
                // Check if 'orVal' exists between start and i
                if (valuePositions.containsKey(orVal)) {
                    TreeSet<Integer> positions = valuePositions.get(orVal);
                    Integer pos = positions.ceiling(start);
                    if (pos != null && pos <= i) {
                        totalCount++;
                    }
                }
            }

            dp.add(current);
        }
        return totalCount;
    }



    public static void main(String[] args) {
        int[] arr = generate();
//        int[] arr = {1,6,7};
        SubarrayBitwiseOR S = new SubarrayBitwiseOR();
        long start, end, result;
        
        start = System.currentTimeMillis();
        result = S.countValidSubarraysByRandom(arr);
        end = System.currentTimeMillis();
        System.out.println("Random Code result: " + result);
        System.out.println("Time taken: " + (end - start) + "ms");
        

        start = System.currentTimeMillis();
        result = S.countValidSubarraysByClaudeSimple(arr);
        end = System.currentTimeMillis();
        System.out.println("Simple Claude Code result: " + result);
        System.out.println("Time taken: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        result = S.countValidSubarraysByClaude(arr);
        end = System.currentTimeMillis();
        System.out.println("Claude Code result: " + result);
        System.out.println("Time taken: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        result = S.countSubarrays(arr);
        end = System.currentTimeMillis();
        System.out.println("Best Code result: " + result);
        System.out.println("Time taken: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        result = S.countValidSubarraysByChatGPT(arr);
        end = System.currentTimeMillis();
        System.out.println("ChatGPT Code result: " + result);
        System.out.println("Time taken: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        result = S.countValidSubarraysByDeepSeek(arr);
        end = System.currentTimeMillis();
        System.out.println("Deepseek Code result: " + result);
        System.out.println("Time taken: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        result = S.countSubarraysBruteForce(arr);
        end = System.currentTimeMillis();
        System.out.println("Brute Force Code result: " + result);
        System.out.println("Time taken: " + (end - start) + "ms");
    }

    private long countValidSubarraysByRandom(int[] arr) {
        int n = arr.length;
        int[] right = new int[n];
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i <= n; i++) {
            int val = (i != n) ? arr[i] : -1;
            while (!stack.isEmpty() && ((arr[stack.peek()] & val) != val || arr[stack.peek()] == val)) {
                int j = stack.pop();;
                right[j] = i - j;
            }
            stack.add(i);
        }
        stack.clear();
        long ans = 0;

        for (int i = n - 1; i >= -1; i--) {
            int val = (i != -1) ? arr[i] : -1;
            while (!stack.isEmpty() && (arr[stack.peek()] & val) != val) {
                int j = stack.pop();;
                ans += (long) right[j] * (j - i);
            }
            stack.add(i);
        }
        return ans;
    }

    private static int[] generate() {
        Random r = new Random();
        int N = 100000;
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = r.nextInt(1,10);
        }
        //System.out.println(Arrays.toString(arr));
        return arr;
    }
}
