# Bitly / URL Shortener

**Shape:** read-heavy · **Core tension:** ~1000:1 read/write ratio

## 1. Functional
- Submit a long URL → get a short one (optional custom alias, optional expiry)
- Access the original URL via the short link
- *Out of scope:* auth, analytics

## 2. Non-Functional
- Redirect latency **< 100ms**
- **99.99%** availability — availability over consistency
- **1B** URLs, **100M** DAU
- Uniqueness: one short code → exactly one long URL

## 3. Core Entities
Original URL · Short URL (code) · User

## 4. API
```
POST /urls              { long_url, custom_alias?, expiration_date? } → short_url
GET  /{short_code}      → 302 redirect  (410 if expired)
```

## 5. High-Level Design
Client → Primary Server → Database (index on `short_code`). Write path validates, generates code, stores mapping. Read path looks up and redirects.

## 6. Deep Dives

**Unique code generation**
- *Hashing* — SHA-256 + base62, handle collisions by retry with salt
- *Counter* — Redis atomic increment + base62. No collisions, but needs distributed coordination. **Counter batching** cuts network overhead

**Fast redirects**
- B-tree on `short_code` as primary key → O(log n)
- Redis cache → **~1000x faster** than SSD
- CDN with edge compute → geographic caching *and* redirect logic at the edge

**Horizontal scaling**
- Split read and write services; cache absorbs reads
- Centralized Redis counter coordinates writers
- 1B × 500 bytes = **500GB** — a single instance handles this comfortably. **Do not propose sharding here**

## Key Insight
The extreme read/write asymmetry dominates everything. Aggressive caching and read-path separation matter far more than write optimization.

## Common Mistakes
- Designing for writes and missing the read-heavy nature
- Weak collision handling in the hashing approach
- **Using 301 redirects** — browsers cache them, killing analytics and future updates. Use 302
- Forgetting counter coordination across distributed writers
