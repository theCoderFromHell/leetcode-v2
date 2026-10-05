# Practice Tracker

Similar-problem suggestions from `/practice`. Medium and Hard only — status refreshes on each run.

**Pending 30 · Solved 20 · Total 50** — updated 2026-10-05

Status key: `☐` pending · `☑ YYYY-MM-DD` solved · `☐ ?` unverified

---

## 167. Two Sum II - Input Array Is Sorted
*Array · Two Pointers · Binary Search · converge from both ends and let the sortedness decide which pointer moves — O(n) with no extra space*

| # | Problem | Diff | Status | Why similar |
|---|---------|------|--------|-------------|
| 923 | [3Sum With Multiplicity](https://leetcode.com/problems/3sum-with-multiplicity/) | Medium | ☐ | Same converging scan, but COUNTING rather than finding — ties force real combinatorics (`C(n,2)` when both pointers sit on equal values), which is the case a find-one solution never has to think about |
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
| 1798 | [Maximum Number of Consecutive Values You Can Make](https://leetcode.com/problems/maximum-number-of-consecutive-values-you-can-make/) | Medium | ☐ | The "first gap" half of 41 in isolation — sweep sorted coins tracking the smallest unreachable total, and stop the moment a coin exceeds it. No array trickery, just the gap argument |
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
| 2542 | [Maximum Subsequence Score](https://leetcode.com/problems/maximum-subsequence-score/) | Hard | ☐ | Size-k heap maintained while sweeping a sorted order — top-k becomes a moving window instead of a one-shot extraction |
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
