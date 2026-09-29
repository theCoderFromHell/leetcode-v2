# Practice Tracker

Similar-problem suggestions from `/practice`. Medium and Hard only — status refreshes on each run.

**Pending 9 · Solved 2 · Total 11** — updated 2026-09-27

Status key: `☐` pending · `☑ YYYY-MM-DD` solved · `☐ ?` unverified

---

## 75. Sort Colors
*Array · Two Pointers · Sorting · in-place three-way partition with pointer invariants*

| # | Problem | Diff | Status | Why similar |
|---|---------|------|--------|-------------|
| 611 | [Valid Triangle Number](https://leetcode.com/problems/valid-triangle-number/) | Medium | ☐ | Sort first, then converge two pointers under an invariant — the counting twist is that one pointer move settles many pairs at once |
| 581 | [Shortest Unsorted Continuous Subarray](https://leetcode.com/problems/shortest-unsorted-continuous-subarray/) | Medium | ☐ | Two pointers converging from both ends, each maintaining a running max/min invariant — same reasoning, no swapping |
| 769 | [Max Chunks To Make Sorted](https://leetcode.com/problems/max-chunks-to-make-sorted/) | Medium | ☐ | Asks where the partition boundaries *are* rather than performing one — a prefix-max invariant marks every point already correctly partitioned |
| 768 | [Max Chunks To Make Sorted II](https://leetcode.com/problems/max-chunks-to-make-sorted-ii/) | Hard | ☐ | Same as 769 but with duplicates and huge values, which is exactly the wrinkle Sort Colors' equal-element handling teaches |
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
| 1019 | [Next Greater Node In Linked List](https://leetcode.com/problems/next-greater-node-in-linked-list/) | Medium | ☐ | Also needs full knowledge of the list before deciding anything about a node, but resolves it with a monotonic stack instead of a frequency table |

*Filtered as already solved: 82 (Remove Duplicates from Sorted List II), 2487 (Remove Nodes From Linked List), 19 (Remove Nth Node From End of List), 86 (Partition List), 92 (Reverse Linked List II), 24 (Swap Nodes in Pairs), 328 (Odd Even Linked List).*
