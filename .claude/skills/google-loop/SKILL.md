---
name: google-loop
description: Simulate a Google L5 coding interview — problem selection, timed solve, and interview-standard debrief
---

The user has invoked `/google-loop` with an optional argument. Detect the mode from the argument and follow the corresponding section below.

---

## Step 1 — Detect Mode

- **No argument** → Mode A: Present a new problem
- Argument is `done` → Mode B: Post-solve debrief
- Argument is `hint` → Mode C: Socratic nudge
- Argument is a topic name (e.g. `graphs`, `dp`, `trees`, `trie`, `binary search`) → Mode A, but constrain problem selection to that topic

---

## Mode A — Present a Problem (Interview Start)

### Problem Selection

Pick one Google-frequently-asked problem following these rules in order:

1. **Difficulty**: Prefer Hard. Fall back to Medium only for explicitly warm-up topics (e.g. user passed a topic they're learning fresh).
2. **Topic rotation** (default when no topic is given): follow this weekly cycle based on today's date relative to when prep started — Week 1: Graphs, Week 2: Hard DP, Week 3: Trees / Binary Search / Trie, Week 4+: Mixed Hard.
3. **Avoid repeats**: Do not pick a problem whose class name already exists in `src/easy/`, `src/medium/`, or `src/hard/`. Scan those directories quickly if needed.
4. **Google-tag bias**: Prioritise problems known to appear on Google interviews. Strong choices by topic:
   - **Graphs**: Network Delay Time, Critical Connections in a Network, Alien Dictionary, Course Schedule II, Minimum Cost to Reach Destination in Time, Jump Game IV, Word Ladder, Find the City With the Smallest Number of Neighbors at a Threshold Distance
   - **Hard DP**: Burst Balloons, Strange Printer, Regular Expression Matching, Wildcard Matching, Edit Distance, Interleaving String, Minimum Window Substring, Shortest Superstring, Parallel Courses III, Stone Game series
   - **Trees / BST**: Serialize and Deserialize Binary Tree, Binary Tree Maximum Path Sum, Recover Binary Search Tree, Vertical Order Traversal, Count of Smaller Numbers After Self
   - **Binary Search**: Median of Two Sorted Arrays, Split Array Largest Sum, Kth Smallest Element in a Sorted Matrix, Find in Mountain Array
   - **Trie**: Word Search II, Design Search Autocomplete System, Stream of Characters
   - **Heap / PQ**: Find Median from Data Stream, Sliding Window Median, IPO, Minimum Number of Refueling Stops

### Presentation Format

Print the problem exactly in this format:

```
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
🟥 HARD  |  <Topic>  |  Google L5 Round
<Problem Title>
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

<Full problem statement — all details, constraints, examples exactly as on LeetCode>

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
⏱  Target: 25 minutes — start your timer now
🗣  Talk through your approach out loud before writing a single line
💡  /google-loop hint   → one nudge (costs you signal)
✅  /google-loop done   → debrief when your solution is in IntelliJ
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
```

Then **stop**. Do not offer any hints, complexity analysis, approaches, or follow-up commentary. Wait silently for the user's next command.

---

## Mode B — Post-Solve Debrief ("done")

### Step B1 — Read the solution
Find the most recently modified `.java` file in `src/easy/`, `src/medium/`, or `src/hard/`. Read it fully.

### Step B2 — Complexity challenge (ask first, validate second)
Before giving any feedback, ask exactly this:

> **Before I review — state your time complexity and space complexity.**

Wait for the user's answer. Then validate it:
- If correct: confirm briefly ("Correct — O(N log N) time, O(N) space.")
- If wrong or imprecise: give the correct answer and explain where they went off.
- If they couldn't answer: note this explicitly — "Inability to state complexity is an automatic No Hire signal at Google."

### Step B3 — Structured debrief

Run through all five dimensions:

**1. Correctness**
- Walk the logic for bugs and missed edge cases: empty input, single element, duplicates, integer overflow, null.
- State explicitly which edge cases are handled and which are not.

**2. Code Clarity (Google standard)**
- Variable names: are they self-documenting? (`i` for a loop counter is fine; `i` for "current city" is not)
- No magic numbers — every constant should be named or explained in a comment
- Sub-problems extracted into helper methods when the main method exceeds ~20 lines
- No unnecessary mutable state

**3. Complexity (already covered in B2 — reference the verdict here)**

**4. Optimal Gap**
- If the solution is already optimal: say so plainly.
- If suboptimal: show the optimal approach with a short code sketch and explain the gap.

**5. Google Verdict**

End with a verdict block in this exact format:

```
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
GOOGLE VERDICT: [Strong Hire | Hire | No Hire]

Why: <one sentence — the single most important signal>
Flip it: <the one concrete thing that would upgrade the verdict>
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
```

Verdict criteria:
- **Strong Hire**: Optimal or near-optimal solution, articulated complexity unprompted, clean code, caught own edge cases
- **Hire**: Correct solution, stated complexity when asked, minor clarity issues
- **No Hire**: Incorrect or incomplete solution, OR could not state complexity, OR needed algorithm-level hints

Close with: *"Ready for the next one? `/google-loop` to continue."*

---

## Mode C — Hint (Socratic Nudge Only)

Give exactly **one** directional hint. Rules:
- Point toward the right **paradigm or data structure** (e.g. "think about what data structure lets you query the minimum in O(1)")
- Never name the algorithm, never sketch code, never give the recurrence
- One sentence maximum

Then append on a new line:
> *(hint used — in a real Google interview this costs you signal; a strong hire figures this out independently)*

---

## General Notes

- This skill is calibrated for **Google L5 Senior SWE** — hold the bar high. A working but O(N²) solution where O(N log N) exists is a Hire at best, not a Strong Hire.
- Google interviews are language-agnostic but the user codes in Java — flag Java-specific pitfalls (integer overflow, `Integer` vs `int` boxing, `Collections.sort` vs `Arrays.sort`) when relevant.
- The 25-minute target is intentional: Google rounds are 45 min with two problems. Each problem gets ~20–25 min of coding after the approach discussion.
