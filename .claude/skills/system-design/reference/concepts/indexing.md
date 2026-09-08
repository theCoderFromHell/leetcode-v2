# Database Indexing

> Note: HelloInterview gates the deeper sections of this page (LSM internals, hash indexes, covering indexes) behind premium. What follows is the free tier plus standard knowledge.

## What an Index Buys

Rows live on disk in unordered heap files. Without an index a query scans pages sequentially — fine for hundreds of rows, ruinous for millions. An index is a separate structure giving a direct path to the rows you want, minimizing pages read. Random vs sequential access still differs meaningfully even on SSD.

## What an Index Costs

- **Disk space** — sometimes approaching the size of the data itself
- **Write amplification** — every insert/update maintains *every* index on the table
- **Multiplied overhead** — each additional index taxes every write

**Indexes hurt when:** the table is write-heavy and read-light (logging tables), or small enough that a sequential scan wins.

## B-Tree — the Default

Self-balancing tree, typically hundreds of children per node, each node sized to one **8KB disk page**.

- All leaves sit at identical depth
- Keys sorted within a node
- A node with *k* keys has *k+1* children

**Strengths:** preserves sort order, so equality *and* range queries *and* `ORDER BY` all work. Predictable performance. **The safe default when unsure.**

Used by: Postgres primary keys and unique constraints, MongoDB (B+ trees), DynamoDB sort-key organization.

## Other Index Types

| Type | Good at | Cannot do |
|---|---|---|
| **LSM tree** | Write-heavy (millions of writes/sec) | Read amplification without compaction |
| **Hash** | Exact equality, fastest | **No range queries** |
| **Geospatial** | Location queries | General-purpose lookup |
| **Inverted** | Full-text search | Structured range queries |

**Geospatial approaches:** *geohash* (encodes location as a string, prefix matching), *quadtree* (recursively divides 2D space into quadrants), *R-tree* (hierarchical grouping of nearby objects).

**Inverted index** maps word → documents. The backbone of Elasticsearch/Lucene.

## Composite and Covering

**Composite indexes — column order matters enormously.** An index on `(user_id, created_at)` serves "posts by this user, newest first" and "all posts by this user," but *not* "everything created today across users." Leftmost prefix rule.

**Covering index** — contains every column a query needs, so the query is answered from the index alone without touching the heap.

## Interview Guidance

- B-tree is the safe default; say so and move on
- Weigh write frequency against read patterns before adding an index
- Index the fields you actually query: `email` for login, `user_id` for lookups
- Reach for an external search system (Elasticsearch) only when the primary DB can't serve the query *type* — then sync via CDC and accept stale reads
- Depth expectation scales with role: infrastructure interviews want mastery, product/full-stack interviews want basic fluency
