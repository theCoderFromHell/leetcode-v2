# Gopuff / Local Delivery

**Shape:** proximity + contention · **Core tension:** strong-consistent writes vs high-volume reads

## 1. Functional
- Query item availability by location (within 1-hour delivery radius)
- Place multi-item orders **atomically**
- *Out of scope:* payments, driver routing, search, cancellation

## 2. Non-Functional
- Availability queries **< 100ms**
- **Strong consistency** for ordering — no double-booking
- **10k** distribution centers, **100k** catalog items
- **10M orders/day** (~115 orders/sec) but **~20k availability queries/sec**

## 3. Core Entities
Inventory (physical instances at a DC) · Item (product type) · DistributionCenter · Order

## 4. API
```
GET  /availability?lat=X&long=Y&filters=...   (paginated)
POST /orders                                   { location, items[] }
```

## 5. High-Level Design
**Availability:** client → Availability Service → Nearby Service (returns serviceable DCs) → query Postgres **read replicas** across those DCs → union → return
**Order:** client → Orders Service → **SERIALIZABLE transaction on the Postgres leader** that checks inventory, validates stock, creates the order, and updates inventory status atomically

## 6. Deep Dives

**Traffic-aware nearby DC selection**
- ❌ *Bad* — Euclidean/Haversine distance, ignoring traffic and geography
- ❌ *Bad* — call a travel-time service against all 10k DCs
- ✅ **Great** — prune to a **60-mile radius** first, then validate with the travel-time service against only those candidates

**Scaling 20k availability queries/sec**
- ✅ **Great** — Redis cache with **1-minute TTL**; Order Service invalidates on write
- ✅ **Great** — partition inventory by region using **zipcode prefixes**; reads to replicas, orders to the leader

## Key Insight
Strong consistency requires an ACID transaction in a single database; read-heavy availability requires caching and replicas. The design separates them — **atomic writes to the leader, reads from replicas** — plus regional partitioning to shrink query scope.

## Common Mistakes
- Distributed transactions across multiple datastores without addressing deadlocks and partial commits
- Querying all 10k DCs per availability request instead of pre-filtering
- Assuming cache invalidation is free — inventory updates must explicitly expire affected entries
