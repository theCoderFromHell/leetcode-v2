# Practice Tracker

Similar-problem suggestions from `/practice`. Status refreshes on each run.

**Pending 4 · Solved 0 · Total 4** — updated 2026-09-27

Status key: `☐` pending · `☑ YYYY-MM-DD` solved · `☐ ?` unverified

---

## 1836. Remove Duplicates From an Unsorted Linked List
*Linked List · Hash Table · frequency pass, then dummy-node removal*

| # | Problem | Diff | Status | Why similar |
|---|---------|------|--------|-------------|
| 203 | [Remove Linked List Elements](https://leetcode.com/problems/remove-linked-list-elements/) | Easy | ☐ | The dummy-node removal loop with nothing else around it — one target value, no counting pass. Good for isolating the "don't advance after a delete" rule |
| 83 | [Remove Duplicates from Sorted List](https://leetcode.com/problems/remove-duplicates-from-sorted-list/) | Easy | ☐ | Inverts the keep-rule: keep **one** copy instead of zero. Sorted input, so adjacency replaces the frequency map — direct contrast with 1836 |
| 1171 | [Remove Zero Sum Consecutive Nodes](https://leetcode.com/problems/remove-zero-sum-consecutive-nodes-from-linked-list/) | Medium | ☐ | Same two-pass shape — build a map, then splice with a dummy node — but keyed on running prefix sums, so the map stores nodes rather than counts |
| 1019 | [Next Greater Node In Linked List](https://leetcode.com/problems/next-greater-node-in-linked-list/) | Medium | ☐ | Also needs full knowledge of the list before deciding anything about a node, but resolves it with a monotonic stack instead of a frequency table |

*Filtered as already solved: 82 (Remove Duplicates from Sorted List II), 2487 (Remove Nodes From Linked List), 19 (Remove Nth Node From End of List).*
