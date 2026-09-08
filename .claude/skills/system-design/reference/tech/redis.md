# Redis

A **data structure store**, not just a cache. In-memory, single-threaded command processing — which is what makes its operations atomic for free.

## Structures and What They Unlock

| Structure | Pattern it enables |
|---|---|
| String + `INCR` | Fixed-window rate limiting (counter + expiry on first request) |
| Sorted set | Leaderboards (`ZADD`, `ZREMRANGEBYRANK`), sliding-window rate limiting (timestamp as score) |
| Hash | Object fields without serializing the whole blob |
| Set | Membership, dedup, tags |
| Stream | Append-only log with consumer groups — timeout-based claiming on worker failure |
| Geospatial | `GEOSEARCH`, geohash-backed, **O(N + log M)** — N candidates in grid boxes, M filtered to exact radius |
| Pub/Sub | Broadcast to *active subscribers only* — at-most-once, no persistence |

## Numbers

- **~100k writes/sec** single node
- Commands execute in **microseconds**; network latency usually sub-millisecond
- Cluster mode: **16,384 hash slots** distributed across nodes
- Pipelining batches commands into one round trip

## Distributed Locks

```
SET key token NX EX 30
```

Release via a Lua script that checks token ownership before deleting — otherwise you delete someone else's lock.

**The caveat that earns points:** async replication means a primary can die after granting a lock but before the replica sees it, producing *two* holders. **Redis locks are an efficiency tool, not a correctness guarantee.** If correctness matters (money, inventory), use a database transaction.

## Cluster Mode

Keys hash into 16,384 slots. Clients cache the slot→node map and route directly; a wrong-node query gets a `MOVED` redirect.

**Multi-key operations require hash tags** to co-locate keys in one slot:
```
{user:123}:posts   {user:123}:likes    → same slot
```

## Limitations — Say These Before the Interviewer Does

**Durability.** Async replication means acknowledged writes can vanish on primary failure. RDB snapshots lose everything since the last checkpoint; AOF fsyncs once/second by default. **Not a system of record.**

**Memory-bound.** The working set must fit economically in RAM.

**Query inflexibility.** No joins, no cross-slot queries without hash tags, no query planner.

**Hot keys.** One key lives on one node and can bottleneck it.
- Client-side caching → introduces staleness
- Key copies with random reader selection → you must track duplicates
- Read replicas → **doesn't help a write-hot key**

## Pub/Sub vs Streams vs Kafka

- **Pub/Sub** — at-most-once, no persistence, subscribers must be live. Cheapest.
- **Streams** — persisted per durability settings, consumer groups, replay within retention.
- **Kafka** — durable, long retention, replay, much higher throughput. Reach for it when you need the log to survive.

## Interview Strategy

Proactively name the hot-key risk and a remediation. Say out loud when Redis is *not* the fit: durable system of record, dataset larger than RAM, complex queries. Knowing the Pub/Sub vs Streams vs Kafka decision point is a senior signal.

> Paywalled on the source page: "Numbers to Know", several problem breakdowns.
