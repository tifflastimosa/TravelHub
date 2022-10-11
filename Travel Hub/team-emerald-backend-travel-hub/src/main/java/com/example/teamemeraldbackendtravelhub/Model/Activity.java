package com.example.teamemeraldbackendtravelhub.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

public class Activity {

    @Id
    private String id;
    @Field(value =  "activity")
    private String activity;
    @Field(value = "cost")
    private Float cost;
    @Field(value = "image")
    private String imageLink;
    @Field(value = "link")
    private String siteLink;
    @Field(value = "start")
    private String start;
    @Field(value = "end")
    private String end;

    public Activity() {
    }

    public Activity(@JsonProperty("id") String id, @JsonProperty("activity") String activity,
                    @JsonProperty("cost") Float cost, @JsonProperty("image") String imageLink,
                    @JsonProperty("link") String siteLink, @JsonProperty("start") String start,
                    @JsonProperty("end") String end) {
        this.id = id;
        this.activity = activity;
        this.cost = cost;
        this.imageLink = imageLink;
        this.siteLink = siteLink;
        this.start = start;
        this.end = end;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getSiteLink() {
        return siteLink;
    }

    public void setSiteLink(String siteLink) {
        this.siteLink = siteLink;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

}
