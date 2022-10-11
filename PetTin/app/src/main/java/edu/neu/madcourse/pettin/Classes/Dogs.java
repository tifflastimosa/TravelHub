package edu.neu.madcourse.pettin.Classes;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Dogs {

    private String name;
    private String gender;
    private String spayed;
    private int age;
    private String breed;
    private ArrayList<String> playStyles;
    private Double weight;
    private int energyLevel;
    private String img;
    private String location;
    private String userID;
    private Timestamp timestamp;
    private String dog_id;
    private Map<String, Dogs> sentMatch;
    private Map<String, Dogs> receivedMatch;


    public Dogs(String name, String gender, String spayed, int age, String breed, ArrayList<String> playStyles, Double weight, int energyLevel, String img, String location, String userId, Timestamp timestamp, Map<String, Dogs> sentMatch, Map<String, Dogs> receivedMatch) {
        this.name = name;
        this.gender = gender;
        this.spayed = spayed;
        this.age = age;
        this.breed = breed;
        this.playStyles = playStyles;
        this.weight = weight;
        this.energyLevel = energyLevel;
        this.img = img;
        this.location = location;
        this.userID = userId;
        this.timestamp = timestamp;
        this.dog_id = UUID.randomUUID().toString();
        this.sentMatch = sentMatch;
        this.receivedMatch = receivedMatch;

    }

    public Dogs() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSpayed() {
        return spayed;
    }

    public void setSpayed(String spayed) {
        this.spayed = spayed;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public ArrayList<String> getPlayStyles() {
        return playStyles;
    }

    public void setPlayStyles(ArrayList<String> playStyles) {
        this.playStyles = playStyles;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public int getEnergyLevel() {
        return energyLevel;
    }

    public void setEnergyLevel(int energyLevel) {
        this.energyLevel = energyLevel;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getDog_id() {
        return dog_id;
    }

    public void setDog_id(String dog_id) {
        this.dog_id = dog_id;
    }

    public Map<String, Dogs> getSentMatch() {
        return sentMatch;
    }

    public void setSentMatch(Map<String, Dogs> sentMatch) {
        this.sentMatch = sentMatch;
    }

    public Map<String, Dogs> getReceivedMatch() {
        return receivedMatch;
    }

    public void setReceivedMatch(Map<String, Dogs> receivedMatch) {
        this.receivedMatch = receivedMatch;
    }
}