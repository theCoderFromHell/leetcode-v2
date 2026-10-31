package medium;

import java.util.*;

// https://leetcode.com/problems/design-a-file-sharing-system/
public class DesignAFileSharingSystem {

    static class FileSharing {
        HashMap<Integer, TreeSet<Integer>> chunks;
        HashMap<Integer, HashSet<Integer>> users;
        PriorityQueue<Integer> freedIds;
        int nextUserId;
        public FileSharing(int m) {
            this.chunks = new HashMap<>();
            this.users = new HashMap<>();
            this.freedIds = new PriorityQueue<>();
            this.nextUserId = 1;
        }

        public int join(List<Integer> ownedChunks) {
            int userId = !freedIds.isEmpty() ? freedIds.poll() :  nextUserId++;
            users.put(userId, new HashSet<>(ownedChunks));
            int size = ownedChunks.size();
            for (int i = 0; i < size; i++)
                chunks.computeIfAbsent(ownedChunks.get(i), k -> new TreeSet<>()).add(userId);
            return userId;
        }

        public void leave(int userID) {
            HashSet<Integer> userChunks = users.get(userID);
            for (int chunk : userChunks) {
                TreeSet<Integer> userIds = chunks.get(chunk);
                userIds.remove(userID);
                if (userIds.isEmpty())
                    chunks.remove(chunk);
            }
            users.remove(userID);
            freedIds.add(userID);
        }

        public List<Integer> request(int userID, int chunkID) {
            TreeSet<Integer> userIds = chunks.get(chunkID);
            if (userIds == null)
                return new ArrayList<>();
            users.get(userID).add(chunkID);
            List<Integer> result = new ArrayList<>(userIds);
            userIds.add(userID);
            return result;
        }
    }

    /*
     * Revision Note — Design a File Sharing System (Medium, premium)
     * Pattern: two maps kept in sync (user -> chunks it owns, chunk -> sorted owners), plus a
     *          min-heap of freed ids alongside a counter for the next never-used id.
     * Key Insight: the smallest free id is either the smallest FREED id or the next id never
     *              handed out, because every id below the counter that isn't freed is taken. So
     *              join is heap-or-counter, O(log n), instead of scanning up from 1, which is
     *              O(active users) per join.
     * Gotchas:
     *   - leave must remove the user from every chunk they own, including chunks gained through
     *     request, or a reused id inherits the old owner's chunks (Test 2.7). The user -> chunks
     *     map makes this O(chunks owned) instead of a sweep over every chunk.
     *   - Copy ownedChunks (new HashSet<>(...)): the caller's list can be immutable (List.of), and
     *     request adds to it later.
     *   - request returns the owners BEFORE adding the requester. A requester who already owns
     *     the chunk is in the list (Test 2.11). A chunk with no owners gives [], and the requester
     *     gains nothing (Tests 2.13-2.14).
     *   - Drop a chunk's owner set when it empties, so containsKey means "someone owns it".
     *   - The heap never goes stale: an id is pushed once, when its user leaves, and polled once,
     *     when it's reused.
     * Complexity: join O(log n + c log u) for c chunks; leave O(k log u) for the k chunks the user
     *             owns; request O(u) to copy the u sorted owners (the output itself) + O(log u)
     *             to add the requester. Space O(ownership pairs + freed ids).
     * Follow-ups:
     *   (1) Users identified by IP who reconnect: map ip -> id, and keep the id and its chunks on
     *       disconnect instead of freeing them.
     *   (2) Frequent join/leave without requests: both cost only the user's own chunks, so this
     *       is fine. A lazy version (a new session object per join, stale owners filtered out on
     *       request with 3408's identity check) makes leave O(log n).
     *   (3) Everyone requests every chunk: every owner set grows to all users, so request is O(n)
     *       output and leave O(m log n) per user. That's inherent in returning the lists.
     *   (4) n files: key the chunk map by (fileId, chunkId).
     * Template:
     *   join(own):      id = freed.isEmpty() ? next++ : freed.poll(); user[id] = copy(own); for c: owners[c].add(id)
     *   leave(id):      for c in user[id]: owners[c].remove(id), drop if empty; user.remove(id); freed.add(id)
     *   request(id, c): if owners[c] missing: return []
     *                   res = list(owners[c]); owners[c].add(id); user[id].add(c); return res
     */
    public static void main(String[] args) {
        FileSharing F = new FileSharing(4);

        // Test 1: LeetCode example 1, replayed call by call on this instance
        System.out.println("Test 1.1: " + F.join(List.of(1, 2)) + " (Expected: 1)");
        System.out.println("Test 1.2: " + F.join(List.of(2, 3)) + " (Expected: 2)");
        System.out.println("Test 1.3: " + F.join(List.of(4)) + " (Expected: 3)");
        System.out.println("Test 1.4: " + F.request(1, 3) + " (Expected: [2])");
        System.out.println("Test 1.5: " + F.request(2, 2) + " (Expected: [1, 2])");
        F.leave(1);
        System.out.println("Test 1.6: " + F.request(2, 1) + " (Expected: [])");
        F.leave(2);
        System.out.println("Test 1.7: " + F.join(List.of()) + " (Expected: 1)");

        // Test 2: a fresh instance with m = 5
        F = new FileSharing(5);
        System.out.println("Test 2.1: " + F.join(List.of(1)) + " (Expected: 1)");
        System.out.println("Test 2.2: " + F.join(List.of(2)) + " (Expected: 2)");
        System.out.println("Test 2.3: " + F.join(List.of(3)) + " (Expected: 3)");

        // Test 2.4-2.6: two users leave, so more than one id is free — join takes the SMALLEST freed id
        // (1, then 3) and only then the next never-used id (4)
        F.leave(3);
        F.leave(1);
        System.out.println("Test 2.4: " + F.join(List.of()) + " (Expected: 1)");
        System.out.println("Test 2.5: " + F.join(List.of(5)) + " (Expected: 3)");
        System.out.println("Test 2.6: " + F.join(List.of()) + " (Expected: 4)");

        // Test 2.7: a chunk owned only by a user who left — id 1 now belongs to a new user with no chunks,
        // so the old owner's chunk 1 must not carry over to it
        System.out.println("Test 2.7: " + F.request(1, 1) + " (Expected: [])");

        // Test 2.8-2.10: one chunk requested by several users in turn — each successful request makes the
        // requester an owner, and the list stays sorted even though 1 became an owner after 3 and 4
        System.out.println("Test 2.8: " + F.request(4, 5) + " (Expected: [3])");
        System.out.println("Test 2.9: " + F.request(1, 5) + " (Expected: [3, 4])");
        System.out.println("Test 2.10: " + F.request(2, 5) + " (Expected: [1, 3, 4])");

        // Test 2.11: a user requests a chunk they already own — they appear in the list themselves
        System.out.println("Test 2.11: " + F.request(4, 5) + " (Expected: [1, 2, 3, 4])");

        // Test 2.12: an owner leaves — 3 drops out of chunk 5's owners
        F.leave(3);
        System.out.println("Test 2.12: " + F.request(2, 5) + " (Expected: [1, 2, 4])");

        // Test 2.13-2.14: a chunk nobody owns, requested twice — an empty answer gives the requester nothing,
        // so the second request is still []
        System.out.println("Test 2.13: " + F.request(2, 4) + " (Expected: [])");
        System.out.println("Test 2.14: " + F.request(1, 4) + " (Expected: [])");
    }
}
