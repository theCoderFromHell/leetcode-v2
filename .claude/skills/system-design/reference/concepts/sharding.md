# Sharding

## Partitioning vs Sharding

**Partitioning** splits a large table *within one database instance*. **Sharding** distributes data across *multiple independent machines*. The distinction matters — partitioning stays on one server, sharding spreads load horizontally.

- **Horizontal** — splits rows (same columns, fewer rows)
- **Vertical** — splits columns (same rows, fewer columns)

## What Makes a Good Shard Key

1. **High cardinality** — millions of distinct values, not a boolean
2. **Even distribution** — spreads uniformly across shards
3. **Query alignment** — the common query hits a single shard

**Good:** `user_id` for user-centric apps, `order_id` for e-commerce.
**Bad:** booleans (2 shards maximum), creation timestamps (every new write lands on the newest shard).

## Distribution Strategies

| Strategy | Mechanism | Tradeoff |
|---|---|---|
| **Hash-based** ★ | `hash(key) % shard_count` | Even distribution. **The default.** Needs consistent hashing to grow |
| **Range-based** | Contiguous ranges per shard | Efficient range scans; uneven load. Good for multi-tenant (a company per range) |
| **Directory-based** | Lookup table maps key→shard | Maximum flexibility, but *every request* pays a lookup + SPOF risk. Rarely right in interviews |

## When Sharding Is Actually Justified

| Dimension | Threshold |
|---|---|
| **Storage** | ~256 TiB single-instance ceiling (Amazon Aurora) |
| **Write throughput** | Struggles above **50K writes/sec** |
| **Read throughput** | DAU × queries-per-user exceeds one instance + replicas |

> **The top interview mistake is "candidates introducing sharding before proving it's necessary."** Do the arithmetic first. A single Postgres handles tens of thousands of QPS and several TB comfortably.

## Hot Spots

**Celebrity problem** — one key takes 1000x normal traffic (Taylor Swift's shard).
**Time-based** — all new writes concentrate on the newest shard while older shards idle.

Mitigations:
- Isolate hot keys onto dedicated shards
- Compound keys: `hash(user_id + date)`
- Dynamic shard splitting (MongoDB balancer, Vitess online resharding)

## Cross-Shard Operations

"Top 10 posts globally" against 64 shards means 64 network calls, waiting on the slowest, then aggregating — 64x the work.

Minimize by: caching results (5-min TTL where eventual consistency is fine) · denormalizing related data onto the same shard · precomputing in background jobs · simply accepting the penalty for rare admin queries.

## Distributed Transactions

A single-database transaction can't span shards. **Two-phase commit is "slow and fragile."**

- **Design to avoid** — keep all of a user's data on one shard (account, history, profile)
- **Saga pattern** — sequence of steps with compensating actions on failure
- **Eventual consistency** — accept brief divergence (follower counts, denormalized fields)

## You Probably Don't Implement This

| System | Approach |
|---|---|
| Cassandra | Virtual nodes, Murmur3Partitioner (consistent hashing) |
| DynamoDB | Automatic partition split/merge, hashed partition key |
| MongoDB | Range-based chunks + automatic balancer |
| Vitess / Citus | Sharding layers over MySQL / Postgres |
| Spanner / Aurora | Built-in distributed SQL |

## Five-Step Interview Walkthrough

1. Identify the bottleneck (storage or throughput) — **with numbers**
2. Propose a shard key from query patterns
3. Pick a distribution strategy (hash-based default)
4. Name the tradeoff (cross-shard queries get expensive)
5. Plan growth (consistent hashing, resharding path)

**Model answer:** *"Most queries are user-scoped, so I'd shard by user_id using hash-based distribution with consistent hashing. This makes global queries expensive — we'd cache trending content instead of computing on-demand. Consistent hashing lets us add shards later without resharding all data."*
