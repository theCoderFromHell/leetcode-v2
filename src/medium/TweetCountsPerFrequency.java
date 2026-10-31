package medium;

import java.util.*;

// https://leetcode.com/problems/tweet-counts-per-frequency/
public class TweetCountsPerFrequency {

    static class TweetCounts {
        HashMap<String, TreeMap<Integer,Integer>> tweets;
        public TweetCounts() {
            this.tweets = new HashMap<>();
        }

        public void recordTweet(String tweetName, int time) {
            TreeMap<Integer,Integer> tweetsAtTime = tweets.getOrDefault(tweetName, new TreeMap<>());
            tweetsAtTime.put(time, tweetsAtTime.getOrDefault(time, 0) + 1);
            tweets.put(tweetName, tweetsAtTime);
        }

        public List<Integer> getTweetCountsPerFrequency(String freq, String tweetName, int startTime, int endTime) {
            int chunkSize = ("minute".equals(freq)) ? 60 : ("hour".equals(freq)) ? 3600 : 86400;
            int chunkCount = 1 + (endTime - startTime)/chunkSize;
            List<Integer> result = new ArrayList<>(Collections.nCopies(chunkCount, 0));
            TreeMap<Integer,Integer> tweetsAtTime = tweets.get(tweetName);
            if (tweetsAtTime == null || tweetsAtTime.isEmpty())
                return result;
            for (int time : tweetsAtTime.keySet()) {
                if (time > endTime)
                    break;
                if (startTime <= time) {
                    int chunkIndex = (time - startTime) / chunkSize;
                    result.set(chunkIndex, result.get(chunkIndex) + tweetsAtTime.get(time));
                }
            }
            return result;
        }
    }

    /*
     * Revision Note — Tweet Counts Per Frequency (Medium)
     * Pattern: per-key sorted map (name -> TreeMap<time, count>) + bucketing each time by its
     *          offset from the period's start.
     * Key Insight: chunks are anchored at startTime, not at the clock. So a tweet at t lands in
     *              chunk (t - startTime) / size, there are (endTime - startTime) / size + 1 chunks,
     *              and the answer's LENGTH is fixed by the period alone — zero-fill it first, then
     *              add counts into it.
     * Gotchas:
     *   - Repeated times collapse into one TreeMap entry with a count. Add the COUNT, not 1: the
     *     first version added 1 per key and returned [2, 1, 1] for Test 2.1 (two tweets at 125).
     *   - Empty chunks still get a 0, and a name never recorded returns all zeros, not an empty
     *     list (Tests 2.2, 2.5).
     *   - new ArrayList<>(n) sets CAPACITY; size is still 0 and set(i) throws. Collections.nCopies(n, 0)
     *     alone is read-only, so copy it: new ArrayList<>(Collections.nCopies(n, 0)).
     *   - HashSet<Integer> isn't sorted (small ints only look sorted because hash == value and they
     *     fit the table) and it drops duplicates — two reasons it can't store tweet times.
     *   - Iterating the whole keySet() walks every time before startTime just to skip it;
     *     subMap(startTime, true, endTime, true) starts in O(log m).
     * Complexity: recordTweet O(log m). Query O(m_before + k + C) as written — k times in range,
     *             C <= 167 chunks — or O(log m + k + C) with subMap. Space O(distinct (name, time)).
     * Alternative: a plain list per name, sorted lazily on query, with one binary search per chunk
     *              edge — each chunk's count is the gap between two edges, O(C log n) per query,
     *              and duplicates need no counting.
     * Template:
     *   record: map[name].merge(t, 1, sum)
     *   query:  size = 60 | 3600 | 86400; res = zeros((end - start) / size + 1)
     *           for (t, c) in map[name].subMap(start, end inclusive): res[(t - start) / size] += c
     */
    public static void main(String[] args) {
        TweetCounts T = new TweetCounts();

        // Test 1: LeetCode example 1, replayed call by call on this instance
        T.recordTweet("tweet3", 0);
        T.recordTweet("tweet3", 60);
        T.recordTweet("tweet3", 10);
        System.out.println("Test 1.1: " + T.getTweetCountsPerFrequency("minute", "tweet3", 0, 59) + " (Expected: [2])");
        System.out.println("Test 1.2: " + T.getTweetCountsPerFrequency("minute", "tweet3", 0, 60) + " (Expected: [2, 1])");
        T.recordTweet("tweet3", 120);
        System.out.println("Test 1.3: " + T.getTweetCountsPerFrequency("hour", "tweet3", 0, 210) + " (Expected: [4])");

        // Test 2: a fresh instance — two names, a repeated time, a tweet outside most query ranges
        T = new TweetCounts();
        T.recordTweet("a", 5);
        T.recordTweet("a", 64);
        T.recordTweet("a", 65);
        T.recordTweet("a", 125);
        T.recordTweet("a", 125);
        T.recordTweet("b", 70);
        T.recordTweet("a", 200);

        // Test 2.1: period not starting at 0 — chunks are [5,64], [65,124], [125,130], anchored at startTime.
        // The two tweets at 125 are ONE TreeMap entry with count 2; the first version added 1 per key ([2, 1, 1])
        System.out.println("Test 2.1: " + T.getTweetCountsPerFrequency("minute", "a", 5, 130) + " (Expected: [2, 1, 2])");

        // Test 2.2: tweet name never recorded — still one 0 per chunk, not an empty list
        System.out.println("Test 2.2: " + T.getTweetCountsPerFrequency("minute", "c", 0, 119) + " (Expected: [0, 0])");

        // Test 2.3: startTime == endTime — a single 1-second chunk; 1 + 0 / size = 1
        System.out.println("Test 2.3: " + T.getTweetCountsPerFrequency("hour", "a", 65, 65) + " (Expected: [1])");

        // Test 2.4: "day" frequency — the chunk is longer than the whole period, so there is exactly one
        System.out.println("Test 2.4: " + T.getTweetCountsPerFrequency("day", "b", 0, 10000) + " (Expected: [1])");

        // Test 2.5: chunks with no tweets — the empty middle chunk and the 1-second last chunk [250,250]
        // both keep their 0; every tweet before startTime is skipped
        System.out.println("Test 2.5: " + T.getTweetCountsPerFrequency("minute", "a", 130, 250) + " (Expected: [0, 1, 0])");
    }
}
