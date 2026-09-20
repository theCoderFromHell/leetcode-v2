# Repo relocation — `Development/code/` → `Development/interviews/`

Run once per machine. Nothing in git changes, so machines can migrate independently
and in any order — an un-migrated machine keeps working exactly as before.

Delete this file once every machine has been done.

---

## Before you start

1. **Exit any running Claude Code session in this repo.** The session writes to a
   transcript inside the directory that step 2 moves; moving it live can split the
   history. `claude --resume` afterwards will not find a session that was mid-write.
2. **Close the project in IntelliJ.** Not strictly required, but IntelliJ caches the
   old absolute path and will show a broken project until you reopen from the new one.
3. **You do NOT need to commit, stash, or push first.** `mv` preserves everything —
   tracked files, untracked files, ignored files, stashes, reflog, branch state.

---

## The migration

Paste this from any directory outside the repo. It is safe to re-run: if a machine
has already migrated, it says so and stops without touching anything.

```bash
OLD=~/Development/code/leetcode-v2
NEW=~/Development/interviews/leetcode-v2
OLD_PROJ=~/.claude/projects/-Users-theCoderFromHell-Development-code-leetcode-v2
NEW_PROJ=~/.claude/projects/-Users-theCoderFromHell-Development-interviews-leetcode-v2

if [ ! -d "$OLD" ]; then
  echo "Nothing at $OLD — already migrated, or this machine keeps the repo elsewhere."
elif [ -e "$NEW" ]; then
  echo "ABORT: $NEW already exists. Resolve by hand before re-running."
else
  mkdir -p ~/Development/interviews
  mv "$OLD" "$NEW"
  echo "repo      -> $NEW"
  if [ -d "$OLD_PROJ" ]; then
    mv "$OLD_PROJ" "$NEW_PROJ"
    echo "claude    -> $NEW_PROJ"
  else
    echo "claude    -- no project data on this machine, nothing to move"
  fi
  echo "DONE"
fi
```

---

## Why the second `mv`

Claude Code keys session history **and memory** to the repo's absolute path, mangling
`/` to `-`. Move the repo without it and Claude sees a brand-new empty project: no
`--resume`, no memory files, no history.

```
/Users/theCoderFromHell/Development/code/leetcode-v2
   -> ~/.claude/projects/-Users-theCoderFromHell-Development-code-leetcode-v2/
```

That directory holds the session transcripts, subagent logs, and `memory/`. This data
is **local to each machine** and never syncs, so every machine moves its own.

If a machine has never run Claude Code in this repo, the directory won't exist — the
script handles that and carries on.

---

## Verify

```bash
cd ~/Development/interviews/leetcode-v2
git status                                    # branch and working tree unchanged
git remote -v                                 # same remote
ls ~/.claude/projects/ | grep leetcode        # should show the -interviews- name only
claude --resume                               # prior sessions should be listed
```

In IntelliJ: **File -> Open** the new path. `src/leetcode-v2.iml` travels with the
repo, so the module config is intact.

---

## Why move rather than re-clone

A fresh `git clone` silently drops everything not committed. On the machine this was
written from that was 14 untracked solution files, `.claude/settings.local.json`
(dozens of accumulated permission rules), `src/leetcode-v2.iml` and `src/.idea/` (both
gitignored), and a year-old stash. A clone also would not save you the second `mv`,
since that is keyed to the filesystem path rather than to git.

`mv` moves everything. `git clone` moves only what was committed.

---

## If something goes wrong

The move is a rename within one filesystem, so it is fully reversible:

```bash
mv ~/Development/interviews/leetcode-v2 ~/Development/code/leetcode-v2
mv ~/.claude/projects/-Users-theCoderFromHell-Development-interviews-leetcode-v2 \
   ~/.claude/projects/-Users-theCoderFromHell-Development-code-leetcode-v2
```
