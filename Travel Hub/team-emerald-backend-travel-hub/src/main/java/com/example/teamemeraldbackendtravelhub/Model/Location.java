package com.example.teamemeraldbackendtravelhub.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "locations")
public class Location {

    @Id
    private String id;
    @Field(value = "city")
    private String city;
    @Field(value = "continent")
    private String continent;
    @Field(value = "country")
    private String country;
    @Field(value = "image")
    private String image;
    @Field(value = "activities")
    private List<Activity> activities;

    public Location() {
    }

    public Location(@JsonProperty("id") String id, @JsonProperty("city") String city, @JsonProperty("continent") String continent,
                    @JsonProperty("country") String country, @JsonProperty("image") String image, @JsonProperty("activities") List<Activity> activities) {
        this.id = id;
        this.city = city;
        this.continent = continent;
        this.country = country;
        this.image = image;
        this.activities = activities;
    }

    public String getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String getContinent() {
        return continent;
    }

    public String getCountry() {
        return country;
    }

    public String getImage() { return image; }

    public List<Activity> getActivities() {
        return activities;
    }
}
