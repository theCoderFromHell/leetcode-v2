# Top K (YouTube Top Videos)

**Shape:** write-heavy aggregation · **Core tension:** 700k writes/sec into a ranked query

## 1. Functional
- Query top K videos (max 1k) over: last hour, day, month, all-time
- **Tumbling windows** (hour boundaries)
- *Out of scope:* arbitrary periods, historical lookback

## 2. Non-Functional
- Latency **tens of milliseconds**
- **700k views/sec** (70B views/day ÷ 86.4k sec)
- Storage **~64GB** naive (4B videos × 16 bytes)
- Exact results initially — no approximation
- **1-minute delay tolerance** for late events ← *this unlocks everything*

## 3. Core Entities
Video · View (event with timestamp) · TimeWindow

## 4. API
```
GET /views/top-k?window={WINDOW}&k={K}
  → { videoId, views }[]
```

## 5. High-Level Design
Kafka (partitioned by `videoId`) → view consumers → Postgres (indexed on views) → Top-K service + cache.

## 6. Deep Dives

| Challenge | Solution | Tradeoff |
|---|---|---|
| Read scaling | Precompute top-K via cron, warm cache hourly | Operational complexity |
| **700k TPS writes** | Shard by `videoId` across **70 instances**; batch with **Flink** (1-hour windows) | Cuts writes **2–100x** via aggregation |
| Large-window queries | Separate table per granularity; aggregate hour→day→month | Multiple write targets, query routing |
| Sliding windows | Increment current minute, decrement T−60 | Needs minute-grain storage; big complexity jump |
| Approximation | **Count-Min Sketch** + sorted set, ~1k items | **100–1000x** memory reduction, loses exactness |
| Specialized DBs | TimescaleDB hypertables, or Druid/Pinot | Risk: interviewer may not know them |

## Key Insight
**Precomputation bridges the requirements.** The 1-minute grace period is not a throwaway detail — it's what permits caching and batching, letting 700k TPS be absorbed by aggregation at the source (Flink) instead of hitting the database. Continuous writes become periodic bulk operations.

## Common Mistakes
- **Ignoring the 1-minute buffer** and missing the precomputation opening
- Starting with a specialized database before justifying it with primitives
- Overcomplicating sliding windows early — **tumbling windows cover 80% of use cases**
- Not estimating throughput, and therefore not seeing why single-node Postgres fails at 700k TPS

> Note: `tech/time-series.md` explicitly names Top-K as the case where a TSDB is **slower** than a general-purpose database — sorting across millions of series is exactly its weakness.
