# Kafka

## Architecture

- **Broker** — a server storing data and serving clients
- **Topic** — logical grouping of partitions
- **Partition** — an ordered, immutable, append-only log. **The unit of parallelism and of ordering**
- **Producer / Consumer** — consumers track progress via **offsets**

Messages route by key: `partition = hash(key) % num_partitions`. Same key → same partition → **ordering guaranteed within a partition, never across them.** If a question needs global ordering, that's one partition, and you should say why that's a bottleneck.

## Numbers

- **~1M messages/sec** and **~1TB storage** per broker (hardware-dependent)
- Keep messages **under 1MB**
- Default retention: **7 days**, configurable via `retention.ms` / `retention.bytes`

**Anti-pattern:** never put large blobs in Kafka. Put the object in S3 and the **pointer** in Kafka.

## Delivery and Ordering

- **At-least-once by default** via offset commits — consumers resume from the last commit after a crash
- `acks=all` — acknowledge only once all in-sync replicas have the message
- **Idempotent producer mode** prevents duplicates from automatic retries
- Replication: each partition has a leader plus follower replicas across brokers

> **"Kafka is always available, sometimes consistent."**

## Hot Partitions

One partition taking disproportionate traffic (a viral ad campaign):
- **Random salting** — spread one logical key across partitions
- **Compound keys** — ad ID + geographic region or user segment
- **Default (round-robin) partitioning** — accept ordering loss when ordering doesn't matter
- **Backpressure** — slow producers when partition lag spikes

## Retries and DLQ

**Kafka has no built-in consumer retry logic.** The standard pattern:
1. Failed message → a dedicated retry topic
2. Separate consumer drains the retry topic
3. Persistent failures → dead letter queue

*Contrast:* AWS SQS gives you retry and DLQ out of the box. If the problem doesn't need replay or multiple consumer groups, SQS is often the simpler correct answer.

## Queue vs Stream

**As a queue** — async processing (video transcoding via S3 pointer), ordered handling (virtual waiting room), producer/consumer decoupling.

**As a stream** — realtime aggregation (ad click counting), multi-consumer pub/sub (live comments), continuous event processing.

The difference from a queue: **Kafka retains after consumption**, so you get replay and multiple independent consumer groups.

## Tuning

Batching (multiple messages per call) · compression (GZIP/Snappy/LZ4) · partition strategy for even parallelism.

> Paywalled on the source page: Flink and ZooKeeper deep-dives, several problem breakdowns.
