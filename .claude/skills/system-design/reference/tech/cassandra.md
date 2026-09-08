# Cassandra

## Data Model

- **Keyspace** — database-level unit, owns the replication strategy
- **Table** — schema-defined rows
- **Primary key** = **partition key** (determines node placement via consistent hashing) + optional **clustering keys** (sort order *within* a partition)

Getting this pair right is the entire design. The partition key decides which node; the clustering key decides retrieval order.

## Write Path (LSM Tree)

1. **Commit log** — write-ahead log for durability
2. **Memtable** — in-memory sorted buffer
3. **SSTable** — immutable file after the memtable flushes
4. **Compaction** — merges SSTables, drops tombstones

Append-mostly, which is why writes are **extremely fast at scale** compared to B-tree databases.

## Tunable Consistency

Consistency level is set **per operation**. The rule that matters:

> **R + W > N** guarantees read-your-writes.

With replication factor **N=3**, requiring **QUORUM (2)** on both read and write forces at least one overlapping node between the write set and read set.

⚠️ **No ACID transactions.** Row-level atomicity within a partition, nothing more.

## When to Use

✅ High write throughput · availability-first designs · flexible/sparse schemas · **well-defined access patterns known upfront**

❌ Strict consistency · JOINs or ad-hoc queries · complex transactions

## The Discord Modeling Lesson

Discord partitioned messages by **`(channel_id, bucket)`** where bucket is a ~10-day window, clustered by `message_id`.

Partitioning by `channel_id` alone would grow unboundedly — a busy channel's partition never stops accumulating. Bucketing bounds partition size *and* matches the real query ("recent messages in this channel").

> **Model around access patterns, not entity relationships.** This is the single most transferable Cassandra lesson.

## Failure Handling

- **Hinted handoff** — the coordinator stores writes destined for a downed node and replays them on recovery
- **Phi Accrual Failure Detector** — nodes independently judge failure from gossip timing rather than a fixed timeout

## Interview Gotchas

Demonstrate **query-driven design**, not relational normalization. Discuss partition sizing explicitly — unbounded partitions are the classic Cassandra failure. State consistency concretely: "RF=3 with QUORUM reads and writes."

> Paywalled on the source page: "Numbers to Know", several case studies.
