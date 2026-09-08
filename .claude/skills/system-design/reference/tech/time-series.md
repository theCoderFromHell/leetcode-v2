# Time-Series Databases

> **"Don't reach for a time-series database when a general-purpose database like Postgres or DynamoDB would be a better fit."**
>
> This is the page's central warning. Time-series *data* does not require a time-series *database*.

Technologies: InfluxDB, TimescaleDB, Prometheus.

## What Makes Them Different

**Append-only storage.** Sequential writes hugely outperform random I/O. New data appends to the end of a file rather than seeking and updating in place — enabling **hundreds of thousands of writes/sec on SSDs**.

**LSM trees.** In-memory buffer → sorted SSTable files → background compaction. Trades read performance for write throughput by deferring reorganization.

**Time-based partitioning.** Daily/weekly windows localize writes to the current partition, make retention a partition *drop* instead of an expensive delete query, and speed range queries.

**Bloom filters.** Answer "definitely not here" with zero I/O, cutting dozens of potential file reads down to one or two.

## Compression — The Real Superpower

| Technique | Result |
|---|---|
| Delta encoding | Store differences (45.2 → +0.1 → −0.2) |
| Delta-of-delta on timestamps | **~1 bit per value** |
| XOR float compression | **~1.37 bytes per value** on typical data |

Worked example achieved **60% reduction** versus raw storage.

## Downsampling and Rollups

Pre-aggregate older data at coarser resolution — 1-minute averages after 7 days, hourly after 30. Cuts historical query cost by orders of magnitude.

Proposing this unprompted is a **staff-level signal**: *"we need fine resolution for one week, then downsample to 5-minute averages."*

## The Cardinality Problem

High-cardinality **tags** (user IDs, request IDs) explode memory, because the in-memory tag index must track every unique combination. That single mistake **disqualifies the benefits** of a TSDB entirely.

**Rule:** high-cardinality values belong in **fields**, not **tags**.

## Worked Numbers

- 100,000 servers × 5 metrics × 10-second intervals = **50,000 writes/sec**
- **4.3 billion** data points/day
- Compression: 60% reduction
- Bloom filters: dozens of reads → one or two

## When NOT to Use One

The **Top-K problem** is the counterexample: sorting and aggregating across millions of series makes a TSDB *slower* than a general-purpose database. Fine for "this series over time," bad for "rank across all series."

## Interview Strategy

Understand **why** the technology excels — low-cardinality tags, regular timestamps, small deltas — before proposing it. If those don't hold, don't reach for it.

> Falling back to a familiar solution beats confidently deploying the wrong technology.
