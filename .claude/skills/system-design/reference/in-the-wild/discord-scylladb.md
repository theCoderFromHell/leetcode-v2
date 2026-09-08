# Discord — Trillions of Messages on ScyllaDB

**Maps to:** Design WhatsApp/chat, message storage, any high-volume write + hot-partition question.
**Pattern:** Scaling reads, hot partitions.

## The Problem

By early 2022: **trillions of messages** across **177 Cassandra nodes**.

**Hot partitions.** Popular channels concentrated thousands of reads onto a single partition (replicated across three nodes). When one partition saturated, *"unrelated queries that also needed that node slowed down behind it"* — the failure spread laterally across the cluster.

**Operational fragility.** Compaction fell behind; uncompacted SSTables accumulated, making reads slower and consuming the very resources needed to compact. **JVM garbage-collection pauses** caused latency spikes bad enough to require node reboots.

## The Solution — Two Independent Fixes

**1. ScyllaDB.** A Cassandra-compatible reimplementation in **C++**, removing JVM GC pauses entirely. Shard-per-core architecture improved throughput and cut operational overhead.

**2. Request coalescing.** A middleware layer of **Rust data services** between the API and the database. Consistent hashing on `channel_id` routes identical requests to the same instance:

> "The first request starts the database query. Any identical request that arrives while that query is still running joins the existing task."

This is the **single-flight pattern** — the same idea as cache-stampede prevention, applied at the service layer.

## The Data Model

Partition key **`(channel_id, bucket)`**, clustering key `message_id`, where bucket is a time window.

Partitioning by `channel_id` alone grows without bound — a busy channel's partition never stops accumulating. Bucketing bounds partition size while still matching the query ("recent messages in this channel").

## Results

| Metric | Before | After |
|---|---|---|
| Nodes | 177 | **72** |
| P99 read latency | 40–125ms | **steady 15ms** |
| Manual compaction "gossip dance" | Required | Eliminated |

## The Transferable Lesson

> **Faster databases and reduced database load solve distinct problems.**

ScyllaDB made each read cheaper. Coalescing meant fewer reads happened at all. Candidates habitually reach for one and stop — naming both, and distinguishing them, is the senior move.

## Use in an Interview

For chat/message storage: bound your partitions with a time bucket, and when asked about a hot channel, propose **request coalescing** rather than only "add a cache." Coalescing helps even on a cold cache, which caching alone does not.
