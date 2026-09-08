# DynamoDB

Fully-managed key-value / document store. **AP system by default** with BASE properties.

## Data Model

Tables → items (rows) → attributes. Schema-less. **Max item size: 400KB.**

- **Partition key** — hashed to determine physical placement. Must distribute evenly *and* match your main query pattern
- **Sort key** (optional) — range queries and ordering within a partition

Prefer monotonically increasing IDs (**UUID v7, Snowflake, ULID**) over raw timestamps — you get ordering *and* uniqueness.

*Example:* chat app with `chat_id` (partition) + `message_id` (sort) retrieves a chat's messages in chronological order efficiently.

## GSI vs LSI

| | **GSI** | **LSI** |
|---|---|---|
| Partition key | Different from base table | Same as base table |
| Use for | Querying non-key attributes globally | Range queries within a partition |
| Storage | Separate partitions | Co-located with base table |
| Consistency | **Eventually consistent only** | Strong *and* eventual |
| Creation | Anytime | **Table creation only** |
| Size limit | None | **10GB per partition key** |
| Max count | 20 per table | 5 per table |

## Hard Limits — Quote These

| Limit | Value |
|---|---|
| Item size | **400KB** |
| Single partition read | **3,000 RCU** (12MB/sec) |
| Single partition write | **1,000 WCU** (1MB/sec) |
| Read capacity unit | 4KB/sec (eventually consistent = 0.5 RCU) |
| Write capacity unit | 1KB/sec, rounded up |
| Transaction scope | **100 items** across tables |

The per-partition ceilings are exactly what you cite when discussing hot partitions.

## Access Patterns

- **Query** — uses primary or secondary index keys. Efficient. Always prefer this
- **Scan** — reads every item. Avoid on large tables
- **ProjectionExpression** cuts network bandwidth only — **you still pay full RCU** on item size

## Consistency

- **Eventual** (default) — lower latency, higher availability, **half the RCU cost**
- **Strong** — latest data, **double RCU**, only on the base table and LSIs (never GSIs)

**ACID transactions** via `TransactWriteItems` / `TransactGetItems`, up to 100 items.

## Extras Worth Naming

**DAX** — in-memory cache, microsecond reads, API-compatible SDK swap. Caches items and queries but **not strongly consistent reads**.

**DynamoDB Streams** — captures INSERT/UPDATE/DELETE. The standard way to sync to Elasticsearch, trigger Lambdas, or feed analytics. This is your CDC mechanism.

## When to Avoid

1. **Cost at high write volume** — gets expensive fast; compare against S3 at $0.023/GB vs DynamoDB at $1.25/GB
2. **Complex queries** — no joins, no ad-hoc aggregation
3. **Heavy GSI/LSI reliance** — if you need many indexes, you wanted a relational database
4. **Vendor lock-in** — some interviewers will ask for an open-source alternative (Cassandra)

> Paywalled on the source page: PostgreSQL deep-dive, vector databases, CDC patterns.
