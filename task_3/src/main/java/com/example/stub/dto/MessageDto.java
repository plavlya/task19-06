package com.example.stub.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageDto {
    @JsonProperty("msg_uuid")
    private String msgUuid;

    private boolean head;
    private String method;
    private String uri;

    public MessageDto() {}

    public MessageDto(String msgUuid, boolean head, String method, String uri) {
        this.msgUuid = msgUuid;
        this.head    = head;
        this.method  = method;
        this.uri     = uri;
    }

    public String getMsgUuid() { return msgUuid; }
    public void setMsgUuid(String msgUuid) { this.msgUuid = msgUuid; }

    public boolean isHead() { return head; }
    public void setHead(boolean head) { this.head = head; }

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }

    public String getUri() { return uri; }
    public void setUri(String uri) { this.uri = uri; }

    @Override
    public String toString() {
        return String.format(
            "{\"msg_uuid\":\"%s\",\"head\":%b,\"method\":\"%s\",\"uri\":\"%s\"}",
            msgUuid, head, method, uri
        );
    }
}
