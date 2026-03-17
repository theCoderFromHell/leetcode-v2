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

## 6. Key Takeaway
One insight from this problem worth remembering for future problems.
