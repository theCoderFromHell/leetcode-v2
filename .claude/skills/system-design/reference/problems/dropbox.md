# Dropbox / File Storage

**Shape:** large blobs · **Core tension:** never route file bytes through your backend

## 1. Functional
- Upload / download files from any device
- Share files with other users
- Auto-sync across devices
- *Out of scope:* editing, preview without download

## 2. Non-Functional
- Files up to **50GB**
- High availability over consistency
- Security + recoverability
- Minimize upload/download/sync latency

## 3. Core Entities
File (raw bytes) · FileMetadata (name, size, MIME, uploader) · User

## 4. API
```
POST  /files/presigned-url          → upload URL
GET   /files/{fileId}/presigned-url → download URL
POST  /files/{fileId}/share
GET   /files/changes?since={ts}     → sync changes
PATCH /files/{fileId}/chunks        → chunk status
```

## 5. High-Level Design
**Upload:** client requests presigned URL → uploads **directly to S3** → S3 notifies backend → metadata marked `uploaded`
**Download:** backend returns CDN-signed URL → client pulls from nearest edge
**Sharing:** normalized `SharedFiles` table, `userId` partition key + `fileId` sort key
**Sync:** WebSocket push for realtime + periodic polling fallback

## 6. Deep Dives

**Large file support**
- Problem: timeouts, browser limits (**API Gateway caps at 10MB**), network interruption
- Multipart chunking at **5–10MB**, client-side fingerprinting for resumability, server-side ETag verification, complete only when all parts assemble

**Speed**
- Parallel chunk uploads saturate bandwidth
- **Content-Defined Chunking (CDC)** with a rolling hash — an edit only affects nearby chunks
- Client-side compression (Zstandard) *before* encryption

**Security**
- HTTPS in transit, S3 encryption at rest
- Signed URLs with **5-minute expiry**; CDN validates signature before serving

## Key Insight
> "The best approach is to allow the user to upload the file directly to Blob Storage from the client."

Presigned URLs bypass the backend entirely. Everything else follows from that.

## Common Mistakes
- **Uploading twice** — to your backend, then to S3
- Server-side chunking, which defeats the point of resumability
- **Fixed-size chunks** — they break delta sync; use CDC
- Compressing already-compressed media for negligible gain
- Trusting the client's chunk-status report without verification
