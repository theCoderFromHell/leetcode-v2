# Web Crawler

**Shape:** data pipeline · **Core tension:** pipelining to isolate failure

## 1. Functional
- Crawl the web from seed URLs
- Extract and store page text
- *Out of scope:* text processing, non-text data, dynamic content, auth

## 2. Non-Functional
- **10 billion pages** at **~2MB** average
- Complete within **5 days**
- Resume without losing progress
- **Politeness:** obey robots.txt, **1 request/sec per domain**
- Minimize redundant crawling

## 3. Core Entities
URL (depth, status, last crawl) · Domain (robots.txt, crawl delay) · Page (raw HTML in blob, extracted text) · Content hash

## 4. Interface
Input: seed URLs. Output: extracted text in S3. *(No user-facing API — say this explicitly rather than inventing one.)*

## 5. High-Level Design
**Frontier Queue** (SQS, exponential backoff) → **URL Fetcher** → **Text/URL Extractor** → **Metadata DB** (DynamoDB) + **Blob Storage** (S3). Supported by cached **DNS resolution** and a Redis-backed **per-domain rate limiter**.

## 6. Deep Dives

**Fault tolerance** — pipelined stages isolate failure. Messages persist in SQS with visibility timeouts; failed fetches go to a DLQ after **5 retries**.

**Politeness** — cache robots.txt per domain; Redis locks enforce crawl-delay; sliding-window rate limiting **with jitter** so crawlers don't synchronize.

**10B pages in 5 days** — ~**3,750 pages/sec** per high-powered instance at 30% bandwidth utilization → **~8 machines**. Parser workers auto-scale on queue depth. Avoid the DNS bottleneck via caching and multiple providers.

**Deduplication** — URL-level via Metadata DB lookup before enqueueing; **content-level via indexed hash** (preferred over Bloom filters here for simplicity).

**Crawler traps** — max depth threshold of **~15–20 hops** from seed.

## Key Insight
> "Pipelining allows you to isolate failures to a single stage and retry that stage without losing progress on the rest of the data. It also allows independent scaling and optimization."

## Common Mistakes
- **Overlooking DNS as a bottleneck** — can consume **70% of latency** at scale
- **Storing large HTML payloads in the queue** instead of blob storage
- Skipping content-level dedup and re-crawling identical data across domains
- **No jitter** in rate limiting → thundering herd
