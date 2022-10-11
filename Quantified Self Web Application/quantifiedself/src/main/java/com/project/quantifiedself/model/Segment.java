package com.project.quantifiedself.model;

import java.util.List;

/**
 * Segment is a segment in the segments list in an Entry object.
 */
public class Segment implements ISegment {

    private String type;
    private String startTime;
    private String endTime;
    private Place place;
    private List<Activity> activities;

    /**
     * Constructor for Segments list.
     *
     * @param type The type of segment.
     * @param startTime The start time of a segment.
     * @param endTime The end time of a segment.
     * @param place The place of a segment.
     * @param activities The list of activities in a segment.
     */
    public Segment(String type, String startTime, String endTime, Place place, List<Activity> activities) {
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.place = place;
        this.activities = activities;
    }

    /**
     * Constructor for Segment.
     */
    public Segment() {
    }

    /**
     * Method gets the type of segment.
     *
     * @return The type of segment.
     */
    public String getType() {
        return type;
    }

    /**
     * Method sets the type of segment, which could be a move or a place.
     *
     * @param type Tne type of segment.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Method gets the start time of the segment.
     *
     * @return The start time of the segment.
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Method sets the start time of the segment.
     *
     * @param startTime The start time of the segment.
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * Method gets the end time of the segment.
     *
     * @return The end time of the segment.
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Method sets the end time of the segment.
     *
     * @param endTime The end time of a the segment.
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * Method gets the name of the segment.
     *
     * @return The name of the segment.
     */
    public Place getPlace() {
        return place;
    }

    /**
     * Method sets the place of the segment.
     *
     * @param place The name of the place in the segment.
     */
    public void setPlace(Place place) {
        this.place = place;
    }

    /**
     * Method gets the list of activities in a segment.
     *
     * @return The activities in a given segment.
     */
    public List<Activity> getActivities() {
        return activities;
    }

    /**
     * Method sets the list of activities in a segment.
     *
     * @param activities The list of activities in a segment.
     */
    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

}
