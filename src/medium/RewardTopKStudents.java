package medium;

import java.util.*;

// https://leetcode.com/problems/reward-top-k-students/
public class RewardTopKStudents {
    public List<Integer> topStudents(String[] positive_feedback, String[] negative_feedback, String[] report, int[] student_id, int k) {
        HashSet<String> positives = new HashSet<>();
        HashSet<String> negatives = new HashSet<>();

        positives.addAll(Arrays.asList(positive_feedback));
        negatives.addAll(Arrays.asList(negative_feedback));
        PriorityQueue<int[]> pq = new PriorityQueue<>(k, (a, b) -> {
            if (a[0] == b[0])
                return a[1] - b[1];
            return b[0] - a[0];
        });
        int size = report.length;
        for (int i = 0; i < size; i++) {
            int score = 0;
            String[] reportRemark = report[i].split(" ");
            for (String word : reportRemark) {
                if (positives.contains(word))
                    score += 3;
                else if (negatives.contains(word))
                    score--;
            }
            pq.add(new int[]{score, student_id[i]});
        }
        List<Integer> result = new ArrayList<>();
        while (k-- > 0)
            result.add(pq.poll()[1]);
        return result;
    }

    /*
     * Revision Note — Reward Top K Students (Medium)
     * Pattern: score every item, then take the top k under a two-key order
     *          (points descending, then id ascending).
     * Key Insight: put the feedback words in HashSets and split each report into whole words, so
     *              every word is one O(1) lookup with no false matches; the ranking is then a
     *              plain comparator over (score, id).
     * Gotchas:
     *   - Match whole words: "smartest" is not "smart" and "nothing" is not "not". Calling
     *     contains() on the report string would count both.
     *   - Every occurrence counts: "smart smart smart" scores 9. Scores can go negative.
     *   - On a tie the LOWER id ranks higher.
     *   - Subtraction in the comparator is safe here — scores lie in [-50, 150] (at most 50 words
     *     per report) and ids in [1, 10^9] — but Integer.compare is the safer habit.
     *   - This heap holds all n students (the capacity k is only a sizing hint). A heap capped at k
     *     needs the REVERSED order — worst on top: points ascending, then id DESCENDING — evicting
     *     when size > k, and the output reversed at the end.
     * Complexity: O(W + n log n) time, W = total characters across the reports and feedback words.
     *             O(P + N + n) space for the two sets and the heap. Capped at k: O(W + n log k).
     * Template:
     *   pos = set(positive), neg = set(negative)
     *   for each report i: score[i] = sum over words w of (+3 if w in pos, -1 if w in neg)
     *   order by (score desc, id asc); return the first k ids
     *   // size-k variant: min-heap on (score asc, id desc), poll when size > k, reverse the result
     */
    public static void main(String[] args) {
        RewardTopKStudents R = new RewardTopKStudents();

        // Test 1: LeetCode example 1
        System.out.println("Test 1: " + R.topStudents(new String[]{"smart", "brilliant", "studious"}, new String[]{"not"},
                new String[]{"this student is studious", "the student is smart"}, new int[]{1, 2}, 2) + " (Expected: [1, 2])");

        // Test 2: LeetCode example 2
        System.out.println("Test 2: " + R.topStudents(new String[]{"smart", "brilliant", "studious"}, new String[]{"not"},
                new String[]{"this student is not studious", "the student is smart"}, new int[]{1, 2}, 2) + " (Expected: [2, 1])");

        // Test 3: every score ties at 0, so the order falls to the ids — ascending, though they arrive unsorted
        System.out.println("Test 3: " + R.topStudents(new String[]{"good"}, new String[]{"bad"},
                new String[]{"ok", "fine", "meh"}, new int[]{5, 3, 9}, 2) + " (Expected: [3, 5])");

        // Test 4: a negative score (-3) ranks below a zero one, and "nothing" must not match the negative word "not"
        System.out.println("Test 4: " + R.topStudents(new String[]{"fine"}, new String[]{"not", "bad"},
                new String[]{"not not bad", "nothing here", "fine"}, new int[]{10, 20, 30}, 3) + " (Expected: [30, 20, 10])");

        // Test 5: every occurrence counts (3 x "smart" = 9), and "smartest" is not "smart" — whole words only
        System.out.println("Test 5: " + R.topStudents(new String[]{"smart"}, new String[]{"dull"},
                new String[]{"smart smart smart", "smartest student", "smart"}, new int[]{1, 2, 3}, 3) + " (Expected: [1, 3, 2])");

        // Test 6: a tie between ids at the constraint limits — a[1] - b[1] stays within int for ids in [1, 10^9]
        System.out.println("Test 6: " + R.topStudents(new String[]{"smart"}, new String[]{"dull"},
                new String[]{"smart", "smart"}, new int[]{1000000000, 1}, 2) + " (Expected: [1, 1000000000])");
    }
}
