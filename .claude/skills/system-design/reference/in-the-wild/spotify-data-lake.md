# Spotify — Point Queries on a Data Lake

**Maps to:** Feature serving, analytics + online reads, "we have a warehouse but need single-row lookups."
**Pattern:** Scaling reads, indexing.

## The Problem

**Exabytes** of listening data in object storage (GCS) as **Parquet** — excellent for columnar scans, terrible for single-row lookups.

"What did user X listen to?" required scanning ~**90,000 candidate files** over a 90-day window. Prohibitively slow and expensive for online feature serving.

**The tension:** data lakes optimize for columnar scans, not key retrieval. The conventional fix — copy hot data into Bigtable or DynamoDB — duplicates storage and cost.

## The Solution — RAP (Random Access Parquet)

An **external index** mapping user IDs to exact file locations and row numbers, **without modifying the original Parquet files**.

**1. Multi-stage pruning: 90,000 files → ~12**
- **Bucketing** — hash user IDs into 1,000 buckets; only one bucket per day is relevant
- **Bloom filters** — cached per file, eliminate days the user wasn't active

**2. The RAP index**
- Multimap entries hold: key, file identifiers (dictionary-encoded), row numbers, optional value counts
- Distributed by key hash
- **Replaces dependent reads with parallel ranged requests** — five sequential reads collapse into one index lookup plus three parallel ranged reads

**3. Write-side optimization**
- Sort by key, start new pages on key boundaries
- Pages shrink from **4MB** mixed-user pages to **kilobytes** of single-user data
- **Covering index** — embed small values directly in index entries, skipping the storage read entirely

**4. Secondary indexing** — additional access paths over existing entries without rewriting data files. Tradeoff: secondary lookups scatter across more files than primary-key queries.

## Numbers

| Metric | Value |
|---|---|
| Candidate files | 90,000 → **~12** |
| Read pattern | 5 dependent reads → 1 lookup + 3 parallel ranged reads |
| Index size | 1 TB data → **GBs** of index; 1 PB → TBs |
| Page-split overhead | ~20 bytes of header per key boundary |

## The Transferable Lesson

The individual techniques — bucketing, Bloom filters, covering indexes — are decades old. The achievement was getting point-query performance **without a second copy of the data** and without disrupting existing pipelines.

> A **third option** between scanning the whole lake and paying to copy everything into a key-value store.

## Use in an Interview

When analytics storage must also serve online reads, the reflex answer is "copy it into DynamoDB." Naming the third option — **index the lake in place** — shows you weigh operational cost, not just latency. And the general move (multi-stage pruning: cheap filter → cheaper filter → precise lookup) transfers to almost any large-scale read problem.
