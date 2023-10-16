package com.example.assignment1;

public class request {

    private String itemId;
    private String requesterId;

    private String ownerId;

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public request(String itemId, String requesterId, String ownerId) {
        this.itemId = itemId;
        this.requesterId = requesterId;
        this.ownerId= ownerId;

    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(String requesterId) {
        this.requesterId = requesterId;
    }
}
