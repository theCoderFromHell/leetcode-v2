# Numbers to Know

> Note: HelloInterview gates most of this page (the full cheat sheet, cost analysis, per-component deep dives) behind premium. Free-tier hardware figures below; the component table is from the *in-a-hurry* summary page.

## The Core Premise

> "One of the biggest giveaways that a candidate has book knowledge but no hands-on experience during a system design interview is when they rely on outdated hardware constraints."

Calculations built on 2015–2020 figures **dramatically underestimate** what one modern machine does. This cuts directly against premature sharding.

## Modern Hardware Ceilings (AWS)

| Instance | Capacity |
|---|---|
| M6i.32xlarge | 512 GiB RAM, 128 vCPU |
| X1e.32xlarge | **4 TB RAM** |
| U-24tb1.metal | **24 TB RAM** |
| i3en.24xlarge | 60 TB local SSD |
| D3en.12xlarge | **336 TB HDD** |

**Network:** 25 Gbps standard, 50–100+ Gbps on high-performance instances.

## Latency Ladder

| Hop | Latency |
|---|---|
| Memory access | nanoseconds |
| SSD read | microseconds |
| Within an availability zone | **< 1ms** |
| Across AZs | **1–2ms** |
| Cross-region | **50–150ms** |
| NYC ↔ London (physics floor) | **~56ms** |
| Cache hit | ~1ms |
| Database disk read | 30–50ms |

Light in fiber ≈ 200,000 km/s. No architecture beats that floor.

## Component Capacity

| Component | Throughput | Latency | Storage | Scale trigger |
|---|---|---|---|---|
| **Redis** | 100k+ ops/sec | ~1ms | up to 1 TB | <80% hit rate · >1ms latency · >80% memory |
| **Database** | up to 50k TPS | <5ms cached | 64 TB+ | >10k write TPS · >5ms uncached read |
| **App server** | 100k+ concurrent conns | SLA-dependent | 64–512 GB | >70% CPU · near 100k conns |
| **Message queue** | 1M msg/sec per broker | <5ms e2e | 50 TB | >800k msg/sec |

## Sharding Thresholds

- Single instance storage ceiling ≈ **256 TiB** (Aurora)
- Write throughput struggles above **50K writes/sec**
- A single Postgres comfortably serves **tens of thousands of QPS** and **several TB**
- Sharding is justified in the **tens-to-hundreds of TB** range — **not at 500 GB**

## Cost

| Store | Price |
|---|---|
| S3 | **$0.023** / GB / month |
| DynamoDB | **$1.25** / GB / month |

≈ **54x** difference. This is the argument for blob storage plus metadata pointers, rather than putting large objects in the primary database.

## How to Use These

- **Don't** recite them upfront. Capacity estimation belongs where it changes a decision, not in the first five minutes
- **Do** deploy one mid-design to justify a choice: *"We need 8k writes/sec; a single Postgres does ~50k, so one node is fine — no sharding yet."*
- Walking the math contextually demonstrates thinking. Reciting it demonstrates memorization
- The most valuable use is **arguing against** unnecessary complexity
