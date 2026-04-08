# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Java LeetCode solutions repository. Each problem is a standalone `.java` file with a `main` method for local testing. There is no build system or test framework — solutions are run directly via the IDE or `javac`/`java`.

## Directory Structure

- `src/easy/`, `src/medium/`, `src/hard/` — LeetCode problems organized by difficulty
- `src/contests/biweekly_NNN/` — Contest submissions grouped by contest number
- `src/interviews/<company>/` — Company-specific interview prep (adobe, atlassian, microsoft, online, others)
- `src/common/` — Shared data structures used across solutions:
  - `ListNode.java` — Singly linked list node with `createList()` and `printList()` helpers
  - `DoubleListNode.java` — Doubly linked list node
  - `TreeNode.java` — Binary tree node
  - `Node.java` — General tree/graph node
  - `Util.java` — Print helpers (`printArray`, `printMatrix`)
  - `RemoveSpacesFromLeetcodeQuestionName.java` — Utility to convert "Problem Name With Spaces" → `ProblemNameWithSpaces` (CamelCase class name)
- `src/syllabus/` — Data structure implementations (`SegmentTree`, `BinaryIndexedTree`) and design patterns
- `src/random/` — Miscellaneous Java experiments and stream examples

## Naming Convention

File names are the exact LeetCode problem title in PascalCase with no spaces or special characters. Use `RemoveSpacesFromLeetcodeQuestionName.java` to convert a problem title to the correct class/file name.

## Adding a New Solution

1. Determine the difficulty (easy/medium/hard) or category (contests, interviews).
2. Run `RemoveSpacesFromLeetcodeQuestionName` to get the PascalCase file name.
3. Create `src/<difficulty>/<ProblemName>.java` with `package <difficulty>;` at the top.
4. Import from `common` package if the problem uses `ListNode`, `TreeNode`, etc.
5. Add a `main` method with test cases for local verification.

## Running a Solution

Since there is no build tool, compile and run directly:

```bash
cd src
javac common/*.java <difficulty>/<FileName>.java
java <difficulty>.<ClassName>
```

Or open the project in IntelliJ IDEA (`.iml` file is at `src/leetcode-v2.iml`) and run the `main` method directly.
