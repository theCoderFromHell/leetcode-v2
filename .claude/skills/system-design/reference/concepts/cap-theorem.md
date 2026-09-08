# CAP Theorem

## The Statement

Pick two of three:

- **Consistency** — all nodes see the same data at the same time
- **Availability** — every request to a non-failing node gets a response
- **Partition tolerance** — the system keeps working despite network failures

**The insight that matters:** partition tolerance isn't optional in a real distributed system. Networks fail. So the actual choice is **consistency or availability during a partition**.

## The Deciding Question

> "Would it be catastrophic if users briefly saw inconsistent data?"

**Yes → choose consistency.** Ticket booking (double-bookings), inventory (overselling), financial systems (bad trades).
*Technologies:* Postgres, MySQL, Spanner, DynamoDB in strong-consistency mode.

**No → choose availability.** Social feeds, content platforms, review sites, recommendations, analytics.
*Technologies:* Cassandra, Redis clusters, DynamoDB multi-AZ.

**The default:** eventual consistency, **unless the problem involves money, inventory, or a limited bookable resource.**

## The Consistency Spectrum

It isn't binary. From strongest to weakest:

1. **Strong** — every read reflects the most recent write. Most expensive
2. **Causal** — related events appear in the same order everywhere
3. **Read-your-own-writes** — a user always sees their own updates immediately
4. **Eventual** — converges given enough time without new updates

Eventual consistency *guarantees convergence*. Weak consistency makes no such promise — don't conflate them.

## Choose Per Feature, Not Per System

This is the senior move. One system routinely needs both:

| System | Strong consistency | Availability |
|---|---|---|
| **Ticketmaster** | Bookings | Event browsing |
| **E-commerce** | Inventory, orders | Reviews, descriptions |
| **Tinder** | Matching | Profile viewing |

## PACELC

The extension worth knowing: during a **P**artition choose **A**vailability or **C**onsistency; **E**lse (normal operation) choose **L**atency or **C**onsistency.

The point: strong consistency costs you latency *even when nothing is broken*, because it requires coordination on every operation.

## Interview Application

- Raise it during **non-functional requirements**, not as a lecture
- Nobody wants the theorem explained — they want it applied
- State the choice per component with a one-line justification
- Naming different consistency needs for different features is what reads as senior
