package com.example.stub.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "kafka_table")  
public class RecordEntity {

    @Id
    private String msgUuid;
    private boolean head;
    private long timeRq;

    public RecordEntity() {}

    public RecordEntity(String msgUuid, boolean head, long timeRq) {
        this.msgUuid = msgUuid;
        this.head    = head;
        this.timeRq  = timeRq;
    }

    public String getMsgUuid() { return msgUuid; }
    public void setMsgUuid(String msgUuid) { this.msgUuid = msgUuid; }

    public boolean isHead() { return head; }
    public void setHead(boolean head) { this.head = head; }

    public long getTimeRq() { return timeRq; }
    public void setTimeRq(long timeRq) { this.timeRq = timeRq; }
}
