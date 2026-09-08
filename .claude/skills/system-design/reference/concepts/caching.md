# Caching

## The Four Layers

| Layer | What | Note |
|---|---|---|
| **External (Redis/Memcached)** | Shared across app servers | **The default interview answer.** LRU + TTL |
| **CDN** | Geographic edge cache | Static content near users |
| **Client-side** | Browser / mobile / client lib | Little backend control; handles offline |
| **In-process** | Local app memory | Fastest, *not shared*. Config, feature flags, rate limits. Optimization layer only |

## The Numbers

| Operation | Latency |
|---|---|
| In-memory cache read | **~1ms** |
| Database disk read | **30–50ms** |
| Distant region, no CDN | 250–300ms |
| Same content via CDN | **20–40ms** |

## The Four Patterns

| Pattern | Best for | Tradeoff |
|---|---|---|
| **Cache-aside** | Most scenarios — **the default** | Misses pay extra latency |
| **Write-through** | Strong consistency needs | Slower writes; dual-write problem remains |
| **Write-behind** | High write throughput | Data loss if cache crashes before flush |
| **Read-through** | CDNs, specialized systems | More complex; less common than cache-aside |

**Cache-aside flow:** check cache → on miss, query DB → store with TTL → return.

## Eviction

- **LRU** — evict least recently used. Adapts well to most workloads; the default
- **LFU** — evict least frequently used. Good when popularity is stable
- **FIFO** — oldest first, ignores usage. Rarely the right call
- **TTL** — expiry-based; combine with LRU/LFU to balance freshness against memory

## The Three Problems

**Cache stampede.** A popular key expires and many requests rebuild it simultaneously, all hammering the DB at once.
→ Request coalescing (single-flight), proactive cache warming, staggered/jittered TTLs.

**Cache consistency.** Cached data drifts from the database.
→ Invalidate on write, short TTLs accepting staleness, or explicitly accept a brief stale window. Say which one you chose and why.

**Hot keys.** One key takes disproportionate traffic and overloads a single node.
→ Replicate the hot key across nodes, add a local fallback cache, rate limit.

**Cache outage.** Redis dies entirely and every request falls through to the DB.
→ In-process fallback cache, circuit breaker, graceful degradation.

## The Five-Step Interview Framework

1. **Establish the bottleneck** — "database at 80% CPU serving repetitive reads; queries average 30–50ms"
2. **Identify what's cacheable** — high-read, low-write, expensive to compute (profiles, computed feeds)
3. **Choose the pattern** — cache-aside unless something argues otherwise
4. **Set eviction** — e.g. LRU with a 10-minute TTL
5. **Address tradeoffs** — invalidation strategy, failure fallback, stampede prevention

> "Don't cache everything. Show you understand when caching is worth the complexity and when a well-indexed database is enough."
