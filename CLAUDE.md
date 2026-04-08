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
└── random/        # Miscellaneous Java experiments
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
- **Each file contains:** problem URL comment → solution method → helper methods → `main()` with test cases
- **Instance variable in `main()`:** named with the first letter of the class (e.g., `FindUniqueBinaryString F = new FindUniqueBinaryString()`)
- **Packages:** match folder names (`easy`, `medium`, `hard`, `common`, etc.)

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

## Code Review Checklist

When reviewing any solution always:
1. **Correctness** — check for bugs and unhandled edge cases (empty input, single element, duplicates, integer overflow, null)
2. **Complexity** — state Big-O time and space with justification; trace through an example if non-obvious
3. **Alternatives** — suggest at least one different approach (different paradigm or data structure)
4. **Java quality** — naming, readability, use of standard collections, modern Java features where appropriate
