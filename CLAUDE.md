# LeetCode DSA Repo — Claude Context

## Project
- **Language:** Java (pure, no external dependencies)
- **IDE:** IntelliJ IDEA
- **Total solved:** ~711 problems

## Structure
```
src/
├── easy/          # Easy problems
├── medium/        # Medium problems (bulk of solutions)
├── hard/          # Hard problems
├── common/        # Shared: ListNode, TreeNode, DoubleListNode, Node, Util
├── interviews/    # Company-specific (Adobe, Atlassian, Microsoft, others)
├── contests/      # Biweekly contest solutions
├── syllabus/      # Data structure implementations (Fenwick tree, Segment tree)
└── random/        # Miscellaneous
```

## Conventions
- **File naming:** CamelCase matching the LeetCode problem name (e.g., `KokoEatingBananas.java`)
- **Variants:** Suffixed with `V2`, `V3` (e.g., `TwoSumV2.java`)
- **Each file contains:** problem URL comment → solution method → helper methods → `main()` with test cases
- **Instance variable in `main()`:** named with the first letter of the class (e.g., `FindUniqueBinaryString F = new FindUniqueBinaryString()`)
- **Packages:** match folder names (`easy`, `medium`, `hard`, `common`, etc.)

## Common Utilities (src/common/)
- `ListNode` — singly linked list with `createList()`, `printList()`
- `TreeNode` — binary tree with `printTree()`
- `DoubleListNode` — doubly linked list
- `Util` — `printArray()`, `printMatrix()` (overloaded for 2D arrays and List<List<T>>)

## Git Conventions
- **Stage:** only the specific solution files (`git add src/medium/Foo.java src/medium/Bar.java`)
- **Commit message:** class names joined by ` , ` — no prefix, no description (e.g., `FindUniqueBinaryString , LongestSubarrayOf1SAfterDeletingOneElement`)
- **Push:** always to the current branch (`git push origin <current-branch>`), never to master

## Code Review Checklist
When reviewing any solution always:
1. **Correctness** — check for bugs and unhandled edge cases (empty input, single element, duplicates, integer overflow, null)
2. **Complexity** — state Big-O time and space with justification; trace through an example if non-obvious
3. **Alternatives** — suggest at least one different approach (different paradigm or data structure)
4. **Java quality** — naming, readability, use of standard collections, modern Java features where appropriate
