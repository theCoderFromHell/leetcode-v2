# Figma — Multiplayer

**Maps to:** Design Google Docs, collaborative editing, realtime sync.
**Pattern:** Pushing realtime updates, dealing with contention.

## The Problem

Multiple designers editing one document simultaneously, with instant feedback, offline support, and **tree-structured** data — while guaranteeing every copy converges to identical state.

## What They Rejected — And Why It Matters

**Operational Transformation (OT).** Too complex. Li and Li noted that *"formal proofs are very complicated and error-prone, even for OT"* handling only text insert/delete — far simpler than a design document.

**Full CRDTs.** Over-engineered *for their situation*. CRDTs use timestamps and metadata to achieve **decentralized** ordering. Figma had a **central server**, making that machinery unnecessary.

> The reasoning is the lesson: they rejected the sophisticated answer because an assumption behind it (no central authority) didn't hold.

## The Solution — Simplified CRDT

**Core principle: last-write-wins per property, ordered by server arrival time.**

Document model:
```
Map<ObjectID, Map<Property, Value>>
```

Every property is a **register** accepting the most recent server-confirmed value. Concurrent edits to *different* properties coexist. Conflicting edits to the *same* property resolve to whichever reached the server last.

## Key Decisions

| Decision | Why |
|---|---|
| **One process per document** | Eliminates cross-server ordering entirely — all writes flow through one process |
| **Fractional positioning** | Children ordered by a 0–1 position (e.g. 0.375). Inserting averages neighbors instead of rewriting siblings |
| **Parent as a property** | Reparenting becomes a simple property write; prevents object duplication |
| **Server rejects cycles** | Parent updates creating circular references are refused server-side |
| **Optimistic UI** | Clients apply edits immediately; server confirmation resolves conflicts |

Fractional positioning is worth memorizing — it's the standard answer to "how do you reorder a list without rewriting every element?"

## The Tradeoff

> Figma swapped **a hard algorithms problem for an infrastructure problem.**

Merging edits correctly is algorithmically brutal. One process per document makes it trivial — but demands robust process lifecycle management, client redirection, and crash recovery.

**That trade is the whole story.** Naming which kind of problem you'd rather have is exactly the judgment an L5 interview probes.

## Modern Legacy

Now the industry-standard pattern. Zero and **Cloudflare Durable Objects** implement the same shape: *"a server ordering optimistic mutations with last-writer-wins per property."*

## Use in an Interview

When asked for collaborative editing, don't reflexively answer "CRDTs" or "OT." Ask whether there's a **central server** — if yes, propose server-ordered last-write-wins per property with optimistic client updates, and name the tradeoff you're accepting (single process per document, so lifecycle management becomes your problem).
