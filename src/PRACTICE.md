# Practice Tracker

Similar-problem suggestions from `/practice`. Medium and Hard only — status refreshes on each run.

**Pending 7 · Solved 0 · Total 7** — updated 2026-09-27

Status key: `☐` pending · `☑ YYYY-MM-DD` solved · `☐ ?` unverified

---

## 49. Group Anagrams
*Array · Hash Table · String · derive a canonical key, then bucket by it*

| # | Problem | Diff | Status | Why similar |
|---|---------|------|--------|-------------|
| 438 | [Find All Anagrams in a String](https://leetcode.com/problems/find-all-anagrams-in-a-string/) | Medium | ☐ | Same count-vector signature, but under a sliding window — you must update it in O(1) per step instead of rebuilding. The natural extension of 49 |
| 893 | [Groups of Special-Equivalent Strings](https://leetcode.com/problems/groups-of-special-equivalent-strings/) | Medium | ☐ | Identical bucket-by-key shape, but the canonical form is harder: sort even and odd indices separately, then concatenate |
| 288 | [Unique Word Abbreviation](https://leetcode.com/problems/unique-word-abbreviation/) | Medium | ☐ | Canonical key is an abbreviation rather than a sorted string, and the query is uniqueness instead of grouping — same map, different question |
| 1487 | [Making File Names Unique](https://leetcode.com/problems/making-file-names-unique/) | Medium | ☐ | Map-as-canonical-registry again, but the key mutates as you go — you must remember the next free suffix per base name |
| 726 | [Number of Atoms](https://leetcode.com/problems/number-of-atoms/) | Hard | ☐ | The idea at full stretch: parse nested formulae into a count map, then emit a canonical sorted signature. Bucketing plus recursive parsing |

*Filtered as already solved: 249 (Group Shifted Strings), 2352 (Equal Row and Column Pairs), 1657 (Determine if Two Strings Are Close), 1497 (Check If Array Pairs Are Divisible by k), 916 (Word Subsets), 890 (Find and Replace Pattern), 767 (Reorganize String).*

---

## 1836. Remove Duplicates From an Unsorted Linked List
*Linked List · Hash Table · frequency pass, then dummy-node removal*

| # | Problem | Diff | Status | Why similar |
|---|---------|------|--------|-------------|
| 1171 | [Remove Zero Sum Consecutive Nodes](https://leetcode.com/problems/remove-zero-sum-consecutive-nodes-from-linked-list/) | Medium | ☐ | Same two-pass shape — build a map, then splice with a dummy node — but keyed on running prefix sums, so the map stores nodes rather than counts |
| 1019 | [Next Greater Node In Linked List](https://leetcode.com/problems/next-greater-node-in-linked-list/) | Medium | ☐ | Also needs full knowledge of the list before deciding anything about a node, but resolves it with a monotonic stack instead of a frequency table |

*Filtered as already solved: 82 (Remove Duplicates from Sorted List II), 2487 (Remove Nodes From Linked List), 19 (Remove Nth Node From End of List), 86 (Partition List), 92 (Reverse Linked List II), 24 (Swap Nodes in Pairs), 328 (Odd Even Linked List).*
