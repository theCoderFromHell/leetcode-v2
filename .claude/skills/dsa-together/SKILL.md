---
name: dsa-together
description: Collaborative DSA problem-solving session — fetches the LeetCode problem, scaffolds the Java file, determines solve status from the repo, then reviews, debugs, or guides accordingly
---

The user has invoked `/dsa-together` with a LeetCode problem number or name. Work through the following steps in order.

---

## Step 1 — Identify the Problem

Parse the argument to extract the problem number or name, then look it up with the **LeetCode MCP server** (configured in the repo's `.mcp.json`, so every machine has it). Its tools may be deferred — load them with ToolSearch (`select:mcp__leetcode__get_problem,mcp__leetcode__search_problems`) first.

- **`mcp__leetcode__get_problem(titleSlug)`** returns everything this step needs in one call:
  - `title`, `questionId`
  - `difficulty` (Easy / Medium / Hard): this decides the package and directory
  - `content`: the full statement, including constraints and examples
  - `topicTags`, e.g. BFS, DP, Sliding Window, Two Pointers
  - `codeSnippets`: take the entry with `langSlug: "java"` for the **verbatim method signature**
- **Number only, slug unknown?** Use `mcp__leetcode__search_problems` with `searchKeywords` set to the number or title words, and match on the returned `questionId`/title.
- **Keep `hints` and `similarQuestions` to yourself.** The response includes them, and they are spoilers. Use `hints` only as the "smallest useful hint" in Step 3c when the user is stuck. Never paste them unprompted.
- **Premium problems** return `content: null` and empty `codeSnippets` (272 and 2764 are two). The MCP server runs anonymously: `.mcp.json` passes it no session. The difficulty, tags and `exampleTestcases` are still reliable. **Read the statement and Java signature from the user's Chrome**, where they're logged in to LeetCode Premium. This was the user's choice (2026-10-29), over putting a session cookie in the MCP config. See *Premium problems — read them through Chrome* below.
- **Never WebFetch leetcode.com itself**, which returns 403 behind auth. Use WebSearch only if Chrome isn't available, or if the MCP server is down.

### Premium problems — read them through Chrome

The browser carries the user's session, so LeetCode's own GraphQL returns the full premium statement and code templates from inside the page. This is **read-only**: never click Run or Submit, and never change anything on the page.

1. Load the `claude-in-chrome` skill. Then load the tools in **one** ToolSearch call: `select:mcp__claude-in-chrome__tabs_context_mcp,mcp__claude-in-chrome__navigate,mcp__claude-in-chrome__javascript_tool,mcp__claude-in-chrome__tabs_close_mcp`.
2. Call `tabs_context_mcp(createIfEmpty: true)`, then navigate that tab to `https://leetcode.com/problems/<slug>/`. The page has to be open: the request below is same-origin and needs its `csrftoken` cookie.
3. Run this in `javascript_tool`, with the slug filled in. Use `var`, not `const`: the page keeps variables between calls, and redeclaring a `const` throws.

   ```js
   var csrf = (document.cookie.match(/csrftoken=([^;]+)/) || [])[1] || '';
   var r = await fetch('/graphql/', {method: 'POST',
       headers: {'Content-Type': 'application/json', 'x-csrftoken': csrf},
       body: JSON.stringify({query: 'query q($s:String!){question(titleSlug:$s){content codeSnippets{langSlug code}}}',
                             variables: {s: '<slug>'}})}).then(x => x.json());
   var q = r.data.question;
   var text = q.content.replace(/<[^>]+>/g, '').replace(/&nbsp;/g, ' ').replace(/&quot;/g, '"')
       .replace(/&lt;/g, '<').replace(/&gt;/g, '>').replace(/&#39;/g, "'").replace(/\n{2,}/g, '\n');
   'JAVA:\n' + q.codeSnippets.find(c => c.langSlug === 'java').code + '\n---\n' + text
   ```

4. **The result is cut off at roughly 1,000 characters** (it ends in `[TRUNCATED]`). Page through it by re-running only the last line with `text.slice(...)`, e.g. `text.slice(text.indexOf('Example 1'))`, then `text.slice(text.indexOf('Constraints'))`. The variables are still set from step 3. If `text` comes back undefined, re-run the whole of step 3 with the slice on its last line.
5. Stripping the tags loses `<sup>`, so a constraint printed as `105` means 10⁵, and `231` means 2³¹.
6. Close the tab with `tabs_close_mcp` as soon as you have the text.

Take only the statement, the examples, the constraints and the Java template. Don't open the hints, the editorial or the solutions tabs. They're spoilers, as in the rule above.

**If Chrome isn't connected, or the page shows the paywall** (the user isn't logged in), say so plainly. Then fall back to WebSearch for the statement. For the signature, translate a public mirror's code (doocs, walkccc) into Java, and say the signature is unverified. Mirror pages carry full solutions, so take only the statement and signature from them.

### Deriving the URL slug

Lowercase the title, replace spaces with hyphens, drop characters that are not alphanumeric or hyphen. Roman numerals lowercase along with everything else.

| Title | Slug |
|---|---|
| Find Smallest Common Element in All Rows | `find-smallest-common-element-in-all-rows` |
| Course Schedule II | `course-schedule-ii` |
| K-th Smallest Prime Fraction | `k-th-smallest-prime-fraction` |

Final URL: `https://leetcode.com/problems/<slug>/`

Confirm the slug resolves: a successful `get_problem` call is the confirmation. If it errors or returns no problem, the slug is wrong; find the canonical one with `search_problems` and use that.

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

        // Test 1: LeetCode example 1
        System.out.println("Test 1: " + F.smallestCommonElement(new int[][]{{1, 2, 3, 4, 5}, {2, 4, 5, 8, 10}, {3, 5, 7, 9, 11}, {1, 3, 5, 7, 9}}) + " (Expected: 5)");

        // Test 2: LeetCode example 2
        System.out.println("Test 2: " + F.smallestCommonElement(new int[][]{{1, 2, 3}, {2, 3, 4}, {2, 3, 5}}) + " (Expected: 2)");

        // Test 3: no element common to every row
        System.out.println("Test 3: " + F.smallestCommonElement(new int[][]{{1, 2}, {3, 4}}) + " (Expected: -1)");

        // Test 4: single row
        System.out.println("Test 4: " + F.smallestCommonElement(new int[][]{{4, 7, 9}}) + " (Expected: 4)");
    }
}
```

Rules for the scaffold:
- Package line matches the directory.
- Problem URL as a `//` comment on the line directly above `public class` — this is the project convention.
- Method signature copied **verbatim** from LeetCode's Java template, with an **empty body**. Do not add a placeholder `return`, and do not attempt an implementation — the empty body is intentional and mirrors what LeetCode gives. It will not compile until the user fills it in; that is expected.
- If the problem needs `ListNode`, `TreeNode`, `Node`, or `DoubleListNode`, add `import common.<Type>;`.
- `main()` declares the instance variable named using the first letter of the class (`F` for `FindSmallestCommonElementInAllRows`), followed by the **test cases, written upfront**. See the next section.
- Do **not** commit the scaffold. The user commits once the solution works.

### Test cases go in upfront

The user's rule (2026-10-20): write the test cases into `main()` **at scaffold time**, so the moment their solution is ready they can run the file and check every result against its expected value. The method body still stays empty. The file won't compile until the user fills it in, exactly as before.

**Which tests:**
- **Tests 1–N are the LeetCode examples**, in order, and their outputs are authoritative. `get_problem`'s `exampleTestcases` field holds the example inputs one argument per line. It is filled in even for premium problems, where `content` is null and the outputs have to be computed.
- **Then add 2–4 tests for traps**, picked from the constraints: the smallest legal input (one element, k == n, …), duplicates, ties, boundary values, and overflow scale when sums or products can pass 2³¹ − 1. Add a large input only when it guards something real (overflow at scale, an O(n²) that would time out).

**No spoilers.** The user reads these tests before they've thought about the problem.
- Comment each extra test with the **input shape only**: `// Test 4: even length`, `// Test 5: all values negative`, `// Test 6: k == n`.
- Never write the reason it's tricky, the bug it catches, or the technique that handles it. For example, "even length — the centre is the LOWER median" gives the gotcha away.

**Every expected value must be right.** A wrong expected value sends the user debugging correct code.
- Example outputs come straight from the statement.
- For any other test, compute the expected value with a quick brute force in the scratchpad, run it, then delete it. Never put the brute force in the solution file, and never show it to the user. Don't hand-derive expected values; that is where wrong tests come from.
- If no brute force is practical, use inputs small enough to check by hand, and check them twice.

**Format.** Use the `CLAUDE.md` line, `System.out.println("Test N: " + X.method(args) + " (Expected: Y)");`, plus:
- **Arrays:** wrap `int[]` / `long[]` in `Arrays.toString(...)` and `int[][]` in `Arrays.deepToString(...)`, with `import java.util.Arrays;`.
- **"Return the answer in any order":** sort the result before printing, e.g. `Arrays.toString(Arrays.stream(r).sorted().toArray())`, and write the expected value sorted. Otherwise a correct answer in a different order looks wrong.
- **`ListNode` input:** use `ListNode.createList(new int[]{...})`.
- **Tree input or output:** use `TreeNode.createTree(4, 2, 6, null, 3)` (LeetCode level-order, `null` = missing child) and `TreeNode.serialize(root)` from `common`. Don't write a local `build`/`show` for trees.
- **List output:** add a small `private static` test helper below the method, such as `show(ListNode)` (see `ReverseNodesInEvenLengthGroups`). Keep any helper to plumbing for a result shape `common` doesn't cover (e.g. a pair of trees), never solution logic.
- **Design problems:** replay each example's call sequence on one fresh instance, and print every call that returns a value as `Test N.M`.

**At review time** (`/dsa-review`, or Step 4 after a solve), keep these tests rather than rewriting them:
- Expand the comments with the reasons.
- Add a test for each bug that came up while solving.
- Verify everything against a brute force on randomised input, as `CLAUDE.md` requires.

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

        // Test 1: LeetCode example 1, replayed call by call on this instance
        // Test 1.1, 1.2, ...: one println per call that returns a value
    }
}
```

The `main()` instance variable follows the first-letter rule based on the **inner** class name (`OrderManagementSystem O = ...`), since that is the object under test.

After creating it, tell the user the path in one line, then move on:
> Created `src/medium/FindSmallestCommonElementInAllRows.java` — Medium, tagged Binary Search / Hash Table, with 4 test cases ready in `main()`.

**Open the file in IntelliJ yourself — do not print a path and hope it is clickable.** Run:

```bash
"/Applications/IntelliJ IDEA.app/Contents/MacOS/idea" <absolute path to the scaffolded file>
```

The user runs Terminal.app, which has **no file-path handler at all**: a bare path is inert text, and even web URLs need Cmd+double-click. So open the file for them.

**Use the launcher binary inside the app bundle, NOT `open -a "IntelliJ IDEA"`.** `idea` is not on their PATH and there is no Toolbox scripts directory, but the binary is always at `/Applications/IntelliJ IDEA.app/Contents/MacOS/idea`. The difference matters when several IntelliJ windows are open:

- `open -a` hands the file to macOS, which just activates the app — IntelliJ then drops the file into whichever window was last focused. **Confirmed to open in the wrong project.**
- The launcher binary goes through IntelliJ's own CLI handler, which resolves the project whose content root contains the file, and opens it there.

Note the IntelliJ project root is `<repo root>/src`, not the repo root itself (the module file lives at `src/leetcode-v2.iml`). Passing the absolute file path is enough — the CLI resolves the owning project on its own.

The command may not return promptly; run it in the background or move on after a short wait rather than blocking on it.

Then print **both** the problem URL and the absolute file path, each on its own line, as text references visible in scrollback:

```
https://leetcode.com/problems/find-smallest-common-element-in-all-rows/
<repo root>/src/medium/FindSmallestCommonElementInAllRows.java
```

**Always print the problem link — every time, in every mode.** Not only when scaffolding a new
file: also when the file already existed, when the status is SOLVED or ATTEMPTED, and when
re-entering a problem already in progress. The user opens the LeetCode page to read the full
statement and to submit, so the link is needed on every invocation, not just the first.

**Derive `<repo root>` from the current working directory — never hardcode it.** The user works across five machines and the repo has already moved directories once; a literal path baked into this file would be wrong everywhere except the one machine it was written on. The user codes in IntelliJ, never in the terminal, so their next action after scaffolding is always "open this file" — so just open it.

**Never commit or push the scaffold, or the finished solution, unless the user explicitly asks in that message.** "Yes go ahead" in reply to an offer to add URL / revision note / test cases authorises the annotations only, never git. The user batches several solutions per commit and controls when a branch is pushed.

---

## Step 2 — Determine Solve Status

Classify into one of three states:

- **SOLVED** — a working solution already exists
- **ATTEMPTED** — tried it, not accepted
- **NEVER ATTEMPTED** — fresh problem

> **The user's rule (2026-10-17): invoking `/dsa-together` means the problem is unsolved — never ask "solved, attempted, or new?".** Don't look up LeetCode submission history to second-guess it either: the MCP server's `get_recent_ac_submissions` exists, but it is for `/practice`'s tracker, not for overriding the user here.

### Signal 1 — the repo (the only check)

Step 1b already established whether `src/<difficulty>/<ClassName>.java` exists.

| Repo state | Conclude |
|---|---|
| File exists, method body is **filled in** | The user's own work is already there. Read it and pick up from it: review it if it looks complete, or continue guiding from where it stands if it's a draft. Never ignore or overwrite it |
| File exists, body is **empty or a stub** | **NEVER ATTEMPTED** — collaborative mode |
| File does not exist | **NEVER ATTEMPTED** — collaborative mode |

Also check for variant files (`<ClassName>V2.java`, `V3`). A variant means the problem was solved at least once, so treat it like a filled-in file.

Do not ask a status question in any of these cases. Defaulting to NEVER ATTEMPTED is deliberately asymmetric. Guessing never-attempted when the user has actually solved it costs a little redundant guidance. Guessing SOLVED when they haven't **spoils the problem permanently** by dumping a full review with the answer in it.

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

Then finish the upfront tests from Step 1b. Keep them, expand their comments with the reasons, add a test for each bug that came up during the session, and verify them against a brute force.
