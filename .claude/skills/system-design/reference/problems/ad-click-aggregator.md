# Ad Click Aggregator

**Shape:** write-heavy · **Core tension:** high-velocity writes → fast analytical reads

## 1. Functional
- User clicks an ad and is redirected to the advertiser
- Advertisers query click metrics at **1-minute granularity**

## 2. Non-Functional
- **10k clicks/sec** peak (100M clicks/day average)
- **Sub-second** query response
- **Zero data loss**
- Queryable immediately after the click
- **Idempotency** — no duplicate counting

## 3. Core Entities
Click event (AdId, UserId, Timestamp, ImpressionId) · Aggregated metric (AdId, minute, count) · Impression

## 4. API
```
POST /click     → track + redirect
GET  /metrics   → aggregates by AdId and time range
```

## 5. High-Level Design
User → **Click Processor** → **Kafka/Kinesis** (sharded by AdId) → **Flink** (windowed aggregation) → **OLAP DB** (Snowflake/BigQuery) → advertiser queries.
Plus **Redis** for dedup and **S3** as a raw-event data lake.

## 6. Deep Dives

**Scaling to 10k clicks/sec** — horizontal processor scaling, Kafka sharding by AdId, Flink parallelization, and **random partition suffixes** to mitigate hot shards on popular ads.

**Zero data loss** — stream replication across brokers and zones, **7-day retention**, and a **Lambda architecture**: realtime Flink plus a daily batch Spark job reconciling against archived raw events in S3.

**Idempotency** — the Ad Placement Service generates **signed impression IDs (HMAC-verified)** used as dedup keys, checked against Redis before the stream write.

**Query latency** — pre-aggregate in the stream rather than at query time; optional pre-aggregation tables for coarser granularities.

## Key Insight
> "Ad click aggregation is a textbook scaling writes problem."

Write throughput dominates read load. Streaming plus pre-aggregation converts high-velocity writes into fast analytical queries — you never aggregate at read time.

## Common Mistakes
- **Storing raw events and aggregating at query time** — the central bottleneck
- Using one database for both storage and analytics
- Ignoring hot shards on popular ads
- Overcomplicating dedup instead of using impression IDs
