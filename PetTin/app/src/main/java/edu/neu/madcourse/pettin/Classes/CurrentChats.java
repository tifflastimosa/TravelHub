package edu.neu.madcourse.pettin.Classes;

public class CurrentChats {

    private String id;
    private String senderId;
    private String receiverId;
    private String concatId;

    public CurrentChats() {
    }

    public CurrentChats(String senderId, String receiverId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.concatId = senderId + receiverId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConcatId() {
        return concatId;
    }

    public void setConcatId(String concatId) {
        this.concatId = concatId;
    }
}

