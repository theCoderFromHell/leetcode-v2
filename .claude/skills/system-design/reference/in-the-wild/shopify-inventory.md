# Shopify — Inventory Reservations

**Maps to:** Ticketmaster, seat booking, flash sales, any "limited resource" question.
**Pattern:** Dealing with contention.

## The Problem

Shopify handles **>14% of US e-commerce**. Reservations lived in **Redis** (temporary holds), permanent inventory in **MySQL**. A crash between the two writes either hid inventory (sold out while stock existed) or oversold the same unit twice.

> At Shopify's scale, rare failures become frequent disasters.

## The Failed First Attempt — Worth Knowing

They moved reservations into MySQL as **one counter row per item**:

```sql
UPDATE reservations SET available = available - 1
```

Thousands of concurrent buyers for a popular item all queued behind **a single row lock**. Throughput collapsed.

**This is exactly what most candidates propose.** Knowing it fails, and why, is the value of this case study.

## The Solution — One Row Per Reservable Unit

Instead of one counter, a **bounded pool of rows**, each representing one reservable unit. Reserving three items = select and lock three available rows, remove them from the pool, create one hold record — **all in one transaction**.

The enabling primitive is MySQL 8's:

```sql
SELECT ... FOR UPDATE SKIP LOCKED
```

> "SKIP LOCKED tells MySQL to pass over that row and keep looking for an available one."

Concurrent transactions lock *different* rows instead of queueing on the same one.

**Bounded pool with replenishment.** Rather than millions of rows per item, cap at **1,000 rows per item**, maintained by a background replenishment process. If the pool drains faster than replenishment, the request triggers an inline refill and retries.

**Three lock-level refinements** that made it work in production:
1. Made reservation query columns part of the **composite primary key**, avoiding secondary index locks
2. Switched `REPEATABLE READ` → **`READ COMMITTED`** so gap locks stopped blocking replenishment
3. **Standardized lock acquisition order** across all code paths to eliminate ordering deadlocks

## Validation

Survived **Black Friday 2025** at a peak of **$5.1 million in sales per minute**.

## Transferable Lessons

**Atomic transactions beat coordination layers.** Two operations that must stay consistent should commit in the *same* database. Redis counted well but couldn't join the ledger transaction.

**Row spreading beats hot rows.** "One seat per row" outperforms a single counter whenever you have thousands of concurrent writers. This generalizes directly to ticket booking.

**Database capabilities evolve.** `SKIP LOCKED` (MySQL 8) made this architecture possible. Designs dismissed years ago may now be practical.

**Every fix trades one moving part for another.** They swapped a Redis cluster for a replenishment process — but the new part lives *inside* the transaction boundary that matters.

## Use in an Interview

When asked to prevent double-booking, don't stop at "row lock" or "distributed lock in Redis." Say: *"A single counter row becomes a hot row under concurrency. I'd model one row per reservable unit and use SELECT … FOR UPDATE SKIP LOCKED so concurrent buyers grab different rows."* That's a Strong Hire answer.
