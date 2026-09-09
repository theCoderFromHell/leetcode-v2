---
name: dsa-together
description: Collaborative DSA problem-solving session — fetches the LeetCode problem, scaffolds the Java file, checks solve status for theCoderFromHell, then reviews, debugs, or guides depending on history
---

The user has invoked `/dsa-together` with a LeetCode problem number or name. Work through the following steps in order.

---

## Step 1 — Identify the Problem

Parse the argument to extract the problem number or name. Use WebSearch or WebFetch to look up the problem on leetcode.com and retrieve:
- Problem title
- **Difficulty** (Easy / Medium / Hard) — needed for the package and directory
- Full problem statement (constraints, examples)
- Topic tags (e.g. BFS, DP, Sliding Window, Two Pointers)
- **The Java method signature** from LeetCode's code template (e.g. `public int smallestCommonElement(int[][] mat)`)

### Deriving the URL slug

Lowercase the title, replace spaces with hyphens, drop characters that are not alphanumeric or hyphen. Roman numerals lowercase along with everything else.

| Title | Slug |
|---|---|
| Find Smallest Common Element in All Rows | `find-smallest-common-element-in-all-rows` |
| Course Schedule II | `course-schedule-ii` |
| K-th Smallest Prime Fraction | `k-th-smallest-prime-fraction` |

Final URL: `https://leetcode.com/problems/<slug>/`

Confirm the slug resolves — if WebSearch returns a different canonical URL, use that one.

### Deriving the class name

PascalCase with all spaces and punctuation removed; Roman numerals stay uppercase. This matches `src/common/RemoveSpacesFromLeetcodeQuestionName.java` — run it if a title is ambiguous.

| Title | Class |
|---|---|
| Find Smallest Common Element in All Rows | `FindSmallestCommonElementInAllRows` |
| Course Schedule II | `CourseScheduleII` |

If difficulty cannot be determined confidently from search results, **ask the user** rather than guessing — it decides which directory the file lands in.

---

## Step 1b — Scaffold the Solution File

Create the file **before** any discussion, so the user can start coding immediately.

**Path:** `src/<difficulty>/<ClassName>.java` where `<difficulty>` is `easy`, `medium`, or `hard`.

**First check whether the file already exists.** If it does, read it and skip creation entirely — never overwrite the user's work. Say so: *"You already have `src/medium/Foo.java` — reading it."*

**Template:**

```java
package medium;

// https://leetcode.com/problems/find-smallest-common-element-in-all-rows/
public class FindSmallestCommonElementInAllRows {
    public int smallestCommonElement(int[][] mat) {

    }

    public static void main(String[] args) {
        FindSmallestCommonElementInAllRows F = new FindSmallestCommonElementInAllRows();

    }
}
```

Rules for the scaffold:
- Package line matches the directory.
- Problem URL as a `//` comment on the line directly above `public class` — this is the project convention.
- Method signature copied **verbatim** from LeetCode's Java template, with an **empty body**. Do not add a placeholder `return`, and do not attempt an implementation — the empty body is intentional and mirrors what LeetCode gives. It will not compile until the user fills it in; that is expected.
- If the problem needs `ListNode`, `TreeNode`, `Node`, or `DoubleListNode`, add `import common.<Type>;`.
- Empty `main()` with the instance variable named using the first letter of the class (`F` for `FindSmallestCommonElementInAllRows`). Test cases get added later, at review time.
- Do **not** commit the scaffold. The user commits once the solution works.

### Design problems — nested class structure

A **design problem** is one where LeetCode's template gives a named class with a constructor plus several methods, instead of a single method on the generic `Solution`. For these, the LeetCode class name (e.g. `OrderManagementSystem`) differs from the problem title (e.g. "Design Order Management System").

Use a **nested** structure so both conventions hold:

- **Outer public class** = PascalCase of the problem title, matching the filename per the normal rule. Holds `main()`.
- **Inner `static` class** = LeetCode's template class name **verbatim**. Holds the constructor and all solution methods, so it pastes straight into the LeetCode editor.

```java
package medium;

// https://leetcode.com/problems/design-order-management-system/
public class DesignOrderManagementSystem {

    static class OrderManagementSystem {

        public OrderManagementSystem() {

        }

        public void addOrder(int orderId, String orderType, int price) {

        }

        public int[] getOrdersAtPrice(String orderType, int price) {

        }
    }

    public static void main(String[] args) {
        OrderManagementSystem O = new OrderManagementSystem();

    }
}
```

The `main()` instance variable follows the first-letter rule based on the **inner** class name (`OrderManagementSystem O = ...`), since that is the object under test.

After creating it, tell the user the path in one line, then move on:
> Created `src/medium/FindSmallestCommonElementInAllRows.java` — Medium, tagged Binary Search / Hash Table.

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

1. Use the existing file found in Step 1b. If Step 1b created a fresh scaffold instead, the problem was solved on LeetCode but never committed to this repo — say so, and ask whether the user wants to re-solve it into the scaffold or paste their accepted solution.
2. Apply the full `/dsa-review` analysis:
   - Problem summary
   - Correctness (bugs, edge cases)
   - Time & space complexity with justification
   - At least one alternative approach with code sketch
   - Code quality rating (1–10)
   - Key takeaway

---

### If ATTEMPTED (submitted but not accepted)

1. Use the existing file found in Step 1b, if there was one. If only a fresh scaffold exists, ask the user to paste the attempt they submitted.
2. Re-read the problem constraints carefully.
3. Analyze the solution for bugs:
   - Walk through the logic step by step against the provided examples
   - Identify the exact failure: wrong logic, off-by-one, overflow, missed edge case, wrong data structure
   - State clearly what is broken and why, with a concrete failing example
4. Do **not** rewrite the solution — point out the issue and let the user fix it.

---

### If NEVER ATTEMPTED

Enter collaborative solving mode. The scaffold from Step 1b is already open and waiting — the user codes directly into it in IntelliJ.

**Never give the direct solution or full code.** Guide with questions and hints in an encouraging tone, as a teammate who believes the user can figure it out. Never fill in the scaffolded method body yourself.

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
