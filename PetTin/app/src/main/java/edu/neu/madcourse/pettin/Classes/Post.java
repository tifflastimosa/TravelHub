package edu.neu.madcourse.pettin.Classes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Post {
    private String image;
    private String title;
    private String content;
    private String location;
    private String time;
    private List<String> likes;
    private String username;
    private String post_id;

    public Post(){}

//    public Post(String image, String title, String content, String location, String time, String likes, String username) {
//        this.image = image;
//        this.title = title;
//        this.content = content;
//        this.location = location;
//        this.time = time;
//        this.likes = likes;
//        this.username = username;
//        this.post_id = UUID.randomUUID().toString();
//    }

    public Post(String image, String title, String content, String location, String time, String username) {
        this.image = image;
        this.title = title;
        this.content = content;
        this.location = location;
        this.time = time;
        this.username = username;
        this.post_id = UUID.randomUUID().toString();
        this.likes = new ArrayList<>();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<String> getLikes() {
        return likes;
    }

    public void setLikes(List<String> likes) {
        this.likes = likes;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    @Override
//    public String toString() {
//        return "Post{" +
//                "image='" + image + '\'' +
//                ", title='" + title + '\'' +
//                ", content='" + content + '\'' +
//                ", location='" + location + '\'' +
//                ", time='" + time + '\'' +
//                ", likes='" + likes + '\'' +
//                ", username='" + username + '\'' +
//                '}';
//    }
    public String toString() {
        return "Post{" +
                "image='" + image + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", location='" + location + '\'' +
                ", time='" + time + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}