package com.example.assignment1;
public class Model {
    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    private String messageId;
    String msg;
    String dpurl;
    String img_url;
    String timestamp;
    String messageType;
    public Model() {
    }

    public Model(String msg, String dpurl, String timestamp , String msg_type) {
        this.msg = msg;
        this.dpurl = dpurl;
        this.timestamp = timestamp;
        this.messageType = msg_type;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDp() {
        return dpurl;
    }

    public void setDp(String dpurl) {
        this.dpurl = dpurl;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
    public String getImageUrl() {
        return img_url;
    }

    public void setImageUrl(String img_url) {
        this.img_url = img_url;
    }
}

