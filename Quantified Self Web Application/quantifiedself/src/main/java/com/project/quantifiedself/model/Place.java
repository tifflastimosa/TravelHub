package com.project.quantifiedself.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

/**
 * Constructs a place object.
 */
@Document(collection = "place")
public class Place  {

    @Id
    private String id;
    private LocalDate date;
    private String place;
    private String type;
    private Location location;
    private String fourSquareId;

    /**
     * Constructs a place object and initializes a place with the given id value, name, type, and
     * location.
     *
     * @param place The name of the place.
     * @param type The type of place.
     * @param location The location of the place
     */
    public Place(LocalDate date, String place, String type, Location location, String fourSquareId) {
        this.date = date;
        this.place = place;
        this.type = type;
        this.location = location;
        this.fourSquareId = fourSquareId;
    }

    /**
     * Constructor for a place.
     */
    public Place() {
    }

    /**
     * Method that gets the id for a Place object.
     *
     * @return The id of the Place object added to the Places collection.
     */
    public String getId() {
        return id;
    }

    /**
     * Method that gets the date of the Place object.
     *
     * @return The date of the Place object.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Method that sets the date of the Place object.
     *
     * @param date The date of the Place object.
     */
    public void setDate(@JsonProperty("date")LocalDate date) {
        this.date = date;
    }

    /**
     * Method that gets the place of the Place object.
     *
     * @return The name of the Place.
     */
    public String getPlace() {
        return place;
    }

    /**
     * Method sets the name of the place.
     *
     * @param place The name of the place object.
     */
    public void setPlace(@JsonProperty("place") String place) {
        this.place = place;
    }

    /**
     * Method that gets the type of place.
     *
     * @return The name of type of place.
     */
    public String getType() {
        return type;
    }

    /**
     * Method sets the type of place.
     *
     * @param type The type of the place.
     */
    public void setType(@JsonProperty("type") String type) {
        this.type = type;
    }

    /**
     * The location of the place.
     *
     * @return The Location of the place, which contains, the latitude and longitude of the place.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Method sets the location of the place.
     *
     * @param location A Location object that contains the latitude and longitude of the place.
     */
    public void setLocation(@JsonProperty("location") Location location) {
        this.location = location;
    }

    /**
     * Method that gets the four square id of a place.
     *
     * @return The four square id.
     */
    public String getFourSquareId() {
        return fourSquareId;
    }

    /**
     * Method that sets the four square id.
     *
     * @param fourSquareId The four square id.
     */
    public void setFourSquareId(@JsonProperty("fourSquareId") String fourSquareId) {
        this.fourSquareId = fourSquareId;
    }

}