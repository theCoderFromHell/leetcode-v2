# Elasticsearch

A distributed search orchestration layer on top of Apache Lucene.

## Architecture

**Node types:** master (cluster coordination/metadata) · data (storage + indexing, the performance driver) · coordinating (query routing and result aggregation) · ingest (pre-index transformation) · ML.

**Shards** horizontally partition documents; **replicas** provide availability and read throughput. Each shard wraps a Lucene index made of **immutable segments**.

## How Search Actually Works

**Inverted index** — maps content (words, numbers) to the documents containing them. Turns an O(n) scan into an O(1) token lookup. This is the whole reason the technology exists; be able to say it in one sentence.

**Doc values** — columnar storage for sorting and aggregation, complementary to the inverted index.

**Segment immutability** — documents land in an in-memory buffer, batch into immutable segments, then flush to disk. Deletes are *soft* (marked, cleaned up at merge time). **An update = soft-delete + re-insert**, which is why updates are slow.

## When to Use It

✅ Complex full-text search · geospatial (`geo_point` / `geo_shape`) · real-time analytics over denormalized data · a **read-optimized replica fed by CDC** from the primary store

❌ **Authoritative storage** (use Postgres/DynamoDB) · write-heavy workloads (updates perform badly) · strong consistency requirements (eventually consistent only) · small datasets (<100K documents — a well-indexed database wins)

## Failure Modes

- **Sync drift** — CDC pipeline failures silently diverge Elasticsearch from the source of truth
- **Segment fragmentation** — rapid updates pile up soft-deletes until a merge runs
- **Deep pagination** — from/size is O(n) and expensive; use **Search After with PIT cursors**
- **Network partitions** — normal CAP tradeoffs apply to cluster coordination

## Interview Approach

The winning framing is **separation of concerns**: primary store owns writes and truth, Elasticsearch serves reads. Justify it against the simpler alternative — Postgres GIN indexes give you full-text search with a far smaller architectural footprint. Then name your CDC sync strategy and acknowledge the eventual consistency it implies.

> Paywalled on the source page: "Numbers to Know", database indexing section — so no reliable latency/throughput benchmarks from this source.
