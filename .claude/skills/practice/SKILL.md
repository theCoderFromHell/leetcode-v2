---
name: practice
description: Find 3-5 similar unsolved problems for problems already solved, tracked with status in src/PRACTICE.md
---

The user has invoked `/practice`. This skill finds problems that drill the *same concept* as
something they have already solved, and tracks them in a committed file so progress survives
across machines.

Two rules govern everything below:

1. **Never suggest a problem the user has already solved.** This is the skill's core failure
   mode. The solved check in Step 4 is not optional.
2. **Never re-derive a source problem that is already in the tracker.** The mapping
   "problem X → similar problems" is stable; recomputing it burns tokens for nothing.

---

## Step 1 — Detect Mode

| Argument | Mode |
|---|---|
| *(none)* | **A** — Refresh statuses, suggest one problem to do next |
| `list` | **B** — Refresh statuses, show the full pending backlog |
| One or more problem names/numbers | **C** — Derive suggestions for each |

The tracker lives at `src/PRACTICE.md`. If it does not exist yet, create it with the header
from Step 6 and no sections.

---

## Step 2 — The Solved Check

Every mode depends on this. Get it right before anything else.

> **Do not use the `// https://leetcode.com/problems/...` comment to decide this.** Only ~11%
> of solution files carry one — it is a convention adopted in April 2026, not a legacy one.
> Slug-based detection reports roughly 660 solved problems as unsolved.

**The filename is the only signal with full coverage.** `CLAUDE.md` mandates PascalCase
filenames matching the LeetCode title, and `src/common/RemoveSpacesFromLeetcodeQuestionName.java`
is the canonical transform.

### 2a. Primary probe

Transform the candidate title to PascalCase, then:

```bash
find src/easy src/medium src/hard src/contests \
     \( -iname '<ClassName>.java' -o -iname '<ClassName>V[0-9].java' \) -print
```

- `-iname` absorbs casing drift; the explicit `V[0-9]` arm catches variants.
- **Do not use a bare trailing `*`.** `RemoveDuplicatesFromSortedList*` also matches
  `RemoveDuplicatesFromSortedListII.java`, so a Roman-numeral sequel makes its own prefix
  look solved. Match exactly.
- Listing the four directories explicitly keeps `src/out/` (IntelliJ's build mirror) out.

**Search scope:** `src/easy`, `src/medium`, `src/hard`, `src/contests` only. Exclude
`src/interviews` (company paraphrases, not LeetCode titles), `src/syllabus`, `src/random`,
`src/common`, `src/out`.

Batch the whole candidate list into **one** shell loop rather than one call per candidate.

### 2b. A file is not proof of a solve

Empty scaffolds exist (`src/hard/FindKThSmallestPairDistance.java` is one). Confirm a method
body before calling it solved:

```bash
grep -qE '(public|private|protected).*\(.*\).*\{' <file>
```

No method declaration → treat as **unsolved**.

### 2c. When the repo says "not found", decide whether to trust it

The repo check errs toward *under*-reporting. Three known false-negative sources:

| Situation | Action |
|---|---|
| Title starts with **"Design"** | ~35 files are named after the API class, not the title ("Design Phone Directory" → `PhoneDirectory.java`; also `Codec`, `LFUCache`, `Trie`, `MedianFinder`, `TimeMap`, `Twitter`, `RandomizedSet`, `StockSpanner`, `BrowserHistory`). **Probe the bare class name too** before concluding unsolved. |
| Title contains a **digit, hyphen or parenthesis** | The transform is lossy: `3Sum Closest → ThreeSumClosest`, `K-th Symbol in Grammar → KthSymbolInGrammar`, `Pow(x, n) → PowXN`, `132 Pattern → OneThreeTwoPattern` (but the file is actually `The132Pattern.java`). **Verify via chrome.** |
| Neither of the above | Repo says unsolved — but see the blind spot below before trusting it. |

### 2c-bis. The repo's structural blind spot

**The repo holds ~751 problems; the LeetCode profile shows ~785 solved.** Roughly 34 problems
are solved on LeetCode but were never committed here. The repo check cannot see any of them —
no filename, no slug, nothing to match. This is not naming drift; the file simply does not exist.

Confirmed cases, all retracted after the user said "already solved": **438** (Find All Anagrams
in a String), **134** (Gas Station), **45** (Jump Game II), **435** (Non-overlapping Intervals).

Note the cluster: three of those four came from a single greedy / interval-scheduling set. The
blind spot is NOT evenly distributed — it concentrates in Top-150 and Blind-75 territory, so
whole concept areas can be far more picked-over than the repo suggests.

So a clean repo miss means *"not in the repo"*, never *"not solved"*. Three consequences:

- Prefer candidates whose concept is niche enough that an uncommitted solve is unlikely. Very
  famous problems (Top-150 / Blind-75 staples) are the highest-risk suggestions; contest-era
  problems are the safest.
- **Say so when reporting.** If the candidate set was verified against the repo only, state that
  in one line rather than implying certainty. Offer the chrome check.
- **If two or more rows in one set get retracted, stop trusting the repo for that concept.** Say
  so plainly and recommend the chrome check instead of retracting a third row.

### 2c-ter. Backfill when a retraction happens

The user's standing decision (2026-10-04): **backfill opportunistically, not in bulk.** A
problem only matters for this purpose once it collides with a suggestion, so there is no project
to import all ~34 at once.

So when the user says *"<N> is already solved"*:

1. Drop the row, move it into the section's *Filtered as already solved* line, and note there
   that it was solved on LeetCode but not committed. Backfill to keep 3-5 candidates.
2. **Offer to create the missing file**, in one line. If the user pastes their accepted code:
   - verify the difficulty against the problem page -> `src/easy|medium|hard/`
   - PascalCase class name per `CLAUDE.md`; confirm nothing exists at that path already
   - package line, `import common.*` if needed, URL comment above the class, their code verbatim
   - add a revision note written from reading their code, plus `main()` with test cases
   - compile and run; report what it actually prints
   - **OPEN IT IN INTELLIJ**, exactly as `/dsa-together` does on a fresh scaffold:

     ```bash
     "/Applications/IntelliJ IDEA.app/Contents/MacOS/idea" <absolute path to the file>
     ```

     Use the launcher binary, never `open -a "IntelliJ IDEA"` - `open -a` lets macOS pick the
     window and drops the file into whichever project was last focused. Run it in the background;
     it may not return promptly. The user codes in IntelliJ and never in the terminal, so a bare
     path in Terminal.app is inert text - a backfilled file is just as much "a file they want to
     look at" as a new scaffold is.
   - commit and push in the same turn - pasting solutions for this purpose authorises it
3. A failing test on already-accepted code is far more likely a wrong EXPECTED value than a bug.
   Report the run honestly and never edit their solution to match an expectation.

This is the only path that shrinks the blind spot, so take it whenever a retraction happens.

### 2d. Chrome fallback

When 2c says verify, check the user's LeetCode profile — username **theCoderFromHell**.

- Load the `claude-in-chrome` skill **before** any `mcp__claude-in-chrome__*` call.
- **Batch every uncertain candidate into one browser session.** Never open a session per candidate.
- Do not try `WebFetch` on leetcode.com — it returns 403 behind auth.
- If chrome is unavailable, say so plainly and mark the row `☐ ?` rather than guessing.

---

## Step 3 — Mode C: Derive Suggestions

### 3a. Check the cache first

Read `src/PRACTICE.md`. If a source problem already has a `##` section, **print it from cache
and skip derivation entirely**:

> *1836 is already mapped — showing cached suggestions.*

Only derive for source problems with no section.

### 3b. Identify the concept, not the tags

Look up each source problem (title, number, difficulty, topic tags). Then name the *technique*
being practised. Topic tags alone are too coarse to find genuinely similar problems:

- Weak: *"Linked List, Hash Table"*
- Strong: *"frequency pass + dummy-node removal"*

### 3c. Propose 3–5 candidates

**Medium and Hard only. Never suggest an Easy problem** — the user has ~751 solves and Easy
problems are almost always subsumed by what they have already done. If a concept only has Easy
analogues, return fewer candidates rather than padding with them.

**Verify each difficulty — do not assert it from memory.** Recalled difficulty is unreliable:
922 (Sort Array By Parity II) was suggested as Medium and is actually Easy. Batch the whole
candidate list into one WebSearch and confirm before writing any row. This costs one search and
prevents a banned suggestion reaching the user.

Each candidate must:
- Drill the **same core technique** as the source problem
- Come with a one-line *why similar* naming the shared technique **and what differs**
- Span a range within Medium/Hard — an easier Medium to isolate the pattern, a Hard to extend it

Prefer problems that vary one dimension (sorted vs unsorted input, one pass vs two, tree vs
graph) so the contrast teaches something.

### 3d. Filter through the solved check

Run **every** candidate through Step 2. Drop anything solved and backfill to keep 3–5.

If fewer than 3 survive, say so rather than padding with loosely-related problems.

### 3e. Write and print

Append the section to `src/PRACTICE.md` (format in Step 6), regenerate the header counts, then
print the same table to the user.

**Print every problem's URL on its own line, BELOW the table — never inside it.**

The user runs Terminal.app, where a URL opens only with Cmd+double-click, and only if it sits
unbroken on a single screen line. A URL in a table cell shares its row with a long "why similar"
sentence, so the row wraps and the URL splits across two lines — at which point Terminal.app no
longer recognises it as a URL at all, modifier or not. A `Link` column was tried and failed for
exactly this reason (2026-10-07).

So keep the table for scanning, without a link column, and list the URLs underneath, one per
line, prefixed by the problem number:

```
| # | Problem | Diff | Why similar |
|---|---------|------|-------------|
| 438 | Find All Anagrams in a String | Medium | Same count-vector signature under a sliding window |
| 567 | Permutation in String | Medium | Fixed-size window instead of all windows — same signature check |

438  https://leetcode.com/problems/find-all-anagrams-in-a-string/
567  https://leetcode.com/problems/permutation-in-string/
```

Bare URLs only: no markdown link syntax, no angle brackets, and nothing immediately after the
URL on that line, so no trailing punctuation gets swallowed into it.

This rule is about **terminal output only**. `src/PRACTICE.md` keeps its `[Title](url)` links —
that file is read in a markdown renderer (GitHub, IntelliJ preview), where they work fine.

**Do not scaffold any files.** Close with:

> *`/dsa-together <number>` to start any of these.*

---

## Step 4 — Mode A: Refresh & Suggest Next (bare `/practice`)

1. Run the solved check over every `☐` row in the tracker.
2. Flip newly-solved rows to `☑ <today's date>`, regenerate header counts, save.
3. Report the diff in one or two lines.
4. Pick **one** pending problem to do next — prefer a suggestion whose source problem was
   solved most recently, so the concept is still fresh.

Print in this format:

```
Refreshed: 2 newly solved
  ✓ 82.  Remove Duplicates from Sorted List II
  ✓ 508. Most Frequent Subtree Sum

Next up:
  1171. Remove Zero Sum Consecutive Nodes from Linked List   Medium
        Dummy node + prefix-sum map over a list — same removal
        pattern as 1836, harder bookkeeping.

  https://leetcode.com/problems/remove-zero-sum-consecutive-nodes-from-linked-list/

  /dsa-together 1171 to start
```

The URL goes on its own line for the same reason as Step 3e — it must never share a line with
wrapped prose, or Terminal.app cannot open it.

If nothing is pending, say so and suggest running `/practice <a recently solved problem>`.

---

## Step 5 — Mode B: Full Backlog (`/practice list`)

Refresh statuses exactly as in Mode A, then print every pending row grouped by source problem.
Include the counts. No "next up" pick — this mode is for scanning.

Same link rule as Step 3e: tables with no link column, then each group's URLs one per line
beneath that group's table, prefixed by problem number.

---

## Step 6 — Tracker Format

`src/PRACTICE.md`:

```markdown
# Practice Tracker

Similar-problem suggestions from `/practice`. Status refreshes on each run.

**Pending 7 · Solved 2 · Total 9** — updated 2026-09-27

---

## 1836. Remove Duplicates From an Unsorted Linked List
*Linked List · Hash Table · frequency pass + dummy-node removal*

| # | Problem | Diff | Status | Why similar |
|---|---------|------|--------|-------------|
| 82 | [Remove Duplicates from Sorted List II](https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii/) | Medium | ☐ | Same delete-all-copies rule; sorted input removes the counting pass, isolating the dummy-node pattern |
| 1171 | [Remove Zero Sum Consecutive Nodes](https://leetcode.com/problems/remove-zero-sum-consecutive-nodes-from-linked-list/) | Medium | ☑ 2026-09-28 | Dummy node + HashMap over a list, harder bookkeeping |
```

- Status is `☐` pending, `☑ YYYY-MM-DD` solved, `☐ ?` unverified (chrome unavailable).
- Header counts are regenerated on **every** write.
- Newest sections go at the top, under the header.
- The italic line under each heading is the **concept**, not a tag dump.

---

## Guardrails

- **Never scaffold a `.java` file here.** Only `/dsa-together` does that. If the user asks for
  one, hand off: `/dsa-together <number>`.
- **Never commit or push unless the user asks in that message.** They batch commits themselves.
- **Never suggest a problem without running the Step 2 solved check.**
- **Never re-derive a source problem that already has a section.**
- When the user says "I solved X from the list", flip that row and save — don't wait for a
  bare `/practice` run.
