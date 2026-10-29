---
name: sync-master
description: Sync the current branch with master when the user says "rebase", "rebase on master", "pull from master" or "sync with master"
---

When asked to rebase/sync the current branch with master, always follow these steps in order:
1. `git stash`
2. `git checkout master && git pull origin master`
3. `git checkout <current-branch> && git merge master`
4. `git push origin <current-branch>`
5. `git stash pop`

It's a merge, not a real `git rebase`: the monthly branch is already pushed, so never rewrite its history.
