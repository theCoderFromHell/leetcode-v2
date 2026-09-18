---
name: dsa-review
description: Full DSA review of the most recently edited solution — correctness, complexity, and alternatives
---

Find the most recently modified `.java` file in `src/easy/`, `src/medium/`, or `src/hard/`. Read it fully, then produce a structured review:

## 1. Problem Summary
One sentence: what does this problem ask for?

## 2. Correctness
- Walk through the logic and identify any bugs
- List edge cases that could break this solution:
  - Empty / null input
  - Single element
  - Duplicates
  - Integer overflow
  - Large input (performance edge)
- State whether each edge case is handled

## 3. Complexity
- **Time:** Big-O with justification. Trace through a small example if non-obvious.
- **Space:** Big-O with justification (include recursion stack if applicable)

## 4. Alternative Approach
Show at least one meaningfully different approach (different paradigm or data structure). Include a brief code sketch and its complexity.

## 5. Code Quality (1–10)
Rate on: readability, naming, Java idioms, structure. One sentence of justification.
- Convention: instance variable in `main()` must be the first letter of the class name (e.g., `FindUniqueBinaryString F = new FindUniqueBinaryString()`). Flag if violated.
- Do NOT deduct points for missing `main()` or test cases — writing test cases is Claude's responsibility, not the user's. If missing, add them after the review.

## 6. Key Takeaway
One insight from this problem worth remembering for future problems.

## 7. File Completeness Check
Every solution file must have all three of these — add any that are missing:
- **Problem URL comment** placed just above the class definition (e.g. `// https://leetcode.com/problems/...` immediately before `public class Foo {`)
- **Revision note** as a block comment before `main()` — pattern, key insight, gotchas, template pseudocode
- **`main()` with test cases** — written by Claude, not the user

## 8. Git Commands
Print the exact commands to commit this solution:
- `git add <relative-path-to-file>`
- `git commit -m "<ClassName>"` (just the class name, no prefix or description)
- `git push origin <current-branch>` (use the actual current branch name, never master)
