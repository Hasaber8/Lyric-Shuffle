package edu.northeastern.a6_group10;

public class Chat {
    private long timestamp;
    private String senderId;
    private String stickerId;

    private Chat() {
        // Default constructor required for calls to DataSnapshot.getValue(Chat.class)
    }

    public Chat(long timestamp, String senderId, String stickerId) {
        this.timestamp = timestamp;
        this.senderId = senderId;
        this.stickerId = stickerId;
    }

    public Chat(String senderId, String stickerId) {
        this.timestamp = 0L;
        this.senderId = senderId;
        this.stickerId = stickerId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getStickerId() {
        return stickerId;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String toString() {
        return "Chat{" +
                "timestamp=" + timestamp +
                ", senderId='" + senderId + '\'' +
                ", stickerId='" + stickerId + '\'' +
                '}';
    }
}

