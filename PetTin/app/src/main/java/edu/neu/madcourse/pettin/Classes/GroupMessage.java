package edu.neu.madcourse.pettin.Classes;


import com.google.firebase.firestore.FieldValue;

import java.time.LocalDateTime;

// COMPLETED

public class GroupMessage {

    private String sender;
    private String message;
    private String username;
    private String timestamp;
    private String image;


    public GroupMessage() {
    }

    public GroupMessage(String sender, String message, String username, String image) {
        this.sender = sender;
        this.message = message;
        this.username = username;
        this.image = image;
        this.timestamp = LocalDateTime.now().toString();
    }


    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}