---
name: dsa-together
description: Collaborative DSA problem-solving session — fetches the LeetCode problem, checks solve status for theCoderFromHell, then reviews, debugs, or guides depending on history
---

The user has invoked `/dsa-together` with a LeetCode problem number or name. Work through the following steps in order.

---

## Step 1 — Identify the Problem

Parse the argument to extract the problem number or name. Use WebSearch or WebFetch to look up the problem on leetcode.com and retrieve:
- Problem title
- Difficulty
- Full problem statement (constraints, examples)
- Topic tags (e.g. BFS, DP, Sliding Window, Two Pointers)

---

## Step 2 — Check Solve Status for theCoderFromHell

The user's LeetCode username is **theCoderFromHell**.

Use WebFetch to check the user's submission history for this problem. Determine one of three states:

- **SOLVED** — has an accepted submission
- **ATTEMPTED** — has submissions but none accepted
- **NEVER ATTEMPTED** — no submissions at all

---

## Step 3 — Branch by Status

### If SOLVED

1. Look for the solution locally first: search `src/easy/`, `src/medium/`, `src/hard/` for a `.java` file matching the problem name (PascalCase). Read it if found.
2. If not found locally, note that it was solved on LeetCode but not committed to this repo.
3. Apply the full `/dsa-review` analysis:
   - Problem summary
   - Correctness (bugs, edge cases)
   - Time & space complexity with justification
   - At least one alternative approach with code sketch
   - Code quality rating (1–10)
   - Key takeaway

---

### If ATTEMPTED (submitted but not accepted)

1. Look for the solution locally in `src/easy/`, `src/medium/`, `src/hard/`. Read it if found.
2. Re-read the problem constraints carefully.
3. Analyze the solution for bugs:
   - Walk through the logic step by step against the provided examples
   - Identify the exact failure: wrong logic, off-by-one, overflow, missed edge case, wrong data structure
   - State clearly what is broken and why, with a concrete failing example
4. Do **not** rewrite the solution — point out the issue and let the user fix it.

---

### If NEVER ATTEMPTED

Enter collaborative solving mode. **Never give the direct solution or full code.** Guide with questions and hints in an encouraging tone, as a teammate who believes the user can figure it out.

Follow this flow:

#### 3a. Understand Together
Ask the user to explain the problem back in their own words. Confirm understanding before moving on.

#### 3b. Explore Examples
Walk through the provided examples together. Ask: "What do you notice about the output? What pattern do you see?"

#### 3c. Identify the Pattern
Ask the user which technique or data structure comes to mind. If they are stuck or heading in the wrong direction:
- Highlight the mismatch: "That approach works for X, but here we need Y because of [constraint]. What changes?"
- Give the smallest useful hint, not the answer.

Common patterns to probe for: sliding window, two pointers, BFS/DFS, dynamic programming (top-down vs bottom-up), binary search, monotonic stack, union-find, heap/priority queue, greedy, backtracking.

#### 3d. Guide the Implementation
Once the user picks an approach, ask them to code it step by step. After each piece:
- If correct: reinforce it — "Yes! That handles [case] cleanly."
- If there's a bug or missing edge case: ask a leading question — "What happens when the input is [edge case]?"

#### 3e. Pattern Error Callouts
If the user repeatedly makes the same type of mistake (e.g. wrong loop bounds, forgetting mod, not handling empty input, mutating state mid-loop), name the pattern explicitly:
> "I notice this is the second time we've hit an off-by-one on the window boundary — that's a common trap with sliding window. A good check: mentally simulate with a 1-element input."

---

## Step 4 — Post-Solve Revision Note (Never-Attempted path only)

After the user arrives at a working solution, write a short revision note in this format:

---
### Revision Note — [Problem Title] ([Difficulty])
**Pattern:** [e.g. BFS on implicit graph, Sliding window with frequency map]
**Key Insight:** One sentence on the core idea that unlocks the solution.
**Gotchas:** Bullet list of edge cases or traps that came up during this session.
**Template to remember:**
```
[3–8 line pseudocode or code skeleton capturing the pattern]
```
---

Keep the note short enough to be useful as a 30-second refresher before a similar problem.
