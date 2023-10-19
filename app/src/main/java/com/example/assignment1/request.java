package com.example.assignment1;

public class request {

    private String itemId;
    private String requesterId;

    private String ownerId;

    private String img;

    public String getOwnerId() {
        return ownerId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
   public request(){

   }
    public request(String itemId, String requesterId, String ownerId, String img) {
        this.itemId = itemId;
        this.requesterId = requesterId;
        this.ownerId= ownerId;
        this.img=img;

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
