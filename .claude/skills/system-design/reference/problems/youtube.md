# YouTube

**Shape:** large blobs + streaming · **Core tension:** a video is segments, not a file

## 1. Functional
- Upload videos
- Watch (stream) videos

## 2. Non-Functional
- Availability over consistency
- Videos **tens of GBs**
- Low-latency streaming on constrained bandwidth
- **~1M uploads/day**, **100M views/day**
- Resumable uploads

## 3. Core Entities
User · Video · VideoMetadata (uploader, transcripts, format URLs)

## 4. API
```
POST /presigned_url   { metadata }  → S3 upload URL
GET  /videos/{videoId}              → metadata + manifest/segment URLs
```

## 5. High-Level Design
- **Video Service** — stateless, issues presigned URLs, serves metadata
- **Cassandra** — metadata, partitioned by `videoId`
- **S3** — multipart uploads, segments, manifests
- **Video Processing Service** — **DAG-based** orchestrator: split → transcode → generate manifest
- **CDN** — caches segments and manifests

**Upload:** client → presigned URL → direct S3 multipart → S3 event → processing pipeline
**Stream:** fetch metadata → load manifest → download **adaptive bitrate** segments per network conditions

## 6. Deep Dives

**Post-processing** — a **directed acyclic graph** orchestrating parallel segment transcoding across workers. Output: multi-format segments in S3 plus manifests referencing them.

**Resumable uploads** — client splits into **5–10MB** chunks with fingerprint hashes, tracks status in VideoMetadata, resumes by checking prior uploads. Uses S3 multipart semantics.

**100M daily views** — Cassandra read replicas for popular videos, distributed LRU cache for metadata, CDN edge servers for segments and manifests.

## Key Insight
Treating a video as **segments in multiple formats** rather than a monolithic file unlocks three things at once: resumable upload (chunked), adaptive streaming (format choice), and geographic distribution (CDN). One architectural decision cascades through the entire design.

## Common Mistakes
- Storing raw video without transcoding or segmentation
- Requiring full download instead of incremental segment streaming
- **Neglecting geography** — S3 alone cannot serve a global audience at low latency
- Treating post-processing as one operation rather than a parallelizable DAG
