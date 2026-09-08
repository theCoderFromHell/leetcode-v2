# Slack — Job Queue

**Maps to:** Design a job scheduler, notification system, async task processing.
**Pattern:** Managing long-running tasks, backpressure.

## The Problem

**1.4 billion jobs/day**, peaking at **33,000/sec** — powering message posting, notifications, billing.

Everything ran on **Redis**, which both buffered incoming work *and* held dispatch state, competing for the same RAM.

When a database slowed down, jobs accumulated until Redis hit its memory ceiling. Then:

> "The queue could neither accept new jobs nor hand out old ones" — **because dequeuing itself required free memory.**

**Root cause:** dequeue cost scaled linearly with queue length. The most congested moment — exactly when draining mattered most — was the slowest. A self-reinforcing lockup with almost no recovery path.

## The Solution — Kafka as a Durable Buffer

Slack **did not replace Redis**. They added Kafka in front and split the roles:

| Component | Role |
|---|---|
| **Kafkagate** (Go) | Web apps enqueue via HTTP POST → Kafka, instead of writing to Redis |
| **Kafka** | Disk-backed durable buffer |
| **JQRelay** (Go) | One relay per topic, drains Kafka → Redis at a controlled rate |
| **Redis** | Retained for fast dispatch and job semantics |

Flow: web app → Kafkagate → Kafka → JQRelay → Redis → workers

**Cluster config:** 16 brokers, 32 partitions per topic, replication factor 3, **two days retention**.

## Tradeoffs They Accepted

JQRelay *"waits only for the partition leader to acknowledge a write, not for replication"* — trading a small job-loss risk for lower latency. (Note this is `acks=1`, not `acks=all`.)

Rate limiting moved into **Consul configuration**, letting operators throttle inflow during a backup without a deploy.

## The Transferable Lesson

> **Separate durable buffering (disk-bounded) from fast dispatch (RAM-bounded).**
>
> "Keep the backlog somewhere that can't run out of memory, and let the fast layer hold only what's in flight."

## Use in an Interview

The insight that scores: **a queue whose dequeue path needs the same resource that's exhausted cannot self-recover.** When you propose Redis as a queue, state the memory bound and what happens when it's hit. Proposing a disk-backed buffer in front — with the fast layer holding only in-flight work — is a genuine deep-dive answer, not a textbook one.
