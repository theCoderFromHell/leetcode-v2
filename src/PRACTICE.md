# Practice Tracker

Similar-problem suggestions from `/practice`. Medium and Hard only — status refreshes on each run.

**Pending 80 · Solved 68 · Total 148** — updated 2026-10-31

Status key: `☐` pending · `☑ YYYY-MM-DD` solved · `☐ ?` unverified

---

## 355. Design Twitter
*Design · Hash Table · Heap · per-key collections kept in sync across several maps, and a top-k read across them: per-user tweet lists ordered by a global timestamp, a follow map of sets, and getNewsFeed's top 10 from a heap*

| # | Problem | Diff | Status | Why similar |
|---|---------|------|--------|-------------|
| 1348 | [Tweet Counts Per Frequency](https://leetcode.com/problems/tweet-counts-per-frequency/) | Medium | ☑ 2026-10-31 | The same per-key event log as 355's userId → tweets, but the query is a time RANGE cut into fixed chunks: sorted timestamps (TreeMap, or a sorted list + binary search) replace the heap. The last chunk can be shorter. The easiest of the set |
| 2349 | [Design a Number Container System](https://leetcode.com/problems/design-a-number-container-system/) | Medium | ☑ 2026-10-31 | Two maps that must stay in sync, like follow/unfollow: index → number and number → ordered indices. A replace has to unhook the old pairing, or use a min-heap per number and discard stale tops lazily by checking the index map |
| 3408 | [Design Task Manager](https://leetcode.com/problems/design-task-manager/) | Medium | ☑ 2026-10-31 | 355's heap, but entries change after they're pushed (edit, rmv): one global max-heap with lazy deletion, where each popped entry is checked against the taskId map before it counts. Ties go to the larger taskId |
| 1500 | [Design a File Sharing System](https://leetcode.com/problems/design-a-file-sharing-system/) | Medium | ☑ 2026-10-31 | LeetCode's own sibling of 355: users join and leave like follow/unfollow, a chunk → owners map of sets answers requests, and a min-heap hands out the smallest freed user id. Premium |
| 1912 | [Design Movie Rental System](https://leetcode.com/problems/design-movie-rental-system/) | Hard | ☐ | getNewsFeed's top 10 becomes top 5 from ordered sets: search reads one movie's set of unrented (price, shop), report reads one global set of rented copies, and rent/drop MOVE an entry between the two. Multi-key tie-breaks |

*Filtered as already solved: 23 (Merge k Sorted Lists), 373 (Find K Pairs with Smallest Sums), 378 (Kth Smallest Element in a Sorted Matrix) and 632 (Smallest Range Covering Elements from K Lists), which cover the k-way merge behind getNewsFeed; 2034 (Stock Price Fluctuation, as `StockPrice`), 2353 (Design a Food Rating System, as `FoodRatings`), 1244 (Design A Leaderboard), 362 (Design Hit Counter, as `HitCounter`). 359 (Logger Rate Limiter) is Easy.*
*Source note: 355 is in the repo as `Twitter` (named after the API class). Rows checked against the repo and the last 20 LeetCode ACs only. 3408, 2349 and 1912 are contest-era, where an uncommitted solve is least likely; 1500 is premium.*

---

## 901. Online Stock Span
*Design · Data Stream · Monotonic Stack · a decreasing stack of (price, span) pairs: each new price pops every entry ≤ it and ABSORBS their spans, so a popped day is never looked at again — amortised O(1) "distance to the previous greater" per call*

| # | Problem | Diff | Status | Why similar |
|---|---------|------|--------|-------------|
| 2832 | [Maximal Range That Each Element Is Maximum in It](https://leetcode.com/problems/maximal-range-that-each-element-is-maximum-in-it/) | Medium | ☐ | 901's span run in BOTH directions: previous greater (exactly 901) plus next greater, and the range is right − left − 1. Offline, so one pass can settle the right edge at pop time. The easiest of the set; isolates the pattern |
| 1950 | [Maximum of Minimum Values in All Subarrays](https://leetcode.com/problems/maximum-of-minimum-values-in-all-subarrays/) | Medium | ☐ | The span flipped to "how far is nums[i] still the MINIMUM" on both sides. That span length L becomes an INDEX into the answer (nums[i] is a candidate for every window size ≤ L), finished by a backward max sweep |
| 2334 | [Subarray With Elements Greater Than Varying Threshold](https://leetcode.com/problems/subarray-with-elements-greater-than-varying-threshold/) | Hard | ☐ | Same both-sided min span: the widest window where nums[i] is the minimum has length L, and it is valid iff nums[i] > threshold / L. Span plus one check; union-find over values in descending order is the alternative |
| 3113 | [Find the Number of Subarrays Where Boundary Elements Are Maximum](https://leetcode.com/problems/find-the-number-of-subarrays-where-boundary-elements-are-maximum/) | Hard | ☐ | 901's (value, count) pair again, but EQUAL values are counted rather than popped: pop the smaller ones, and if the top equals the new value its count grows and feeds the answer. 901's "absorb what you pop" bookkeeping, where ties are the whole problem |
| 2281 | [Sum of Total Strength of Wizards](https://leetcode.com/problems/sum-of-total-strength-of-wizards/) | Hard | ☐ | Both-sided min span gives each wizard's contribution range, but summing every subarray SUM over that range needs prefix sums of prefix sums, under mod 10⁹+7. 907's contribution technique at full difficulty |

*Filtered as already solved: 739 (Daily Temperatures — the next-greater mirror, own section), 1019 (Next Greater Node In Linked List), 503 (Next Greater Element II), 907 (Sum of Subarray Minimums), 2104 (Sum of Subarray Ranges), 1856 (Maximum Subarray Min-Product), 84 (Largest Rectangle in Histogram), 85 (Maximal Rectangle), 962 (Maximum Width Ramp), 456 (132 Pattern), 1762 (Buildings With an Ocean View), 1793 (Maximum Score of a Good Subarray), 2866 (Beautiful Towers II). Already pending elsewhere, so not repeated: 1063 (Number of Valid Subarrays) and 2454 (Next Greater Element IV) under 739, 1944 (Number of Visible People in a Queue) under 42, 2289 (Steps to Make Array Non-decreasing) under 735.*
*Source note: 901 is in the repo as `StockSpanner` (named after the API class). Rows checked against the repo and the last 20 LeetCode ACs only; all five are contest-era, where an uncommitted solve is least likely. 2832 and 1950 need Premium.*

---

## 1325. Delete Leaves With a Given Value
*Tree · DFS · postorder deletion: settle the children first, then decide the node, and return null (or 0) to cut it off. Deletions cascade upward, because a parent can only be judged once its children's fate is known. On a general tree, the same cut happens bottom-up in a DFS, or by peeling leaves with a degree queue*

| # | Problem | Diff | Status | Why similar |
|---|---------|------|--------|-------------|
| 1080 | [Insufficient Nodes in Root to Leaf Paths](https://leetcode.com/problems/insufficient-nodes-in-root-to-leaf-paths/) | Medium | ☐ | Same postorder "return null to delete" with a cascade, but the test is a root-to-leaf path sum passed down instead of a node value. A node that loses all its children is deleted rather than judged as a new leaf: every path through it was already too small |
| 2872 | [Maximum Number of K-Divisible Components](https://leetcode.com/problems/maximum-number-of-k-divisible-components/) | Hard | ☐ | The bottom-up cut on a general tree: the DFS returns a subtree's leftover sum, and a subtree whose sum is divisible by k is cut off (returns 0) and counted. Sums reach 3e13, so long |
| 2440 | [Create Components With Same Value](https://leetcode.com/problems/create-components-with-same-value/) | Hard | ☐ | 2872 with the target unknown: try each divisor of the total as the component value and run the same cut-when-the-sum-hits-target DFS. A subtree that overshoots the target makes that divisor fail |
| 2603 | [Collect Coins in a Tree](https://leetcode.com/problems/collect-coins-in-a-tree/) | Hard | ☐ | 1325's cascade done iteratively from the leaves of an unrooted tree: peel coinless leaves with a degree-1 queue until none are left (each removal can expose a new leaf), then peel two more layers for the distance-2 reach |

*Filtered as already solved: 814 (Binary Tree Pruning), 1110 (Delete Nodes And Return Forest), 366 (Find Leaves of Binary Tree), 1660 (Correct a Binary Tree), 669 (Trim a BST), 2049 (Count Nodes With the Highest Score), 1443 (Minimum Time to Collect All Apples), 310 (Minimum Height Trees), 979 (Distribute Coins). Already in the tracker under 199: 2458 (Height After Subtree Removal Queries). Left out as looser: 2925 (Maximum Score After Applying Operations — keep/take tree DP).*
*Source note: 1325 is in the repo as `DeleteLeavesWithAGivenValue`. Rows checked against the repo and the last 20 LeetCode ACs only. All four are contest-era except 1080.*

---

## 230. Kth Smallest Element in a BST
*Tree · BST · Order statistics · an inorder walk visits a BST in sorted order, so the kth visit is the kth smallest and you can stop early. 230's follow-up asks about a set that keeps changing: then you keep counts (subtree sizes, a Fenwick tree over values, or a pair of heaps/ordered sets split at k) so each rank query costs O(log n) instead of a fresh walk*

| # | Problem | Diff | Status | Why similar |
|---|---------|------|--------|-------------|
| 2476 | [Closest Nodes Queries in a Binary Search Tree](https://leetcode.com/problems/closest-nodes-queries-in-a-binary-search-tree/) | Medium | ☑ 2026-10-29 | Same "inorder gives a sorted array" step, then many queries: flatten the tree once and answer each floor/ceiling by binary search, rather than walking the tree per query |
| 2102 | [Sequentially Ordinal Rank Tracker](https://leetcode.com/problems/sequentially-ordinal-rank-tracker/) | Hard | ☐ | 230's follow-up made real: the ith best of a set that keeps growing, where i goes up by one per query. Ties break on name, so the ordering needs a two-key comparator |
| 1649 | [Create Sorted Array through Instructions](https://leetcode.com/problems/create-sorted-array-through-instructions/) | Hard | ☐ | Rank queries (how many smaller, how many greater) on every insert: the counts 230's follow-up would keep in each node, held in a Fenwick tree over values instead. Duplicates count as neither; the total needs mod 1e9+7 |
| 1825 | [Finding MK Average](https://leetcode.com/problems/finding-mk-average/) | Hard | ☐ | Kth-smallest and kth-largest boundaries over a sliding window with both inserts and deletes: three ordered multisets (bottom k, middle, top k) with a running sum of the middle, rebalanced on every add |

*Filtered as already solved: 173 / 1586 (BST Iterator I/II), 285 / 510 (Inorder Successor I/II), 99 (Recover BST), 1305 (All Elements in Two BSTs), 538 / 1038 (BST to Greater Tree), 1214 (Two Sum BSTs), 1382 (Balance a BST), 1756 (Design MRU Queue — kth with deletions), 315 (Count of Smaller Numbers After Self). Already in the tracker under 658: 272 (Closest BST Value II).*
*Source note: 230 is not in the repo (solved on LeetCode, not committed). Rows checked against the repo and the last 20 LeetCode ACs only. 2476, 2102 and 1825 are contest-era, where an uncommitted solve is least likely.*

---

## 621. Task Scheduler
*Greedy · Heap · Counting · the most frequent task sets the frame — (maxFreq − 1) blocks of n + 1 slots, plus one slot per task tied at maxFreq — and everything else fills the idle gaps, so the answer is max(tasks.length, frame). As a simulation: a max-heap of remaining counts plus a cooldown queue that hands each task back n steps later*

| # | Problem | Diff | Status | Why similar |
|---|---------|------|--------|-------------|
| 2365 | [Task Scheduler II](https://leetcode.com/problems/task-scheduler-ii/) | Medium | ☑ 2026-10-29 | 621 with the ORDER fixed: there's no choice of what to run next, so the greedy disappears and only the cooldown bookkeeping is left — remember each type's next allowed day and jump straight to it instead of idling day by day (days reach ~10¹⁰, so long) |
| 1953 | [Maximum Number of Weeks for Which You Can Work](https://leetcode.com/problems/maximum-number-of-weeks-for-which-you-can-work/) | Medium | ☑ 2026-10-30 | 621's frame argument with a cooldown of 1, except you stop instead of idling: either the biggest project fits between the others, or the others can only separate a limited number of its milestones. Totals reach 10¹⁴, so long |
| 2182 | [Construct String With Repeat Limit](https://leetcode.com/problems/construct-string-with-repeat-limit/) | Medium | ☐ | The heap-with-a-hold-back loop from 621's simulation, but ordered by LETTER instead of by count: run the largest letter up to the limit, then slip in one of the next largest to break the run |
| 358 | [Rearrange String k Distance Apart](https://leetcode.com/problems/rearrange-string-k-distance-apart/) | Hard | ☐ | 621 where you must output the arrangement and can't idle: a max-heap of counts plus a waiting queue that hands each letter back k steps after its use; running dry while letters still wait means it's impossible. Premium |
| 2141 | [Maximum Running Time of N Computers](https://leetcode.com/problems/maximum-running-time-of-n-computers/) | Hard | ☐ | The same "does the largest item dominate, or does everything fit?" split, on a time budget: a battery longer than the target can only ever power one computer, so it can't lend its excess to the others |

*Filtered as already solved: 767 (Reorganize String — the cooldown-1 construction), 1405 (Longest Happy String), 1054 (Distant Barcodes), 1834 (Single-Threaded CPU), 2402 (Meeting Rooms III). Subsumed by 1405, so left out: 984 (String Without AAA or BBB). Easy, so left out: 2335 (Minimum Amount of Time to Fill Cups).*
*Source note: 621 is in the repo as `TaskScheduler`. Rows checked against the repo and the last 20 LeetCode ACs only. Four of the five are contest-era (2365, 1953, 2182, 2141), where an uncommitted solve is least likely; 358 is older but premium.*

---

## 739. Daily Temperatures
*Array · Stack · Monotonic Stack · indices wait on a decreasing stack until a larger value arrives; that arrival resolves every waiting index it beats (pop → answer = i − j), so each index is pushed and popped once — O(n) "next greater" for every element*

| # | Problem | Diff | Status | Why similar |
|---|---------|------|--------|-------------|
| 1130 | [Minimum Cost Tree From Leaf Values](https://leetcode.com/problems/minimum-cost-tree-from-leaf-values/) | Medium | ☑ 2026-10-29 | Same pop-when-something-bigger-arrives loop, but each pop is a greedy MERGE decision: a small leaf pairs with the smaller of its two bigger neighbours (the arrival, or the new top) and pays their product. n <= 40 also admits an O(n³) interval DP — the stack is the O(n) insight |
| 2297 | [Jump Game VIII](https://leetcode.com/problems/jump-game-viii/) | Medium | ☐ | 739's "next greater" computed twice — next >= and next < — and used as a graph: those two indices are each position's ONLY legal jumps, so two stack passes build the edges and a DP over them gives the minimum cost |
| 1063 | [Number of Valid Subarrays](https://leetcode.com/problems/number-of-valid-subarrays/) | Hard | ☐ | 739 flipped to next SMALLER and summed: a subarray starting at i stays valid until something smaller than nums[i] appears, so every start contributes a distance. Duplicates decide strict vs non-strict pops |
| 2454 | [Next Greater Element IV](https://leetcode.com/problems/next-greater-element-iv/) | Hard | ☐ | The SECOND greater: an index popped from the first stack has found its first greater but isn't done — it moves to a second stack and waits for one more larger value. Moving popped indices across without scrambling their order is the whole problem |
| 975 | [Odd Even Jump](https://leetcode.com/problems/odd-even-jump/) | Hard | ☐ | "Next" by VALUE instead of position: sort indices by value and run 739's stack over that order to get each index's smallest->= (and largest-<=) jump target, then a backward DP over odd/even reachability |

*Filtered as already solved: 1019 (Next Greater Node In Linked List — 739 on a list), 503 (Next Greater Element II — circular, two passes), 901 (Online Stock Span — `StockSpanner`, the previous-greater mirror), 1856 (Maximum Subarray Min-Product), 907 (Sum of Subarray Minimums), 2104 (Sum of Subarray Ranges), 962 (Maximum Width Ramp), 456 (132 Pattern), 84 (Largest Rectangle in Histogram), 85 (Maximal Rectangle), 1762 (Buildings With an Ocean View), 1793 (Maximum Score of a Good Subarray), 2866 (Beautiful Towers II). Already pending elsewhere, so not repeated: 1944 (Number of Visible People in a Queue — under 42), 2289 (Steps to Make Array Non-decreasing) and 1776 (Car Fleet II) — both under 735.*
*Source note: 739 itself has no file in the repo — a NeetCode / Top-150 staple, almost certainly solved on LeetCode but never committed. Rows checked against the repo and the last 20 LeetCode ACs only; 975 is the best-known of the five, and 2297 / 1063 need Premium. Fair sixth pick: 3113 (Find the Number of Subarrays Where Boundary Elements Are Maximum, Hard — a monotonic stack where EQUAL values are counted, not popped).*

---

## 735. Asteroid Collision
*Array · Stack · Simulation · the survivors so far sit on a stack; each newcomer fights only the top — pop while it wins, stop when it loses, both vanish on a tie — and only a right-mover on the stack meeting an incoming left-mover can collide*

| # | Problem | Diff | Status | Why similar |
|---|---------|------|--------|-------------|
| 2211 | [Count Collisions on a Road](https://leetcode.com/problems/count-collisions-on-a-road/) | Medium | ☑ 2026-10-24 | Same R-meets-L setup, but crashed cars STOP instead of exploding (adding a third state, S), and you count collisions instead of returning survivors. The stack works, then collapses into an observation about which cars can never be hit |
| 2289 | [Steps to Make Array Non-decreasing](https://leetcode.com/problems/steps-to-make-array-non-decreasing/) | Medium | ☐ | Bigger-eats-the-smaller-to-its-right, but every elimination in a round happens at once and the answer is the number of ROUNDS — so a stack entry has to carry WHEN it dies, not just whether. The hardest invariant in the set despite the Medium label |
| 2197 | [Replace Non-Coprime Numbers in Array](https://leetcode.com/problems/replace-non-coprime-numbers-in-array/) | Hard | ☐ | 735's "keep fighting the top until nothing changes" loop, but a collision MERGES the pair into their LCM instead of destroying one — and the merged value can then hit the new top. The stated order-independence is what makes one left-to-right pass valid |
| 2751 | [Robot Collisions](https://leetcode.com/problems/robot-collisions/) | Hard | ☐ | 735 with health: positions arrive unsorted (process them in position order), the winner loses 1 health per fight and keeps fighting, and survivors come back in ORIGINAL input order. LeetCode lists 735 as its only similar question |
| 1776 | [Car Fleet II](https://leetcode.com/problems/car-fleet-ii/) | Hard | ☐ | Collisions with TIME: cars merge into the slower one and you return each car's collision time with the next. Scan right-to-left; the stack drops any car you can't catch or that merges away before you would reach it — the monotonic stack meets kinematics |

*Filtered as already solved: 1209 (Remove All Adjacent Duplicates in String II — `RemoveAllAdjacentDuplicatesInStringII`, the cancel-against-the-top sibling with counts), 853 (Car Fleet — `CarFleet`, the sort-then-stack version 1776 builds on), 2390 (Removing Stars From a String). Also skipped: 2126 (Destroying Asteroids — LeetCode lists it, but it is sort + greedy with no stack), 1503 (Last Moment Before All Ants Fall Out of a Plank — the "collisions are pass-throughs" trick, a different technique).*
*Source note: `AsteroidCollision` is in the repo. Rows checked against the repo and the last 20 LeetCode ACs only; all five are contest-era, where an uncommitted solve is least likely.*

---

## 98. Validate Binary Search Tree
*Tree · BST · value-range propagation — pass an open (low, high) window down (or return min/max/isBST up), so each node is checked against ALL its ancestors, not just its parent*
*Filtered as already solved: 333 (Largest BST Subtree — the Medium form of 1373), 99 (Recover BST), 1008 (BST from Preorder), 449 (Serialize and Deserialize BST), 285 / 510 (Inorder Successor I/II), 450 (Delete Node), 669 (Trim a BST), 538 / 1038 (BST to Greater Tree), 1214 (Two Sum BSTs), 1382 (Balance a BST), 95 (Unique BSTs II). Left out as Top-150 staples likely solved but not committed: 230 (Kth Smallest in a BST), 173 (BST Iterator). 98 itself is not in the repo either. Checked against the repo and the last 20 LeetCode ACs.*

| # | Problem | Diff | Status | Why similar |
|---|---------|------|--------|-------------|
| 255 | [Verify Preorder Sequence in Binary Search Tree](https://leetcode.com/problems/verify-preorder-sequence-in-binary-search-tree/) | Medium | ☑ 2026-10-23 | Same lower-bound check, but on an array with no tree: going right raises the floor every later value must beat. Monotonic stack, or O(1) space by overwriting the input |
| 1902 | [Depth of BST Given Insertion Order](https://leetcode.com/problems/depth-of-bst-given-insertion-order/) | Medium | ☑ 2026-10-24 | The (low, high) window turned around: each new value lands in the gap between its nearest smaller and larger inserted values, so a TreeMap of gaps gives its depth without building the tree (1e5 values, a skewed build is O(n^2)) |
| 1373 | [Maximum Sum BST in Binary Tree](https://leetcode.com/problems/maximum-sum-bst-in-binary-tree/) | Hard | ☐ | 98's bottom-up form: return (isBST, min, max, sum) from each subtree and check left.max < node < right.min. Every subtree is a candidate, and an empty BST (sum 0) beats all-negative ones |
| 1932 | [Merge BSTs to Create Single BST](https://leetcode.com/problems/merge-bsts-to-create-single-bst/) | Hard | ☐ | Graft small trees onto matching leaves, then run 98 on the result with (low, high) bounds. The hard part is the root choice (the only root that is no tree's leaf) and checking every tree got used |

---

## 199. Binary Tree Right Side View
*Tree · BFS / DFS · per-depth bookkeeping — group nodes by depth and keep one fact per level: the last node of each BFS level, or (DFS right child first) the first node to reach a new depth (depth == result.size())*

| # | Problem | Diff | Status | Why similar |
|---|---------|------|--------|-------------|
| 662 | [Maximum Width of Binary Tree](https://leetcode.com/problems/maximum-width-of-binary-tree/) | Medium | ☑ 2026-10-23 | Same per-level scan, but you track BOTH ends of each level, as heap positions (2i, 2i+1) so the missing nodes in between count. The index outgrows int on a deep thin tree, so renormalise per level |
| 987 | [Vertical Order Traversal of a Binary Tree](https://leetcode.com/problems/vertical-order-traversal-of-a-binary-tree/) | Hard | ☑ 2026-10-23 | The right side view keeps one node per row; this groups every node by (column, row) instead, with a value tie-break when two nodes share a position. The step past 314, which you solved, where ties go by left-to-right order |
| 2458 | [Height of Binary Tree After Subtree Removal Queries](https://leetcode.com/problems/height-of-binary-tree-after-subtree-removal-queries/) | Hard | ☐ | Per-depth bookkeeping as a precompute: removing a node only touches its own level, so for each depth keep the best and second-best (depth + subtree height) and answer each of 10⁴ queries in O(1) |

*Filtered as already solved: 513 (Find Bottom Left Tree Value — the mirror image), 515 (Largest Value in Each Row), 1161 (Maximum Level Sum), 1302 (Deepest Leaves Sum), 2583 (Kth Largest Level Sum), 545 (Boundary of Binary Tree), 314 (Vertical Order Traversal), 116 / 117 (Populating Next Right Pointers I/II), 1602 (Find Nearest Right Node), 1660 (Correct a Binary Tree), 958 (Check Completeness), 919 (Complete Binary Tree Inserter), 2641 (Cousins II), 2415 (Reverse Odd Levels), 1609 (Even Odd Tree), 2471 (Sort a Tree by Level), 103 (Zigzag), 623 (Add One Row), 2385 (Time to Infect). 3157 (Level with Minimum Sum) left out as a near-copy of 1161. Only 3 survive: level-order is the most picked-over concept in the repo. Checked against the repo and the last 20 LeetCode ACs.*

---

## 215. Kth Largest Element in an Array
*Array · Quickselect · Heap · select the k-th order statistic without a full sort — quickselect partition (O(n) average), a size-k min-heap, counting over a bounded value range, or binary search on the value with a count*

| # | Problem | Diff | Status | Why similar |
|---|---------|------|--------|-------------|
| 2343 | [Query Kth Smallest Trimmed Number](https://leetcode.com/problems/query-kth-smallest-trimmed-number/) | Medium | ☐ | Selection under a custom key with a tie rule (equal trimmed value → lower index), repeated per query. Quickselect per query works; the follow-up swaps it for radix sort, where each trim length is one more LSD pass |
| 2448 | [Minimum Cost to Make Array Equal](https://leetcode.com/problems/minimum-cost-to-make-array-equal/) | Hard | ☑ 2026-10-28 | Weighted median: the k-th element where k is counted in total weight, not in elements. Same "select the middle, then sum the distances" as 462 and 2033, but with per-element costs and long arithmetic (answers up to 2⁵³) |
| 668 | [Kth Smallest Number in Multiplication Table](https://leetcode.com/problems/kth-smallest-number-in-multiplication-table/) | Hard | ☑ 2026-10-29 | The k-th of an implicit m·n set (up to 9·10⁸ entries) that can't be built or partitioned, so you binary-search the VALUE and count entries ≤ it. Same counting idea as 719 (pending under 167), but the count has a closed form per row instead of two pointers |
| 2386 | [Find the K-Sum of an Array](https://leetcode.com/problems/find-the-k-sum-of-an-array/) | Hard | ☐ | k-th largest of 2ⁿ subsequence sums: the set is far too big for any selection, so a max-heap generates the sums in decreasing order and you pop k times, where 215's heap only holds k |

*Filtered as already solved: 2033 (Minimum Operations to Make a Uni-Value Grid — solved on LeetCode, not committed; backfilled as `MinimumOperationsToMakeAUniValueGrid` on 2026-10-23), 378 (Kth Smallest Element in a Sorted Matrix), 786 (K-th Smallest Prime Fraction), 373 (Find K Pairs with Smallest Sums), 973 (K Closest Points to Origin), 324 (Wiggle Sort II), 462 (Minimum Moves to Equal Array Elements II), 692 (Top K Frequent Words), 1985 (Find the Kth Largest Integer in the Array). Already pending elsewhere, so not repeated: 719 (Find K-th Smallest Pair Distance, under 167), 1738 (Find Kth Largest XOR Coordinate Value, under 347), 3321 (Find X-Sum of All K-Long Subarrays II, under 973). Easy, so left out: 703 (Kth Largest Element in a Stream), 414 (Third Maximum Number).*
*Source note: 215 is in the repo as `KthLargestElementInAnArray`. Rows checked against the repo and the last 20 LeetCode ACs only. Three of the four are contest-era (2343, 2386, 2448), where an uncommitted solve is least likely; 668 is older but not a Top-150 staple.*

---

## 235. Lowest Common Ancestor of a Binary Search Tree
*Tree · BST · the LCA is the split point — the first node on the root path where p and q stop going the same way (BST order tells you which way without searching)*

| # | Problem | Diff | Status | Why similar |
|---|---------|------|--------|-------------|
| 776 | [Split BST](https://leetcode.com/problems/split-bst/) | Medium | ☑ 2026-10-21 | Same one-path walk steered by BST order (go left or right of the target), but you rebuild two trees along that path instead of stopping at a split |
| 2096 | [Step-By-Step Directions From a Binary Tree Node to Another](https://leetcode.com/problems/step-by-step-directions-from-a-binary-tree-node-to-another/) | Medium | ☑ 2026-10-21 | The split point as a tool: no BST order, so record root→s and root→t paths; their common prefix ends at the LCA, then emit U's up and L/R down |
| 1740 | [Find Distance in a Binary Tree](https://leetcode.com/problems/find-distance-in-a-binary-tree/) | Medium | ☑ 2026-10-21 | LCA plus depths: dist = depth(p) + depth(q) − 2·depth(LCA); the count-only version of 2096 |
| 1257 | [Smallest Common Region](https://leetcode.com/problems/smallest-common-region/) | Medium | ☑ 2026-10-22 | LCA on a general n-ary tree given only as parent lists: build a parent map, mark one node's ancestors, walk the other up until it hits a marked one |
| 2509 | [Cycle Length Queries in a Tree](https://leetcode.com/problems/cycle-length-queries-in-a-tree/) | Hard | ☐ | Heap-numbered tree: the label itself encodes the path (parent = v/2), so walk the larger label up until they meet, the same "move the deeper/bigger side" logic as 235, 10⁵ queries |

*Filtered as already solved: 236 (LCA of a Binary Tree), 1644 (LCA II — nodes may be missing), 1650 (LCA III — parent pointers), 1676 (LCA IV — many nodes), 1123 / 865 (LCA of Deepest Leaves), 285 / 510 (Inorder Successor in BST I/II), 669 (Trim a BST). 1483 (Kth Ancestor, binary lifting) left out as a different technique. Checked against the repo and the last 20 LeetCode ACs only; 235 itself has no file here, so this area may hold more uncommitted solves.*

---

## 973. K Closest Points to Origin
*Array · Heap · Quickselect · Sorting · top-k by a derived key — compare squared distances (no sqrt), then either a size-k max-heap whose root is the current k-th (a closer point evicts it), or a quickselect partition around the k-th distance for O(n) average*

| # | Problem | Diff | Status | Why similar |
|---|---------|------|--------|-------------|
| 1471 | [The k Strongest Values in an Array](https://leetcode.com/problems/the-k-strongest-values-in-an-array/) | Medium | ☑ 2026-10-20 | Same "k farthest from a centre", but the centre is the median, so you have to find it first, and ties go to the larger value. Once sorted, the strongest values sit at the two ends, so two pointers moving inward replace the heap |
| 3275 | [K-th Nearest Obstacle Queries](https://leetcode.com/problems/k-th-nearest-obstacle-queries/) | Medium | ☑ 2026-10-21 | 973 made online: points arrive one at a time and you report the k-th distance after each one. Sorting and quickselect are ruled out, so it HAS to be the size-k max-heap, and its root is the answer |
| 2512 | [Reward Top K Students](https://leetcode.com/problems/reward-top-k-students/) | Medium | ☑ 2026-10-21 | Compute a score per item, then take the top k, with a tie rule (equal points → lower id first). With a size-k heap the eviction comparator must be the exact reverse of the ranking, tie included, which is where it goes wrong |
| 3321 | [Find X-Sum of All K-Long Subarrays II](https://leetcode.com/problems/find-x-sum-of-all-k-long-subarrays-ii/) | Hard | ☐ | Top-x by (frequency, value) over a sliding window, so elements leave as well as arrive. A heap can't delete, so the top-x set and the rest become two TreeSets with a running sum, rebalanced on every slide: the step past 3275's insert-only stream |

*Filtered as already solved: 215 (Kth Largest Element in an Array — the quickselect drill), 692 (Top K Frequent Words — top-k with a tie-break comparator), 1985 (Find the Kth Largest Integer in the Array — selection under a custom string comparator), 324 (Wiggle Sort II — quickselect median). Already pending under 347, so not repeated here: 1738 (Find Kth Largest XOR Coordinate Value), 2542 (Maximum Subsequence Score), 857 (Minimum Cost to Hire K Workers).*
*Source note: 973 has no file in the repo; it was solved on LeetCode but not committed (see 75). Rows checked against the repo and the recent-AC list only. All four are contest-era, where an uncommitted solve is least likely.*

---

## 150. Evaluate Reverse Polish Notation
*Array · Math · Stack · operands wait on a stack until an operator consumes them — pop right THEN left, apply, push the result; truncate toward zero*

| # | Problem | Diff | Status | Why similar |
|---|---------|------|--------|-------------|
| 1006 | [Clumsy Factorial](https://leetcode.com/problems/clumsy-factorial/) | Medium | ☑ 2026-10-20 | Same push-operands / apply-operators stack, but the expression is INFIX with a fixed `* / + -` rotation — so precedence appears: apply `*` and `/` to the stack top immediately, defer `+` and `-` by pushing ±value, then sum the stack |
| 439 | [Ternary Expression Parser](https://leetcode.com/problems/ternary-expression-parser/) | Medium | ☐ | RPN read BACKWARDS: scan right-to-left pushing operands, and when you hit `?` the condition before it pops two operands and pushes the chosen one — the same "operator consumes the stack top" step with a different operator |
| 1106 | [Parsing A Boolean Expression](https://leetcode.com/problems/parsing-a-boolean-expression/) | Hard | ☐ | Operators become VARIADIC and bracketed: push everything, and on `)` pop operands back to `(`, apply the operator just below it to all of them, push one result — RPN's evaluation step generalised to n operands |
| 772 | [Basic Calculator III](https://leetcode.com/problems/basic-calculator-iii/) | Hard | ☐ | The full infix calculator: `+ - * /` precedence AND parentheses — each `(` opens a fresh evaluation (a recursive call or a saved stack frame), each `)` collapses it to one operand, which is exactly what RPN makes explicit |

*Filtered as already solved: 227 (Basic Calculator II — `BasicCalculatorII`, the precedence-without-parentheses step 772 builds on), 1628 (Design an Expression Tree With Evaluate Function — the closest sibling: the same postfix scan building tree nodes instead of values). Also skipped: 224 (Basic Calculator — Top-150 staple with high blind-spot risk, and 772 subsumes it), 394 (Decode String — LeetCode-75 staple, same risk). Note: 150 itself has no file in the repo — solved on LeetCode but never committed.*

---

## 19. Remove Nth Node From End of List
*Linked List · Two Pointers · fixed-gap pointers — a lead pointer starts n steps ahead, so when it reaches the end the trailer sits exactly before the target; a dummy node makes "delete the head" an ordinary case*

| # | Problem | Diff | Status | Why similar |
|---|---------|------|--------|-------------|
| 369 | [Plus One Linked List](https://leetcode.com/problems/plus-one-linked-list/) | Medium | ☑ 2026-10-17 | Dummy node in front for the case where the head itself changes (999 → 1000), plus a trailing pointer that remembers the last non-9 node instead of the node n from the end |
| 708 | [Insert into a Sorted Circular Linked List](https://leetcode.com/problems/insert-into-a-sorted-circular-linked-list/) | Medium | ☑ 2026-10-18 | prev/curr pair walking a list to find a splice point, but circular: no null to stop on, and the max→min wrap plus the all-equal list are the edge cases a dummy can't absorb |
| 2905 | [Find Indices With Index and Value Difference II](https://leetcode.com/problems/find-indices-with-index-and-value-difference-ii/) | Medium | ☐ | 19's gap moved onto an array: j leads, i = j − indexDifference trails at a fixed distance, and the trailer feeds a running min/max so each j is checked in O(1) |
| 220 | [Contains Duplicate III](https://leetcode.com/problems/contains-duplicate-iii/) | Hard | ☐ | The extension: the gap becomes "at most k apart", so the trailer evicts nums[i − k − 1] from a bucket map (or TreeSet) while j leads; the value check needs width-(valueDiff+1) buckets |

*Filtered as already solved: 1721 (Swapping Nodes — k-th from the start and the end), 2095 (Delete the Middle Node), 61 (Rotate List), 2674 (Split a Circular Linked List), 1650 (LCA of a Binary Tree III — pointer switching to equalise distances), 237 (Delete Node in a Linked List), 3217 (Delete Nodes From Linked List Present in Array), 2817 (Minimum Absolute Difference Between Elements With Constraint — the gap-pointer array version, already done), 142, 2130, 138. 876 and 160 skipped as Easy. Checked against the repo only, and the repo is NOT trustworthy for linked lists (2130 and 138 were retracted under 143). 369, 708 and 2 (Add Two Numbers, left out as Top-150 risk) have no file.*

---

## 143. Reorder List
*Linked List · Two Pointers · in-place restructure in three passes — slow/fast pointers to find the middle, reverse the second half, then weave the two halves together by rewiring next pointers*

| # | Problem | Diff | Status | Why similar |
|---|---------|------|--------|-------------|
| 2816 | [Double a Number Represented as a Linked List](https://leetcode.com/problems/double-a-number-represented-as-a-linked-list/) | Medium | ☑ 2026-10-17 | Reverse to process from the far end, then reverse back — the reversal is a tool rather than the goal; the follow-up drops it entirely by looking one node ahead for the carry |
| 2074 | [Reverse Nodes in Even Length Groups](https://leetcode.com/problems/reverse-nodes-in-even-length-groups/) | Medium | ☑ 2026-10-17 | Reversal plus reconnection inside one list: groups of size 1, 2, 3… and only even-length ones flip, so each group needs its own before/after links stitched back — the trap is a short last group whose actual length decides the flip |
| 2046 | [Sort Linked List Already Sorted Using Absolute Values](https://leetcode.com/problems/sort-linked-list-already-sorted-using-absolute-values/) | Medium | ☑ 2026-10-17 | Pure pointer rewiring in one pass: the negatives sit in reverse order, so cut each one out and push it to the head — head insertion is how a reversal is built, applied selectively instead of to the whole list |

*Filtered as already solved: 2130 (Maximum Twin Sum of a Linked List — solved on LeetCode but not committed; backfilled 2026-10-17), 138 (Copy List with Random Pointer — same, backfilled 2026-10-17), 25 (Reverse Nodes in k-Group), 92 (Reverse Linked List II), 328 (Odd Even Linked List — the inverse of the weave), 148 (Sort List — middle split + merge), 2095 (Delete the Middle Node), 1721 (Swapping Nodes), 24 (Swap Nodes in Pairs), 142 (Linked List Cycle II), 445 (Add Two Numbers II), 61, 86, 725, 1669, 2058, 2487. 234 (Palindrome Linked List — middle + reverse + compare) skipped as Easy. No Hard: the linked-list Hards (25, 23) are both solved. Two rows (2130, 138) were retracted as uncommitted solves, so the repo is NOT trustworthy for linked lists — verify the remaining rows on the LeetCode profile via chrome. 143 itself and 206 (Reverse Linked List) also have no file.*

---

## 658. Find K Closest Elements
*Array · Binary Search · Two Pointers · Sorting · in sorted order the k closest to x are always a contiguous window — shrink from whichever end is farther (a tie drops the larger), or binary-search the window's LEFT edge in [0, n − k] by comparing x − arr[mid] with arr[mid + k] − x*

| # | Problem | Diff | Status | Why similar |
|---|---------|------|--------|-------------|
| 475 | [Heaters](https://leetcode.com/problems/heaters/) | Medium | ☑ 2026-10-17 | 658's "x sits between two sorted neighbours" step, asked once per house: sort the heaters, find each house's insertion point, take the nearer of its two neighbours, and answer the max over houses. Or sort both and sweep with two pointers, converging without any binary search |
| 1818 | [Minimum Absolute Sum Difference](https://leetcode.com/problems/minimum-absolute-sum-difference/) | Medium | ☐ | For every i, the best replacement is the value in sorted(nums1) closest to nums2[i] — the lower bound or its predecessor, the same two-neighbour check — found in O(log n) per query instead of a linear scan. Adds "best single improvement to a total" and a mod |
| 1300 | [Sum of Mutated Array Closest to Target](https://leetcode.com/problems/sum-of-mutated-array-closest-to-target/) | Medium | ☑ 2026-10-20 | The same "closest, ties to the smaller" rule moved onto the ANSWER space: the capped sum is monotone in value, so binary-search where it crosses target and compare the candidates either side — 658's comparison applied to a value you search for, not one you are handed |
| 272 | [Closest Binary Search Tree Value II](https://leetcode.com/problems/closest-binary-search-tree-value-ii/) | Hard | ☐ | 658 on a BST: an inorder traversal IS the sorted array, so a k-sized deque that evicts the farther end is the shrink-from-both-ends solution verbatim. The real lesson is O(log n + k): predecessor and successor stacks expanding outward from target, without materialising the array |
| 1847 | [Closest Room](https://leetcode.com/problems/closest-room/) | Hard | ☐ | Closest id to `preferred` with ties to the SMALLER id — 658's exact tie rule — but only among rooms of size >= minSize. Sort queries by minSize descending, add rooms to a TreeSet as they qualify, then `floor` / `ceiling` around preferred: offline ordering turns a changing candidate set into a sorted one |

*Filtered as already solved: 2817 (Minimum Absolute Difference Between Elements With Constraint — the direct sibling, a TreeSet floor/ceiling closest-value lookup), 4 (Median of Two Sorted Arrays — binary search on a cut comparing the boundary elements, the same move as 658's `arr[mid]` vs `arr[mid + k]`), 373 (Find K Pairs with Smallest Sums), 378 (Kth Smallest Element in a Sorted Matrix), 16 (3Sum Closest).*
*Deliberately NOT suggested: 973 (K Closest Points to Origin) is unfound in the repo but a NeetCode / Top-150 staple, exactly where the blind spot concentrates — and it is the heap / quickselect version over UNSORTED input, which the two heap versions in `FindKClosestElements` already drill. 719 (Find K-th Smallest Pair Distance) is already pending under 167. 270 (Closest Binary Search Tree Value) is Easy.*
*Source note: `FindKClosestElements.java` holds four versions — shrink from both ends (the main one, O(n − k)), two heaps split at x, expand outward from the closest index, and a sort-everything heap. None is the O(log(n − k) + k) binary search on the window's left edge. Rows checked against the repo only; 475 is the best-known of the five, 1818 / 1300 / 1847 are contest-era, and 272 needs Premium.*

---

## 209. Minimum Size Subarray Sum
*Array · Sliding Window · variable window minimising length — grow right until the window qualifies, then shrink left while it still qualifies; works only because the aggregate is monotonic (positive numbers: shrinking can only lower the sum)*

| # | Problem | Diff | Status | Why similar |
|---|---------|------|--------|-------------|
| 1234 | [Replace the Substring for Balanced String](https://leetcode.com/problems/replace-the-substring-for-balanced-string/) | Medium | ☑ 2026-10-16 | Same grow-then-shrink loop minimising length, but the condition is on what lies OUTSIDE the window: every letter's count outside must be ≤ n/4, so it's tracked by subtracting window chars from the global counts |
| 2875 | [Minimum Size Subarray in Infinite Array](https://leetcode.com/problems/minimum-size-subarray-in-infinite-array/) | Medium | ☑ 2026-10-17 | 209 on an infinitely repeated array with sum EXACTLY target: take whole copies with target / total, then run the window over two concatenated copies for the remainder |
| 3097 | [Shortest Subarray With OR at Least K II](https://leetcode.com/problems/shortest-subarray-with-or-at-least-k-ii/) | Medium | ☐ | Same shrink-while-valid loop, but OR can't be undone by subtraction — keep a count per bit (32 counters) so removing nums[left] knows which bits actually disappear |
| 2302 | [Count Subarrays With Score Less Than K](https://leetcode.com/problems/count-subarrays-with-score-less-than-k/) | Hard | ☐ | The extension: the window is still monotonic (sum × length only grows), but you COUNT every valid window instead of minimising one — each right end adds right − left + 1 |

*Filtered as already solved: 1658 (Minimum Operations to Reduce X to Zero — 209's complement, longest window with sum total − x), 713 (Subarray Product Less Than K), 1004 (Max Consecutive Ones III), 1838 (Frequency of the Most Frequent Element), 2958 (Length of Longest Subarray With at Most K Frequency), 1695 (Maximum Erasure Value). Also skipped: 862 (Shortest Subarray with Sum at Least K — 209 with negatives, where shrinking stops being safe and a monotonic deque of prefix sums takes over) is already pending under 560. Checked against the repo only.*

---

## 567. Permutation in String
*String · Sliding Window · Hash Table · fixed-size window carrying a 26-count signature — slide by adding one char and removing one, and track how many letters currently match instead of comparing whole arrays each step*

| # | Problem | Diff | Status | Why similar |
|---|---------|------|--------|-------------|
| 1297 | [Maximum Number of Occurrences of a Substring](https://leetcode.com/problems/maximum-number-of-occurrences-of-a-substring/) | Medium | ☑ 2026-10-16 | Same fixed window with a count array, but the window test is "distinct letters ≤ maxLetters" (a running distinct counter) and qualifying substrings go into a frequency map. The trap: maxSize is a decoy, since any longer valid window contains a minSize one that occurs at least as often |
| 3297 | [Count Substrings That Can Be Rearranged to Contain a String I](https://leetcode.com/problems/count-substrings-that-can-be-rearranged-to-contain-a-string-i/) | Medium | ☐ | 567 asks whether a window IS a permutation of the target; this asks whether it CONTAINS one. The window stops being fixed-size: shrink from the left while the "letters still needed" counter is 0, and every valid left endpoint counts all its extensions to the right |
| 2953 | [Count Complete Substrings](https://leetcode.com/problems/count-complete-substrings/) | Hard | ☐ | The extension: 567's fixed window run 26 times, once per possible distinct-letter count c, with length c*k and the matches counter tracking "letters with exactly k occurrences". The adjacent-difference ≤ 2 rule first splits the string into independent segments |
| 30 | [Substring with Concatenation of All Words](https://leetcode.com/problems/substring-with-concatenation-of-all-words/) | Hard | ☐ | 567 with whole words as the alphabet: the signature is a HashMap of word counts, the window slides one word at a time, and it has to be run once per starting offset 0..wordLen-1 to cover every alignment |

*Filtered as already solved: 76 (Minimum Window Substring — the variable-window version of 3297's counter), 2461 (Maximum Sum of Distinct Subarrays With Length K), 239 (Sliding Window Maximum), 438 (Find All Anagrams in a String — the direct sibling, solved on LeetCode but not committed). Note: 567 itself is not in the repo either, so it is another LeetCode-only solve. Checked against the repo only; 30 is a Top-150 staple, so it has the highest risk of being an uncommitted solve.*

---

## 3. Longest Substring Without Repeating Characters
*Hash Table · String · Sliding Window · grow the window right; the moment the invariant (all distinct) breaks, move left past the duplicate — a last-seen index lets it JUMP instead of step — and the answer is the widest valid window*

| # | Problem | Diff | Status | Why similar |
|---|---------|------|--------|-------------|
| 2401 | [Longest Nice Subarray](https://leetcode.com/problems/longest-nice-subarray/) | Medium | ☑ 2026-10-16 | 3 over a 30-letter alphabet: "no two elements share a set bit" is "no repeated character" over bits, so the seen-set becomes a single OR mask — add with `\|`, evict with `^` — and the shrink loop is unchanged. LeetCode lists 3 as its first similar question |
| 1438 | [Longest Continuous Subarray With Absolute Diff Less Than or Equal to Limit](https://leetcode.com/problems/longest-continuous-subarray-with-absolute-diff-less-than-or-equal-to-limit/) | Medium | ☑ 2026-10-16 | Same longest-valid-window loop, but validity depends on the window's max and min, which a hash set cannot hand back after an eviction — two monotonic deques carry them, and left can no longer jump, it has to step |
| 395 | [Longest Substring with At Least K Repeating Characters](https://leetcode.com/problems/longest-substring-with-at-least-k-repeating-characters/) | Medium | ☐ | The counter-example: "every character appears >= k times" is NOT shrink-safe — shrinking can break it and growing can fix it — so 3's expand/shrink loop is simply wrong here. Fix the number of distinct letters (1..26) to make it monotone again, or split at letters whose total count is below k |
| 992 | [Subarrays with K Different Integers](https://leetcode.com/problems/subarrays-with-k-different-integers/) | Hard | ☐ | From the LONGEST window to COUNTING windows: "exactly k distinct" is not monotone either, but atMost(k) − atMost(k−1) is, and each atMost pass is 3's shrink-on-violation loop adding `right − left + 1` per step |

*Filtered as already solved: 2958 (Length of Longest Subarray With at Most K Frequency — 3 generalised from one copy to k, the direct sibling), 1695 (Maximum Erasure Value — 3 over integers, maximising a sum), 2461 (Maximum Sum of Distinct Subarrays With Length K), 159 (Longest Substring with At Most Two Distinct Characters), 340 (Longest Substring with At Most K Distinct Characters), 904 (Fruit Into Baskets), 1004 (Max Consecutive Ones III), 487 (Max Consecutive Ones II), 1493 (Longest Subarray of 1's After Deleting One Element), 1208 (Get Equal Substrings Within Budget), 76 (Minimum Window Substring), 2009 (Minimum Number of Operations to Make Array Continuous), 1358 (Number of Substrings Containing All Three Characters), 2799 (Count Complete Subarrays in an Array), 1248 (Count Number of Nice Subarrays), 209 (Minimum Size Subarray Sum), 1658 (Minimum Operations to Reduce X to Zero), 239 (Sliding Window Maximum), 438 (Find All Anagrams in a String — solved on LeetCode, not committed).*
*Deliberately NOT suggested: 424 (Longest Repeating Character Replacement) and 567 (Permutation in String) are unfound in the repo but are Blind-75 / NeetCode staples, exactly where the uncommitted-solve blind spot concentrates; 2024 (Maximize the Confusion of an Exam) is 1004 with two letters. The fair fifth pick, 1234 (Replace the Substring for Balanced String, Medium — the window flipped, the SHORTEST window whose complement satisfies the counts), has since been solved and committed (`ReplaceTheSubstringForBalancedString`, 2026-10-16).*
*Source note: 3 itself is not in the repo — a Blind-75 staple almost certainly solved on LeetCode but never committed.*

---

## 155. Min Stack
*Stack · Design · store the aggregate of everything BENEATH each element alongside it — push (val, min-so-far), so the answer is always at the top and a pop restores the previous answer for free*

| # | Problem | Diff | Status | Why similar |
|---|---------|------|--------|-------------|
| 1352 | [Product of the Last K Numbers](https://leetcode.com/problems/product-of-the-last-k-numbers/) | Medium | ☑ 2026-10-15 | Same "keep a running aggregate per pushed entry" idea, but the aggregate is a prefix PRODUCT queried over the last k entries — and a 0 forces you to reset the whole history instead of dividing by it |
| 1438 | [Longest Continuous Subarray With Absolute Diff Less Than or Equal to Limit](https://leetcode.com/problems/longest-continuous-subarray-with-absolute-diff-less-than-or-equal-to-limit/) | Medium | ☑ 2026-10-16 | Min Stack answers "min of everything so far"; here you need min AND max of a sliding window, so elements also leave from the FRONT — the min-stack becomes two monotonic deques |
| 716 | [Max Stack](https://leetcode.com/problems/max-stack/) | Hard | ☑ 2026-10-20 | The mirror of 155 plus `popMax`, which removes from the MIDDLE and breaks the per-entry aggregate trick — forces a TreeMap + doubly linked list (or lazy deletion with two heaps) |
| 2398 | [Maximum Number of Robots Within Budget](https://leetcode.com/problems/maximum-number-of-robots-within-budget/) | Hard | ☐ | The extension: an O(1) window max (monotonic deque) combined with a running window sum inside a two-pointer loop, under a cost formula `max + k * sum` |

*Filtered as already solved: 901 (Online Stock Span — `StockSpanner`, the closest sibling: each stack entry carries an aggregated span), 1381 (Design a Stack With Increment Operation — `CustomStack`, a lazy per-entry increment), 895 (Maximum Frequency Stack — `FreqStackV2`), 239 (Sliding Window Maximum). Note: 155 was solved on LeetCode but never committed — backfilled as `MinStack` on 2026-10-14.*

---

## 881. Boats to Save People
*Array · Two Pointers · Greedy · Sorting · sort, then let the heaviest remaining element decide — pair it with the lightest if they fit, otherwise it goes alone; if the lightest cannot share with the heaviest, nobody can*

| # | Problem | Diff | Status | Why similar |
|---|---------|------|--------|-------------|
| 2576 | [Find the Maximum Number of Marked Indices](https://leetcode.com/problems/find-the-maximum-number-of-marked-indices/) | Medium | ☑ 2026-10-08 | Same sort-then-pair, but pairing the extremes is now WRONG — match the smaller half against the larger half under `2*nums[i] <= nums[j]`, giving each small element the smallest partner that works. On [2,3,4,5,9,10] extremes mark 4, half-split marks 6 |
| 2563 | [Count the Number of Fair Pairs](https://leetcode.com/problems/count-the-number-of-fair-pairs/) | Medium | ☑ 2026-10-09 | Same `a + b <= limit` test on a sorted array, but COUNTS every qualifying pair instead of consuming them — `count(sum <= upper) - count(sum <= lower - 1)`, each half a converging two-pointer pass |
| 870 | [Advantage Shuffle](https://leetcode.com/problems/advantage-shuffle/) | Medium | ☑ 2026-10-14 | The same "the extreme decides" logic across TWO sorted arrays: if your best cannot beat their best, throw your worst at it — boats' heaviest-goes-alone, recast as a deliberate sacrifice |
| 2071 | [Maximum Number of Tasks You Can Assign](https://leetcode.com/problems/maximum-number-of-tasks-you-can-assign/) | Hard | ☐ | The extension: binary-search the answer k, then greedily match the k easiest tasks to the k strongest workers through a deque, spending a pill only when forced — sorted greedy pairing under a budget |

*Filtered as already solved: 1877 (Minimize Maximum Pair Sum in Array — the direct sibling, identical extreme-pairing), 948 (Bag of Tokens), 2410 (Maximum Matching of Players With Trainers), 1561 (Maximum Number of Coins You Can Get), 2491 (Divide Players Into Teams of Equal Skill). Also skipped: 1498 (already pending under 167), 1679 (Max Number of K-Sum Pairs — LeetCode-75 staple and effectively Two Sum II, high blind-spot risk), 2856 (Minimum Array Length After Pair Removals — real solution is frequency maths, and its two-pointer form duplicates 2576).*

---

## 42. Trapping Rain Water
*Array · Two Pointers · Monotonic Stack · water at i = min(maxLeft, maxRight) − h[i]: split it into a left pass and a right pass and combine per index. Two pointers make it O(1) space, because the lower side's bound is already final*

| # | Problem | Diff | Status | Why similar |
|---|---------|------|--------|-------------|
| 2012 | [Sum of Beauty in the Array](https://leetcode.com/problems/sum-of-beauty-in-the-array/) | Medium | ☑ 2026-10-07 | 42's DP form stripped to the bone: prefix-max and suffix-min arrays, then each index is judged against both sides. Same two-pass split, but the per-index combine is a comparison instead of min(L, R) − h |
| 2866 | [Beautiful Towers II](https://leetcode.com/problems/beautiful-towers-ii/) | Medium | ☑ 2026-10-16 | Left pass + right pass combined at each index again (the peak), but each pass needs a monotonic stack to build the best non-increasing profile. 42's prefix/suffix method and its stack method in one problem |
| 1944 | [Number of Visible People in a Queue](https://leetcode.com/problems/number-of-visible-people-in-a-queue/) | Hard | ☐ | 42's stack form: a decreasing stack where each taller arrival pops the shorter bars it hides. In 42 each pop fills a basin; here each pop counts a line of sight |
| 407 | [Trapping Rain Water II](https://leetcode.com/problems/trapping-rain-water-ii/) | Hard | ☐ | 42 in 2D. "Advance the lower side, its bound is final" becomes "pop the lowest boundary cell from a min-heap": a Dijkstra-like flood inward from the edges |

*Filtered as already solved: 84 (Largest Rectangle in Histogram), 907 (Sum of Subarray Minimums), 1856 (Maximum Subarray Min-Product), 769 (Max Chunks To Make Sorted), 768 (Max Chunks To Make Sorted II), 2017 (Grid Game).*
*Note: 42 itself and 238 (Product of Array Except Self, the other two-pass prefix/suffix staple) are both solved on LeetCode but not committed here, so this concept area is more picked-over than the repo shows. Rows checked against the repo only. 407 is the riskiest, as the famous direct sequel; the other three are contest-era.*

---

## 11. Container With Most Water
*Array · Two Pointers · Greedy · converge from both ends and always move the LIMITING side — keeping it can never beat a narrower width, so it is eliminated*

| # | Problem | Diff | Status | Why similar |
|---|---------|------|--------|-------------|
| 2104 | [Sum of Subarray Ranges](https://leetcode.com/problems/sum-of-subarray-ranges/) | Medium | ☑ 2026-10-06 | Same "a window is governed by its extremes" objective, but summed over EVERY subarray rather than maximised over one — so the elimination argument is replaced by counting each element's span of dominance with a monotonic stack |
| 1793 | [Maximum Score of a Good Subarray](https://leetcode.com/problems/maximum-score-of-a-good-subarray/) | Hard | ☑ 2026-10-06 | The closest relative: score is min(window) x width, exactly 11's shape. Pointers EXPAND outward from a fixed pivot instead of converging, and you always step toward the larger neighbour — the same "the limiting side decides the move" argument, mirrored |
| 1537 | [Get the Maximum Score](https://leetcode.com/problems/get-the-maximum-score/) | Hard | ☐ | Two pointers advancing over two sorted arrays, always moving the smaller — the elimination argument transplanted from one array's two ends to two arrays' two fronts, with accumulation between shared checkpoints |

*Filtered as already solved: 42 (Trapping Rain Water — the direct sibling, same move-the-smaller-side rule, solved on LeetCode but not committed here), 84 (Largest Rectangle in Histogram), 85 (Maximal Rectangle), 907 (Sum of Subarray Minimums), 881 (Boats to Save People), 1838 (Frequency of the Most Frequent Element).*
*Note: only three, and the tight family really is exhausted — 42, 84 and 85 are the canonical companions and all three are done. 2104 and 1537 are deliberate widenings, labelled above as such: 2104 keeps the objective and drops the elimination, 1537 keeps the elimination and changes the geometry.*

---

## 189. Rotate Array
*Array · Math · Two Pointers · decompose a rotation into REVERSALS to get it in place — reverse all, reverse the first k, reverse the rest*

| # | Problem | Diff | Status | Why similar |
|---|---------|------|--------|-------------|
| 396 | [Rotate Function](https://leetcode.com/problems/rotate-function/) | Medium | ☑ 2026-10-06 | Stops performing rotations and starts REASONING about all n of them — derive F(k) from F(k-1) in O(1) so every rotation is scored in one pass instead of n passes |
| 798 | [Smallest Rotation with Highest Score](https://leetcode.com/problems/smallest-rotation-with-highest-score/) | Hard | ☐ | Same "score every rotation at once" goal as 396, but each element contributes over a RANGE of rotation offsets, so the aggregation needs a difference array indexed by offset rather than a recurrence |

*Filtered as already solved: 48 (Rotate Image — backfilled into the repo 2026-10-06), 61 (Rotate List), 186 (Reverse Words in a String II — the two-level reversal, closest sibling), 151 (Reverse Words in a String), 1861 (Rotating the Box); plus the entire spiral / ring-traversal family — 54, 59, 885, 2326.*
*Note: only TWO candidates, and not for lack of searching. Both families that 189 belongs to are exhausted — reversal-decomposition (61, 186, 151, 1861, 48) and concentric-ring traversal (54, 59, 885, 2326) are entirely solved, and 867 (Transpose Matrix) is Easy. 396 and 798 survive because they are about reasoning over rotations rather than performing one.*
*Adjacent but a DIFFERENT concept, deliberately not mixed in: the binary-search-on-rotated cluster — 33, 81, 153, 154 — is entirely absent from the repo. Those treat a rotated array as a search space rather than practising the rotation itself, and 33/153 are Blind-75 staples where the uncommitted-solve blind spot concentrates. Worth a separate `/practice 33` run if that is the thread wanted.*

---

## 167. Two Sum II - Input Array Is Sorted
*Array · Two Pointers · Binary Search · converge from both ends and let the sortedness decide which pointer moves — O(n) with no extra space*

| # | Problem | Diff | Status | Why similar |
|---|---------|------|--------|-------------|
| 923 | [3Sum With Multiplicity](https://leetcode.com/problems/3sum-with-multiplicity/) | Medium | ☑ 2026-10-06 | Same converging scan, but COUNTING rather than finding — ties force real combinatorics (`C(n,2)` when both pointers sit on equal values), which is the case a find-one solution never has to think about |
| 1498 | [Number of Subsequences That Satisfy the Given Sum Condition](https://leetcode.com/problems/number-of-subsequences-that-satisfy-the-given-sum-condition/) | Medium | ☐ | Converging pointers where ONE match settles 2^(j-i) subsequences at once — the same "a single pointer move resolves many answers" leverage as 611, but with modular powers of two |
| 826 | [Most Profit Assigning Work](https://leetcode.com/problems/most-profit-assigning-work/) | Medium | ☐ | Two pointers that both sweep FORWARD over two separately sorted arrays while carrying a running max, instead of converging from the ends — the other half of the two-pointer family |
| 719 | [Find K-th Smallest Pair Distance](https://leetcode.com/problems/find-k-th-smallest-pair-distance/) | Hard | ☐ | Two pointers become a COUNTING SUBROUTINE inside a binary search on the answer: guess a distance, count pairs within it in O(n), and bisect. Already scaffolded empty at `src/hard/FindKThSmallestPairDistance.java` |

*Filtered as already solved: 16 (3Sum Closest), 18 (4Sum), 259 (3Sum Smaller), 611 (Valid Triangle Number), 881 (Boats to Save People), 2410 (Maximum Matching of Players With Trainers), 1877 (Minimize Maximum Pair Sum in Array), 658 (Find K Closest Elements), 2616 (Minimize the Maximum Difference of Pairs), 42 (Trapping Rain Water), 4 (Median of Two Sorted Arrays).*
*Deliberately NOT suggested: 15 (3Sum) and 11 (Container With Most Water) are both unfound in the repo but are Blind-75 staples, which is exactly where the uncommitted-solve blind spot concentrates. Ask before trusting either.*
*Source note: 167 itself was solved on LeetCode but never committed — backfilled into the repo 2026-10-05, which confirms the reading above about this neighbourhood.*

---

## 41. First Missing Positive
*Array · Hash Table · index-as-hash — when the value range matches the index range, the array IS the hash table; then scan for the first gap*

| # | Problem | Diff | Status | Why similar |
|---|---------|------|--------|-------------|
| 1798 | [Maximum Number of Consecutive Values You Can Make](https://leetcode.com/problems/maximum-number-of-consecutive-values-you-can-make/) | Medium | ☑ 2026-10-05 | The "first gap" half of 41 in isolation — sweep sorted coins tracking the smallest unreachable total, and stop the moment a coin exceeds it. No array trickery, just the gap argument |
| 765 | [Couples Holding Hands](https://leetcode.com/problems/couples-holding-hands/) | Hard | ☐ | The swap-into-place half of 41, taken seriously: keep a value-to-index map and swap each person to their partner. Counting the swaps is cycle decomposition, which is what 41's while-loop is doing implicitly |
| 330 | [Patching Array](https://leetcode.com/problems/patching-array/) | Hard | ☐ | "Smallest value not yet reachable" promoted from an answer to a loop invariant — you may insert numbers to close gaps, so the gap you track drives the greedy rather than terminating it |
| 2003 | [Smallest Missing Genetic Value in Each Subtree](https://leetcode.com/problems/smallest-missing-genetic-value-in-each-subtree/) | Hard | ☐ | Literally 41 answered for every subtree at once. Needs a presence array indexed by value, plus small-to-large merging so the total stays near-linear instead of O(n^2) |

*Filtered as already solved: 442 (Find All Duplicates in an Array), 287 (Find the Duplicate Number — backfilled 2026-10-04), 2471 (Minimum Number of Operations to Sort a Binary Tree by Level — the cycle-decomposition sibling of 765), 73 (Set Matrix Zeroes), 289 (Game of Life).*
*Note: only one Medium survives. The index-as-hash family's Medium tier is exhausted — 442 and 287 are solved, and 448 (Find All Numbers Disappeared) and 645 (Set Mismatch) are Easy, so excluded. The set is Hard-weighted by availability, not by choice.*

---

## 560. Subarray Sum Equals K
*Array · Hash Table · Prefix Sum · carry a running prefix state and look up how many earlier prefixes complete the target — counting in one pass instead of enumerating pairs*

| # | Problem | Diff | Status | Why similar |
|---|---------|------|--------|-------------|
| 1442 | [Count Triplets That Can Form Two Arrays of Equal XOR](https://leetcode.com/problems/count-triplets-that-can-form-two-arrays-of-equal-xor/) | Medium | ☑ 2026-10-05 | One dimension changed: the accumulator is prefix XOR rather than prefix sum, so "equal halves" collapses to "prefix repeats" — the same map, a different group operation |
| 1371 | [Find the Longest Substring Containing Vowels in Even Counts](https://leetcode.com/problems/find-the-longest-substring-containing-vowels-in-even-counts/) | Medium | ☑ 2026-10-05 | The prefix state becomes a 5-bit parity MASK instead of a number, and the map stores the FIRST index rather than a count because the answer is a length — both axes of 560 varied at once |
| 1915 | [Number of Wonderful Substrings](https://leetcode.com/problems/number-of-wonderful-substrings/) | Medium | ☐ | Prefix bitmask with COUNTS, back to 560's shape — but "at most one odd letter" means probing 11 masks per position rather than one, which is the step that makes it a 2234-rated Medium |
| 2488 | [Count Subarrays With Median K](https://leetcode.com/problems/count-subarrays-with-median-k/) | Hard | ☐ | Exactly 560's prefix-count map once you see the transform: map each element to +1/-1 against k, and "median is k" becomes "balance equals 0 or 1". Finding the transform is the whole difficulty |
| 862 | [Shortest Subarray with Sum at Least K](https://leetcode.com/problems/shortest-subarray-with-sum-at-least-k/) | Hard | ☐ | Same prefix array, but negatives plus a `>= K` goal break the hash map entirely — needs a monotonic deque over prefixes. The counter-example that shows where 560's technique stops working |

*Filtered as already solved: 974 (Subarray Sums Divisible by K), 325 (Maximum Size Subarray Sum Equals k), 525 (Contiguous Array), 930 (Binary Subarrays With Sum), 1524 (Number of Sub-arrays With Odd Sum).*
*Not repeated: 1074 (Number of Submatrices That Sum to Target) is unsolved but already pending under 304. Also unsolved and a fair sixth pick if wanted: 523 (Continuous Subarray Sum) — prefix mod k, but the index-gap >= 2 rule is its only twist over 974, which is already done.*

---

## 229. Majority Element II
*Array · Hash Table · Sorting · Counting · Boyer-Moore Voting · cancel k-at-a-time in k-1 candidate slots, then VERIFY in a second pass*

| # | Problem | Diff | Status | Why similar |
|---|---------|------|--------|-------------|
| 260 | [Single Number III](https://leetcode.com/problems/single-number-iii/) | Medium | ☑ 2026-10-04 | Same "cancel the bulk, isolate the exceptions" shape in a different algebra — XOR annihilates the pairs, then a differing bit splits the two survivors apart. O(1) space by cancellation rather than by counting |
| 1157 | [Online Majority Element In Subarray](https://leetcode.com/problems/online-majority-element-in-subarray/) | Hard | ☐ | The direct sequel, and tagged Boyer-Moore Voting itself: the same vote must be answerable for any subarray on demand, which forces a segment tree whose merge combines vote summaries, plus binary search over the candidate's sorted positions to verify |

*Filtered as already solved: 2780 (Minimum Index of a Valid Split — Boyer-Moore then prefix counts, the closest sibling), 137 (Single Number II — the n/3 cancellation in bits), 287 (Find the Duplicate Number — backfilled into the repo 2026-10-04), 41 (First Missing Positive), 442 (Find All Duplicates in an Array), 1838 (Frequency of the Most Frequent Element), 451 (Sort Characters By Frequency), 347 (Top K Frequent Elements).*
*Note: only TWO candidates, below the usual 3-5, and deliberately not padded. The Boyer-Moore family on LeetCode is just 169, 229, 1157 and 2780 — two are solved and 169 is Easy — so 1157 is the only true relative, and 260 is the nearest adjacent concept. Everything else in this space is frequency counting that merely shares a tag.*

---

## 122. Best Time to Buy and Sell Stock II
*Array · Greedy · Dynamic Programming · prove the objective decomposes into independent adjacent deltas, then take every positive one*

| # | Problem | Diff | Status | Why similar |
|---|---------|------|--------|-------------|
| 1024 | [Video Stitching](https://leetcode.com/problems/video-stitching/) | Medium | ☐ | Greedy still wins, but the local quantity is the furthest reach within the current layer rather than a positive delta — the interval form of the Jump Game II argument |
| 2008 | [Maximum Earnings From Taxi](https://leetcode.com/problems/maximum-earnings-from-taxi/) | Medium | ☑ 2026-10-04 | Unlimited non-overlapping trades, except each now carries its own weight — so greedy fails and you need DP over sorted endpoints with binary search. The gentlest version of that lift |
| 1235 | [Maximum Profit in Job Scheduling](https://leetcode.com/problems/maximum-profit-in-job-scheduling/) | Hard | ☐ | The same DP as 2008 with arbitrary intervals instead of a timeline, which is where 122's greedy decisively dies — the canonical weighted interval scheduling problem |
| 1751 | [Maximum Number of Events That Can Be Attended II](https://leetcode.com/problems/maximum-number-of-events-that-can-be-attended-ii/) | Hard | ☐ | The k-constrained version of 1235, standing to it exactly as 188 stands to 122 — the same ladder already climbed on stocks, now on intervals |

*Filtered as already solved: the whole stock family — 123 (III), 188 (IV), 714 (Transaction Fee), 309 (Cooldown); 435 (Non-overlapping Intervals), 134 (Gas Station) and 45 (Jump Game II) — all three backfilled into the repo 2026-10-04; plus 1353 (Maximum Number of Events That Can Be Attended — the unweighted prequel to 1751), 1749, 1963, 853 (Car Fleet), 1306 (Jump Game III), 646 (Maximum Length of Pair Chain), 1626 (Best Team With No Conflicts), 452 (Minimum Number of Arrows to Burst Balloons).*

---

## 128. Longest Consecutive Sequence
*Array · Hash Table · Union Find · hash set as an O(1) membership oracle, expanding a run only from its left boundary*

| # | Problem | Diff | Status | Why similar |
|---|---------|------|--------|-------------|
| 549 | [Binary Tree Longest Consecutive Sequence II](https://leetcode.com/problems/binary-tree-longest-consecutive-sequence-ii/) | Medium | ☑ 2026-10-04 | Same longest-consecutive-run question lifted onto a tree, and the run may bend through a node — so each node returns an increasing and a decreasing length, joined at the parent |
| 1562 | [Find Latest Group of Size M](https://leetcode.com/problems/find-latest-group-of-size-m/) | Medium | ☑ 2026-10-04 | The boundary trick made dynamic: insert positions one at a time and maintain each run's length at its two endpoints, merging with the neighbours — exactly what `!set.contains(x-1)` decides statically |
| 352 | [Data Stream as Disjoint Intervals](https://leetcode.com/problems/data-stream-as-disjoint-intervals/) | Hard | ☐ | The direct dynamic sequel: consecutive runs must be queryable after every insert, so the one-shot hash set becomes a TreeMap of intervals that splits and merges |
| 2213 | [Longest Substring of One Repeating Character](https://leetcode.com/problems/longest-substring-of-one-repeating-character/) | Hard | ☐ | Longest run again, but under point updates — forces a segment tree whose merge combines prefix-run, suffix-run and best-run, the gap visible in the skills profile |

*Filtered as already solved: 298 (Binary Tree Longest Consecutive Sequence), 1218 (Longest Arithmetic Subsequence of Given Difference), 2007 (Find Original Array From Doubled Array — which subsumes 954, Array of Doubled Pairs).*
*Note: 128 itself is not in the repo — a Blind-75 staple almost certainly solved on LeetCode but never committed.*

---

## 36. Valid Sudoku
*Array · Hash Table · Matrix · one sweep maintaining several constraint sets, grouping key derived from the coordinates*

| # | Problem | Diff | Status | Why similar |
|---|---------|------|--------|-------------|
| 498 | [Diagonal Traverse](https://leetcode.com/problems/diagonal-traverse/) | Medium | ☑ 2026-10-01 | The coordinate-to-group-key arithmetic in isolation — `r+c` identifies the diagonal the way `(r/3)*3 + c/3` identifies the box, with no constraints on top |
| 1895 | [Largest Magic Square](https://leetcode.com/problems/largest-magic-square/) | Medium | ☐ | Validates rows, columns and both diagonals like 36 validates rows, columns and boxes — but over every submatrix, so prefix sums carry the checking |
| 37 | [Sudoku Solver](https://leetcode.com/problems/sudoku-solver/) | Hard | ☐ | The direct sequel: identical box-index trick and three constraint sets, but now they are maintained incrementally under backtracking rather than checked once |

*Filtered as already solved: 51 (N-Queens), 52 (N-Queens II), 348 (Design Tic-Tac-Toe — in the repo as `TicTacToe.java`), 289 (Game of Life), 73 (Set Matrix Zeroes).*

---

## 238. Product of Array Except Self
*Array · Prefix Sum · accumulate from the left, accumulate from the right, combine at each index*

| # | Problem | Diff | Status | Why similar |
|---|---------|------|--------|-------------|
| 2270 | [Number of Ways to Split Array](https://leetcode.com/problems/number-of-ways-to-split-array/) | Medium | ☑ 2026-10-01 | The idea stripped to its bones — compare prefix against suffix at every split point, one running total each way |
| 2256 | [Minimum Average Difference](https://leetcode.com/problems/minimum-average-difference/) | Medium | ☑ 2026-10-01 | Same two-sided sweep, but the combine step is an average, so the element count matters as much as the sum |
| 845 | [Longest Mountain in Array](https://leetcode.com/problems/longest-mountain-in-array/) | Medium | ☑ 2026-10-01 | Left-run and right-run lengths instead of products, joined at each peak — the accumulation is a streak, not a total |
| 2167 | [Minimum Time to Remove All Cars Containing Illegal Goods](https://leetcode.com/problems/minimum-time-to-remove-all-cars-containing-illegal-goods/) | Hard | ☐ | Prefix DP and suffix DP combined per split — the pattern lifted from running totals to running optimal costs |

*Filtered as already solved: 1685 (Sum of Absolute Differences in a Sorted Array), 42 (Trapping Rain Water).*

---

## 304. Range Sum Query 2D - Immutable
*Matrix · Prefix Sum · Design · precompute cumulative sums, answer rectangles by inclusion-exclusion*

| # | Problem | Diff | Status | Why similar |
|---|---------|------|--------|-------------|
| 1314 | [Matrix Block Sum](https://leetcode.com/problems/matrix-block-sum/) | Medium | ☑ 2026-10-01 | The same integral image, applied rather than queried — every cell needs its own clamped rectangle, so it drills the boundary arithmetic |
| 1292 | [Maximum Side Length of a Square with Sum ≤ Threshold](https://leetcode.com/problems/maximum-side-length-of-a-square-with-sum-less-than-or-equal-to-threshold/) | Medium | ☐ | Prefix sum as a subroutine: O(1) rectangle queries make a binary search over side length affordable |
| 1074 | [Number of Submatrices That Sum to Target](https://leetcode.com/problems/number-of-submatrices-that-sum-to-target/) | Hard | ☐ | Collapses the 2D prefix to 1D per row-pair, then counts with a hashmap — the 2D lift of "subarray sum equals K" |
| 363 | [Max Sum of Rectangle No Larger Than K](https://leetcode.com/problems/max-sum-of-rectangle-no-larger-than-k/) | Hard | ☐ | Same row-pair collapse as 1074, but the ≤ K constraint forces an ordered set instead of a hashmap |
| 308 | [Range Sum Query 2D - Mutable](https://leetcode.com/problems/range-sum-query-2d-mutable/) | Hard | ☐ | The direct sequel: allow updates and the static prefix table dies, forcing a 2D Binary Indexed Tree |

*Filtered as already solved: 2536 (Increment Submatrices by One), 85 (Maximal Rectangle), 221 (Maximal Square).*

---

## 347. Top K Frequent Elements
*Array · Hash Table · Heap · count first, then select the top k by that count*

| # | Problem | Diff | Status | Why similar |
|---|---------|------|--------|-------------|
| 1054 | [Distant Barcodes](https://leetcode.com/problems/distant-barcodes/) | Medium | ☑ 2026-10-01 | Same frequency map feeding a max-heap, but the counts drive *placement* rather than a top-k cut — pull the most frequent first and interleave |
| 1738 | [Find Kth Largest XOR Coordinate Value](https://leetcode.com/problems/find-kth-largest-xor-coordinate-value/) | Medium | ☐ | Isolates the selection half: values are computed by prefix-XOR rather than counted, then kth-largest via heap or quickselect |
| 2542 | [Maximum Subsequence Score](https://leetcode.com/problems/maximum-subsequence-score/) | Medium | ☐ | Size-k heap maintained while sweeping a sorted order — top-k becomes a moving window instead of a one-shot extraction |
| 857 | [Minimum Cost to Hire K Workers](https://leetcode.com/problems/minimum-cost-to-hire-k-workers/) | Hard | ☐ | Same size-k heap sweep as 2542 but the sort key is a ratio, so the invariant is far harder to spot |

*Filtered as already solved: 692 (Top K Frequent Words), 451 (Sort Characters By Frequency), 1481 (Least Number of Unique Integers after K Removals), 621 (Task Scheduler), 1642 (Furthest Building You Can Reach).*

---

## 75. Sort Colors
*Array · Two Pointers · Sorting · in-place three-way partition with pointer invariants*

| # | Problem | Diff | Status | Why similar |
|---|---------|------|--------|-------------|
| 611 | [Valid Triangle Number](https://leetcode.com/problems/valid-triangle-number/) | Medium | ☑ 2026-09-30 | Sort first, then converge two pointers under an invariant — the counting twist is that one pointer move settles many pairs at once |
| 581 | [Shortest Unsorted Continuous Subarray](https://leetcode.com/problems/shortest-unsorted-continuous-subarray/) | Medium | ☑ 2026-09-29 | Two pointers converging from both ends, each maintaining a running max/min invariant — same reasoning, no swapping |
| 769 | [Max Chunks To Make Sorted](https://leetcode.com/problems/max-chunks-to-make-sorted/) | Medium | ☑ 2026-09-30 | Asks where the partition boundaries *are* rather than performing one — a prefix-max invariant marks every point already correctly partitioned |
| 768 | [Max Chunks To Make Sorted II](https://leetcode.com/problems/max-chunks-to-make-sorted-ii/) | Hard | ☑ 2026-09-30 | Same as 769 but with duplicates and huge values, which is exactly the wrinkle Sort Colors' equal-element handling teaches |
| 493 | [Reverse Pairs](https://leetcode.com/problems/reverse-pairs/) | Hard | ☑ 2026-09-29 | Partitioning at full stretch: divide, count cross-pairs during the merge, then combine. The recursive cousin of the one-pass partition |

*Filtered as already solved: 2161 (Partition Array According to Given Pivot), 324 (Wiggle Sort II), 280 (Wiggle Sort), 215 (Kth Largest Element in an Array), 462 (Minimum Moves to Equal Array Elements II), 4 (Median of Two Sorted Arrays), 763 (Partition Labels), 259 (3Sum Smaller), 912 (Sort an Array — solved on LeetCode, not committed), 973 (K Closest Points to Origin — solved on LeetCode, not committed).*

---

## 49. Group Anagrams
*Array · Hash Table · String · derive a canonical key, then bucket by it*

| # | Problem | Diff | Status | Why similar |
|---|---------|------|--------|-------------|
| 893 | [Groups of Special-Equivalent Strings](https://leetcode.com/problems/groups-of-special-equivalent-strings/) | Medium | ☑ 2026-09-27 | Identical bucket-by-key shape, but the canonical form is harder: sort even and odd indices separately, then concatenate |
| 288 | [Unique Word Abbreviation](https://leetcode.com/problems/unique-word-abbreviation/) | Medium | ☑ 2026-09-27 | Canonical key is an abbreviation rather than a sorted string, and the query is uniqueness instead of grouping — same map, different question |
| 1487 | [Making File Names Unique](https://leetcode.com/problems/making-file-names-unique/) | Medium | ☐ | Map-as-canonical-registry again, but the key mutates as you go — you must remember the next free suffix per base name |
| 726 | [Number of Atoms](https://leetcode.com/problems/number-of-atoms/) | Hard | ☐ | The idea at full stretch: parse nested formulae into a count map, then emit a canonical sorted signature. Bucketing plus recursive parsing |

*Filtered as already solved: 249 (Group Shifted Strings), 2352 (Equal Row and Column Pairs), 1657 (Determine if Two Strings Are Close), 1497 (Check If Array Pairs Are Divisible by k), 916 (Word Subsets), 890 (Find and Replace Pattern), 767 (Reorganize String), 438 (Find All Anagrams in a String — solved on LeetCode, not committed here).*

---

## 1836. Remove Duplicates From an Unsorted Linked List
*Linked List · Hash Table · frequency pass, then dummy-node removal*

| # | Problem | Diff | Status | Why similar |
|---|---------|------|--------|-------------|
| 1171 | [Remove Zero Sum Consecutive Nodes](https://leetcode.com/problems/remove-zero-sum-consecutive-nodes-from-linked-list/) | Medium | ☐ | Same two-pass shape — build a map, then splice with a dummy node — but keyed on running prefix sums, so the map stores nodes rather than counts |
| 1019 | [Next Greater Node In Linked List](https://leetcode.com/problems/next-greater-node-in-linked-list/) | Medium | ☑ 2026-10-01 | Also needs full knowledge of the list before deciding anything about a node, but resolves it with a monotonic stack instead of a frequency table |

*Filtered as already solved: 82 (Remove Duplicates from Sorted List II), 2487 (Remove Nodes From Linked List), 19 (Remove Nth Node From End of List), 86 (Partition List), 92 (Reverse Linked List II), 24 (Swap Nodes in Pairs), 328 (Odd Even Linked List).*
