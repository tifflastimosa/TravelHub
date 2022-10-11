package com.project.quantifiedself.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Arrays;

/**
 * Class that is an Activity from the json array.
 */
@Document(collection = "activities")
public class Activity {

    @Id
    private String id;
    private LocalDate date; // required
    private String activity; // required
    private String startTime; // optional
    private String endTime; //  optional
    private Double duration; // optional
    private Double distance; // optional
    private Double steps; // optional
    private Double calories; // optional
    private int[] trackPoints;

    /**
     * Constructor for the Activity class.
     *
     * @param date      The date of the activity.
     * @param activity  The activity performed.
     * @param startTime The start time of the activity.
     * @param endTime   The end time of the activity.
     * @param duration  The duration of the activity.
     * @param distance  The distance of the activity.
     * @param steps     The number of steps, if the activity is walking or running.
     * @param calories  The number of calories burned in an activity.
     */
    public Activity(LocalDate date, String activity, String startTime, String endTime, Double duration, Double distance,
                    Double steps, Double calories, int[] trackPoints) {
        this.date = date;
        this.activity = activity;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.distance = distance;
        this.steps = steps;
        this.calories = calories;
        this.trackPoints = trackPoints;
    }

    /**
     * Constructor for Activity class.
     */
    public Activity() {
    }

    /**
     * Method that gets the id of the Activity object that is added to the activities collection.
     *
     * @return The id of the Activity object.
     */
    public String getId() {
        return id;
    }

    /**
     * Method gets the date of the Activity object.
     *
     * @return The date of the Activity object.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Method sets the date of the Activity object.
     *
     * @param date The date of the Activity object.
     */
    public void setDate(@JsonProperty("date")LocalDate date) {
        this.date = date;
    }

    /**
     * Method gets the name of the activity.
     *
     * @return The name of the activity.
     */
    public String getActivity() {
        return activity;
    }

    /**
     * Method that sets the name of the activity.
     *
     * @param activity The name of the activity.
     */
    public void setActivity(@JsonProperty("activity") String activity) {
        this.activity = activity;
    }

    /**
     * Method that gets the start time of the activity.
     *
     * @return The start time of the activity.
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Method that sets the start time of the activity.
     *
     * @param startTime The start time of the activity.
     */
    public void setStartTime(@JsonProperty("start time") String startTime) {
        this.startTime = startTime;
    }

    /**
     * Method that gets the end time of the activity.
     *
     * @return The end time of the activity.
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Method that sets the end time of the activity.
     *
     * @param endTime The end time of the activity.
     */
    public void setEndTime(@JsonProperty("end time") String endTime) {
        this.endTime = endTime;
    }

    /**
     * The duration of the activity.
     *
     * @return The duration of the activity.
     */
    public Double getDuration() {
        return duration;
    }

    /**
     * Method that sets the duration of the activity.
     *
     * @param duration The duration of the activity.
     */
    public void setDuration(@JsonProperty("duration") Double duration) {
        this.duration = duration;
    }

    /**
     * Method that gets the distance of the activity.
     *
     * @return The distance traveled for the activity.
     */
    public Double getDistance() {
        return distance;
    }

    /**
     * Method that sets the distance of the activity.
     *
     * @param distance The distance traveled for the activity.
     */
    public void setDistance(@JsonProperty("distance") Double distance) {
        this.distance = distance;
    }

    /**
     * Method that gets the steps of the activity.
     *
     * @return The steps for the given activity.
     */
    public Double getSteps() {
        return steps;
    }

    /**
     * Method sets the sets of the activity.
     *
     * @param steps The number of steps for a given activity, specifically walking and running.
     */
    public void setSteps(@JsonProperty("steps") Double steps) {
        this.steps = steps;
    }

    /**
     * Method that gets the number of calories burned.
     *
     * @return The number of calories burned.
     */
    public Double getCalories() {
        return calories;
    }

    /**
     * Method that sets the number of calories burned for the activity.
     *
     * @param calories The number of calories burned.
     */
    public void setCalories(@JsonProperty("calories") Double calories) {
        this.calories = calories;
    }

    /**
     * Method that gets the track points array.
     *
     * @return An array of track points.
     */
    public int[] getTrackPoints() {
        return trackPoints;
    }

    /**
     * Method that sets the track points array.
     *
     * @param trackPoints The track points for an activity.
     */
    public void setTrackPoints(@JsonProperty("track points")int[] trackPoints) {
        this.trackPoints = trackPoints;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", activity='" + activity + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", duration=" + duration +
                ", distance=" + distance +
                ", steps=" + steps +
                ", calories=" + calories +
                ", trackPoints=" + Arrays.toString(trackPoints) +
                '}';
    }

}
