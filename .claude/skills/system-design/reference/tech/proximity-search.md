# Proximity Search

## Why Ordinary Indexes Fail

> "A one-dimensional sort order can't preserve two-dimensional closeness."

A B-tree on `latitude` and another on `longitude` treats them as independent dimensions. Two points can be adjacent on the map and arbitrarily far apart in either index. **Open with this sentence** — it shows you understand the problem before naming a tool.

## Approach 1 — Spatial Trees

Best for **geometric data**: polygons, roads, delivery zones.

| Structure | How | Note |
|---|---|---|
| **Quadtree** | Recursively divides into four quadrants | Simple; variable depth in dense areas (Manhattan), poor disk locality |
| **k-d / BKD tree** | Binary splits alternating dimensions, balanced by median | BKD packs points into disk pages. **What Elasticsearch uses.** Effectively write-once |
| **R-tree** | Groups objects in minimum bounding rectangles | Disk-friendly and balanced. **PostGIS default.** R*-tree refines to minimize overlap |

**Tradeoff:** expensive writes, more infrastructure.

## Approach 2 — Encoded Keys

Best for **moving points**: drivers, users, live locations.

| Encoding | How | Note |
|---|---|---|
| **Geohash** | Space-filling curve → hierarchical string ID | **5 chars ≈ 5km, 9 chars ≈ 5m.** Works with an ordinary B-tree index — the big practical win |
| **S2** | Google's spherical version | Equal-area cells globally, handles the antimeridian |
| **H3** | Uber's hexagonal grid | Cleaner ring-based analytics, integer lookups, no space-filling-curve ordering |

**Tradeoff:** points only, and you must query a ring and post-filter.

**The geohash boundary caveat:** two nearby points can sit in different cells with unrelated prefixes. You must query the **3×3 grid** of surrounding cells, then post-filter.

## The Universal Rule

**Every approach requires exact-distance post-filtering.** The index narrows candidates; the math finalizes the answer. Redis `GEOSEARCH` is explicitly **O(N + log M)** for this reason — N candidates from grid boxes, M filtered to the true radius.

## Choosing

| Situation | Reach for |
|---|---|
| Postgres already in the stack, polygons/shapes | **PostGIS** (R-tree) |
| Moving points, need speed, Redis present | **Redis GEOSEARCH** (geohash) |
| Already running Elasticsearch | **geo_point / geo_shape** (BKD) |
| Hexagonal analytics, ride-sharing zones | **H3** |
| Global scale, equal-area cells matter | **S2** |

## Interview Framework

1. Explain **why flat indexing fails** (one sentence)
2. Identify your data type — **shapes or moving points**
3. Name the production tool
4. State the specific tradeoff
5. Mention post-filtering

**And the scoping note:** most systems never query globally. When proximity is involved, users are almost always looking for things local to them — which simplifies the design considerably.

> Paywalled on the source page: database indexing, "Numbers to Know", several deep-dives.
