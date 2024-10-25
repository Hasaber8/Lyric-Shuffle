package edu.northeastern.a6_group10;

import java.util.Date;

public class Chat {
    private int fromCount;
    private Date timestamp;

    public Chat(int fromCount, Date timestamp) {
        this.fromCount = fromCount;
        this.timestamp = timestamp;
    }

    public int getFromCount() {
        return fromCount;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}

