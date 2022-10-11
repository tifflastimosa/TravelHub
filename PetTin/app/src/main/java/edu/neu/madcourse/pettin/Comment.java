package edu.neu.madcourse.pettin;

import com.google.firebase.database.ServerValue;

public class Comment {

    private String content;
    private String userId;
    private String uname;
    private Object timestamp;


    public Comment() {
    }

    public Comment(String content, String userId, String uname) {
        this.content = content;
        this.userId = userId;
        this.uname = uname;
        this.timestamp = ServerValue.TIMESTAMP;

    }

    public Comment(String content, String comment_id, String uname, Object timestamp) {
        this.content = content;
        this.userId = comment_id;
        this.uname = uname;
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public Object getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }
}

