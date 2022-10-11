package edu.neu.madcourse.pettin.Classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    private String username;
    private String email;
    private String followers;
    private Map<String, String> following;
    private Map<String, Object> posts;

    private ArrayList<String> dogs;
    private ArrayList<User> matchedUsers;


    private ArrayList<String> dislikeDog;
    private String userId;

    public User(){};
    // TODO disliked dog list
    public User(String username, String email, String userId){
        this.following=new HashMap<>();
        this.username = username;
        this.email = email;
        this.posts = new HashMap<>();
        this.dogs = new ArrayList<>();
        this.matchedUsers = new ArrayList<>();
        this.dislikeDog = new ArrayList<>();
        this.userId = userId;
    }

    public ArrayList<String> getDogs() {
        return dogs;
    }

    public void setDogs(ArrayList<String> dogs) {
        this.dogs = dogs;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public Map<String, String> getFollowing() {
        return following;
    }

    public void setFollowing(Map<String, String> following) {
        this.following = following;
    }

    public Map<String, Object> getPosts() {
        return posts;
    }

    public void setPosts(Map<String, Object> posts) {
        this.posts = posts;
    }

    public ArrayList<User> getMatchedUsers() {
        return matchedUsers;
    }

    public void setMatchedUsers(ArrayList<User> matchedUsers) {
        this.matchedUsers = matchedUsers;
    }


    public ArrayList<String> getDislikeDog() {
        return dislikeDog;
    }

    public void setDislikeDog(ArrayList<String> dislikeDog) {
        this.dislikeDog = dislikeDog;
    }

    public void updateDislikeDog(String dogId) {
        this.dislikeDog.add(dogId);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", followers=" + followers +
                ", posts=" + posts +
                '}';
    }
}