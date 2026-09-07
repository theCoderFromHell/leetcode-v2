# Plan: Google L5 Interview Prep + /google-loop Skill

## Context
Google L5 interview in ~30 days. User has strong Medium coverage (700) but critical gaps in Hard problems (50) and low contest exposure (5). System Design is covered separately via hellointerviews.com. This plan addresses the coding rounds gap and creates a `/google-loop` skill to simulate Google interview conditions during daily practice.

---

## Gap Analysis (from LeetCode stats)

| Metric | Current | Target for L5 | Gap |
|---|---|---|---|
| Hard solved | 50 (5%) | ~100+ | 50+ more needed |
| Easy solved | 62 (6%) | ~150+ | Fast recall matters at start of interview |
| Contest rating | 1,597 | 1,800+ | Time pressure practice needed |
| Contests attended | 5 | — | Low interview-simulation experience |
| Active days (past year) | 60 | Daily for 30 days | Consistency |

**Topic gaps (inferred from visible skills):**
- DP: x147 strong — but almost certainly Medium-heavy; Hard DP (interval, 2D, bitmask) needs focus
- Graphs: Not visible in top skills — Google's most-tested category
- Tries: Not visible — Google text-processing problems
- Binary Search on answer: Medium/Hard variant practice needed
- Heap / Priority Queue: Likely light on Hard variants
- Sliding Window / Two Pointers: Probably fine (Medium coverage is high)

**Google L5 coding round reality:**
- 2-3 coding rounds, 45 min each
- Typically 1 Medium + 1 Hard, or 2 Hard problems per round
- Interviewer expects: talking through approach first, clean code, O(n) or O(n log n), edge cases discussed unprompted

---

## 30-Day Practice Roadmap

### Week 1 (Days 1–7): Graphs — Google's #1 topic
- BFS/DFS on matrix, adjacency list
- Dijkstra, Bellman-Ford
- Topological sort (Kahn's + DFS)
- Cycle detection (directed + undirected)
- Target: 3–4 Hard graph problems + 3–4 Medium per day
- Key Hards: Network Delay Time, Critical Connections, Alien Dictionary, Minimum Cost to Reach City With Discounts

### Week 2 (Days 8–14): Hard DP
- Interval DP (Burst Balloons, Strange Printer)
- 2D DP (Edit Distance, Longest Common Subsequence variants)
- Bitmask DP (Shortest Superstring, Parallel Courses III)
- State machine DP (Best Time to Buy and Sell Stock with Cooldown variants)
- Target: 2 Hard DP + 2 Medium DP per day

### Week 3 (Days 15–21): Trees + Binary Search + Trie
- Binary Search on answer (Koko Eating Bananas family → Hard variants)
- Segment Trees, BIT (already in syllabus — apply to Hard problems)
- Trie (Word Search II, Prefix + Search problems)
- Advanced BST (Serialize/Deserialize, LCA variants)
- Target: 2 Hard + 2 Medium per day

### Week 4 (Days 22–30): Mock Interview Sprint
- 2 timed problems per day using `/google-loop`
- 25-min hard limit per problem — no peeking
- Every other day: attempt a LeetCode Weekly Contest
- Final 2 days: review all problems solved this month, re-solve ones that took hints

**Total target: ~80 problems in 30 days (30 Hard, 50 Medium)**

---

## /google-loop Skill Design

### File location
`.claude/skills/google-loop/SKILL.md`

### Trigger
`/google-loop [optional: topic | "done" | "hint"]`

### Behavior — three modes keyed on argument

#### Mode A — No arg: Present a problem (interview start)
1. Pick a Google-frequently-asked problem. Prefer Hard for L5; fall back to Medium if topic is a warm-up area. Bias toward the current week's topic from the 30-day roadmap above (graphs → DP → trees/BS/trie → mixed).
2. Present the full problem statement (constraints, examples) formatted as it would appear in an interview whiteboard.
3. Print a reminder block:
   ```
   ⏱  Target: 25 minutes
   🗣  Talk through your approach before writing code
   💡  Type `/google-loop hint` if truly stuck (costs you signal)
   ✅  Type `/google-loop done` when your solution is in IntelliJ
   ```
4. Stay silent — do not offer hints, approaches, or commentary until prompted.

#### Mode B — "done": Post-solve Google debrief
1. Find the most recently modified `.java` file in `src/easy/`, `src/medium/`, or `src/hard/`. Read it fully.
2. Ask the user: *"Before I review — what's your time and space complexity?"* Wait for their answer.
3. Then run a structured debrief:
   - **Correctness** — bugs, edge cases (same checklist as `/dsa-review`)
   - **Complexity** — validate/correct what the user stated; penalise if they couldn't articulate it
   - **Code clarity** — Google standard: meaningful names, no magic numbers, helper methods for sub-problems, no unnecessary state
   - **Optimal gap** — if solution is suboptimal, show the optimal approach with a sketch
   - **Google Verdict** — one of: `Strong Hire` / `Hire` / `No Hire` with one-line rationale and the single thing that would flip the verdict
4. End with: *"Ready for the next one? `/google-loop` to continue."*

#### Mode C — "hint": Socratic nudge only
- Give exactly one directional hint (point toward the right paradigm, never reveal the algorithm).
- Append: *"(hint used — this would cost you signal in a real interview)"*

### Skill content outline (what goes in SKILL.md body)
- Frontmatter: `name: google-loop`, `description: Simulate a Google L5 coding interview — problem selection, timed solve, and interview-standard debrief`
- Section 1: Detect mode from args (no-arg / "done" / "hint")
- Section 2: Problem selection heuristics (Hard-first, Google-tagged, rotate topics by week)
- Section 3: Problem presentation format
- Section 4: Post-solve debrief steps (complexity challenge, correctness, clarity, verdict)
- Section 5: Hint protocol

---

## Files to Create / Modify

| Action | Path |
|---|---|
| **Create** | `.claude/skills/google-loop/SKILL.md` |
| **Update** | `/Users/theCoderFromHell/.claude/projects/.../memory/user_profile.md` — add Google L5 interview date |
| **Update** | `/Users/theCoderFromHell/.claude/projects/.../memory/project_solving_stats.md` — record gap analysis |

---

## Verification
1. Invoke `/google-loop` — should present a Hard graph problem with the timer reminder block.
2. Invoke `/google-loop hint` — should give one directional hint only.
3. Solve a problem in IntelliJ, invoke `/google-loop done` — should ask for complexity first, then debrief with verdict.
4. Confirm skill listing shows `google-loop` with correct description.
