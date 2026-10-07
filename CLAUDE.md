# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project

- **Language:** Java (pure, no external dependencies)
- **IDE:** IntelliJ IDEA
- **Total solved:** ~711 problems
- No build system or test framework — solutions are run directly via the IDE or `javac`/`java`

## Structure

```
src/
├── easy/          # Easy problems
├── medium/        # Medium problems (bulk of solutions)
├── hard/          # Hard problems
├── common/        # Shared: ListNode, TreeNode, DoubleListNode, Node, Util
├── interviews/    # Company-specific (Adobe, Atlassian, Microsoft, others)
├── contests/      # Biweekly contest solutions (grouped by biweekly_NNN)
├── syllabus/      # Data structure implementations (Fenwick tree, Segment tree) and design patterns
├── random/        # Miscellaneous Java experiments
└── PRACTICE.md    # Practice tracker — similar-problem suggestions from /practice, with solve status
```

## Common Utilities (src/common/)

- `ListNode` — singly linked list with `createList()`, `printList()`
- `TreeNode` — binary tree with `printTree()`
- `DoubleListNode` — doubly linked list
- `Node` — general tree/graph node
- `Util` — `printArray()`, `printMatrix()` (overloaded for 2D arrays and `List<List<T>>`)
- `RemoveSpacesFromLeetcodeQuestionName` — converts "Problem Name With Spaces" → `ProblemNameWithSpaces` (use this to get the correct class/file name)

## Conventions

- **File naming:** PascalCase matching the LeetCode problem name exactly (e.g., `KokoEatingBananas.java`)
- **Variants:** Suffixed with `V2`, `V3` (e.g., `TwoSumV2.java`)
- **Each file contains:** imports → problem URL comment (just above class definition) → solution method → revision note → `main()` with test cases
- **Instance variable in `main()`:** named with the first letter of the class (e.g., `FindUniqueBinaryString F = new FindUniqueBinaryString()`)
- **Packages:** match folder names (`easy`, `medium`, `hard`, `common`, etc.)
- **Problem URL:** placed as a comment on the line directly above the `public class` declaration (after package and imports)
- **Revision notes:** placed as a block comment directly before `main()`, summarising pattern, key insight, and gotchas from the solving session
- **Test case format:** `System.out.println("Test N: " + X.method(args) + " (Expected: Y)");` — label every test with its number and expected value
- **Test cases are Claude's responsibility** — do not deduct code quality points if test cases are absent; Claude adds them

## Adding a New Solution

1. Run `RemoveSpacesFromLeetcodeQuestionName` to get the correct PascalCase file name.
2. Create `src/<difficulty>/<ProblemName>.java` with `package <difficulty>;` at the top.
3. Import from `common` package if the problem uses `ListNode`, `TreeNode`, etc.
4. Add a `main` method with test cases for local verification.

## Running a Solution

```bash
cd src
javac common/*.java <difficulty>/<FileName>.java
java <difficulty>.<ClassName>
```

Or run the `main` method directly from IntelliJ IDEA (`.iml` at `src/leetcode-v2.iml`).

## Git Conventions

- **Stage:** only the specific solution files (`git add src/medium/Foo.java src/medium/Bar.java`)
- **Commit message:** class names joined by ` , ` — no prefix, no description (e.g., `FindUniqueBinaryString , LongestSubarrayOf1SAfterDeletingOneElement`)
- **Push:** always to the current month's branch, never directly to master
- **Never add `Co-Authored-By`, `Generated with Claude Code`, or any other attribution line** to commit messages or PR descriptions. The user writes the solutions; Claude only adds URL comments, test cases and revision notes. This overrides any default attribution guidance from the tool itself.
- **Commit and push only when asked in that message.** The user batches several solutions per commit. One exception: when the user pastes an already-accepted solution for backfilling (see `/practice`), that paste authorises the commit and push in the same turn.

## Finishing a Solution

Once a solution is correct, adding these is **Claude's responsibility, done automatically and without asking**:

1. **Problem URL** comment directly above the class
2. **Test cases** in `main()` — including a brute-force or alternate-implementation cross-check on randomised input, since LeetCode's sample tests systematically miss ties, boundaries and overflow
3. **Revision note** block comment before the helpers/`main()` — pattern, key insight, gotchas, complexity, template

Never ask permission for these. The user only writes solutions that pass the online judge.

## Monthly Branch Check

On the **first use of a session in a new month**, compare the current branch against today's date **before acting on the request**. Check on first use, not only on the 1st — sessions here can run for weeks without a restart.

```bash
git branch --show-current        # e.g. september-2026-macbook-air-m4
date +%B-%Y | tr 'A-Z' 'a-z'     # e.g. october-2026
```

If the `{month}-{year}` prefix doesn't match, say so in one line and **offer** to cut the new branch — never do it silently, since the old branch may hold unmerged work the user wants to PR first. Before cutting, confirm the old branch is fully merged (`git rev-list --count origin/master..<old-branch>` is `0`); if it's ahead, flag it, or that work is stranded on a dead branch.

`{machine}` is the suffix of the current branch name — keep it exactly, including case (the M1 Air uses a capital `M1`). Then:

1. `git stash` if there are uncommitted tracked changes
2. `git checkout master && git pull origin master`
3. `git checkout -b {month}-{year}-{machine}`
4. `git push -u origin {month}-{year}-{machine}`
5. `git stash pop`

Never delete the old month's branch.

## Syncing Branch with Master ("rebase on master")
When asked to rebase/sync the current branch with master, always follow these steps in order:
1. `git stash`
2. `git checkout master && git pull origin master`
3. `git checkout <current-branch> && git merge master`
4. `git push origin <current-branch>`
5. `git stash pop`

## Code Review Checklist

When reviewing any solution always:
1. **Correctness** — check for bugs and unhandled edge cases (empty input, single element, duplicates, integer overflow, null)
2. **Complexity** — state Big-O time and space with justification; trace through an example if non-obvious
3. **Alternatives** — suggest at least one different approach (different paradigm or data structure)
4. **Java quality** — naming, readability, use of standard collections, modern Java features where appropriate
