package com.project.quantifiedself.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

/**
 * Constructs an entry object to insert segments.
 */
@Document(collection = "entries")
public class Entry {

    @Id
    private String id;
    @Indexed(unique = true)
    private LocalDate date;
    private Double caloriesIdle;
    private String lastUpdate;
    private List<Segment> segments;
    private List<ISummaryEntry> summaries;

    /**
     * Constructor for an entry, which is a result from parsing the json array.
     *
     * @param date The date of the entry.
     * @param caloriesIdle Calories idle for an entry.
     * @param lastUpdate The last update of the json object.
     * @param segments The list of segments that contains activities and places.
     * @param summaries The list of summaries that contains a summary of segments.
     */
    public Entry(LocalDate date, Double caloriesIdle, String lastUpdate, List<Segment> segments, List<ISummaryEntry> summaries) {
        this.date = date;
        this.caloriesIdle = caloriesIdle;
        this.lastUpdate = lastUpdate;
        this.segments = segments;
        this.summaries = summaries;
    }

    /**
     * Constructor for an Entry.
     */
    public Entry() {
    }

    /**
     * Method gets an id of the Entry object when added to the entries collection.
     *
     * @return The id of the Entry object, which is generated when the Entry is added to the entries collection.
     */
    public String getId() {
        return id;
    }

    /**
     * Method that gets the date for the entry.
     *
     * @return The date of the entry.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Method that sets the date of the entry.
     *
     * @param date The date of the entry.
     */
    public void setDate(@JsonProperty("date")LocalDate date) {
        this.date = date;
    }

    /**
     * Method that gets the calories idle.
     *
     * @return The calories idle from the entry.
     */
    public Double getCaloriesIdle() {
        return caloriesIdle;
    }

    /**
     * Method sets the calories.
     *
     * @param caloriesIdle The calories idle for an entry.
     */
    public void setCaloriesIdle(@JsonProperty("caloriesIdle")Double caloriesIdle) {
        this.caloriesIdle = caloriesIdle;
    }

    /**
     * Method that gets the last update for the entry.
     * @return
     */
    public String getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Method that sets the last update of the entry.
     *
     * @param lastUpdate The last update of the entry.
     */
    public void setLastUpdate(@JsonProperty("lastUpdate")String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Method that gets the list of segments from the Entry.
     *
     * @return The list of segments in the Entry object.
     */
    public List<Segment> getSegments() {
        return segments;
    }

    /**
     * Method that sets the segments.
     *
     * @param segments The list of segments for an Entry object.
     */
    public void setSegments(@JsonProperty("segments")List<Segment> segments) {
        this.segments = segments;
    }

    /**
     * Method that gets the summaries for an entry.
     *
     * @return The list of summaries for an entry.
     */
    public List<ISummaryEntry> getSummaries() {
        return summaries;
    }

    /**
     *  Method that sets the list of summaries for the entry.
     *
     * @param summaries The list of summaries from the entry object.
     */
    public void setSummaries(@JsonProperty("summary")List<ISummaryEntry> summaries) {
        this.summaries = summaries;
    }
}
