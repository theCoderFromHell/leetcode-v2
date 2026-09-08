# WhatsApp

**Shape:** realtime messaging · **Core tension:** durability decoupled from delivery

## 1. Functional
- Group chats, **2–100 participants**
- Send/receive messages in realtime
- Offline message storage (**30-day** retention)
- Media attachments

## 2. Non-Functional
- Delivery **< 500ms**
- Billions of users, **200M concurrent connections**
- **~40K messages/sec** baseline, **~100K writes/sec** including Inbox
- Guaranteed delivery, component failure tolerance

## 3. Core Entities
User · Chat · Message · Client (multiple devices per user) · ChatParticipant

## 4. API — WebSocket commands
```
createChat · sendMessage · createAttachment · modifyChatParticipants
inbound: chatUpdate, newMessage  (with ACK)
```

## 5. High-Level Design
**Send path:**
1. Client sends `sendMessage` over WebSocket
2. Server writes to Messages table **+ Inbox entries (durable)**
3. Server publishes to **Redis Pub/Sub** for realtime delivery
4. Connected clients receive `newMessage`, send ACK
5. Server deletes the Inbox entry on ACK

**Offline:** undelivered messages sit in a per-user Inbox; client syncs on reconnect; TTL cleanup after 30 days
**Media:** direct client upload to blob storage via presigned URL; the message carries an opaque URL, never bytes

## 6. Deep Dives

| Problem | Solution |
|---|---|
| Billions of concurrent users | Redis Pub/Sub with **per-user channels**, sharded cluster; chat servers subscribe to user topics |
| Multiple devices | Separate `Clients` table, per-client Inbox entries, **3-device limit** |
| WebSocket failures | Application-level heartbeats every **10–30s**; server detects timeout in **< 15s**, forces reconnect + Inbox sync |
| Redis delivery failure | **Per-chat sequence numbers** with gap detection, piggybacked on heartbeats; periodic polling as backstop |
| Out-of-order messages | Accept eventual consistency; NTP server timestamps; display by receive time |
| Last seen | Track disconnect time in DynamoDB; realtime `ONLINE` via Pub/Sub query — **avoid write amplification** |

## Key Insight
> "Messages should be stored on centralized servers no longer than necessary."

The architecture **decouples durability from delivery**: the database write happens first and is authoritative; Pub/Sub is best-effort on top. That ordering is what makes the system reliable without making it slow.

## Common Mistakes
- **Naive horizontal scaling** with no routing — messages never reach the right server
- **A Kafka topic per user** — impossible overhead at billion-user scale
- **Database write on every heartbeat** — massive write amplification for last-seen
- Ignoring the multi-device case
