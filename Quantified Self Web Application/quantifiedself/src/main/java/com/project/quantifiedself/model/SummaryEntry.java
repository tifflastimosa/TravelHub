package com.project.quantifiedself.model;

import java.util.NoSuchElementException;

public class SummaryEntry implements ISummaryEntry {

    private String activity;
    private String group;
    private Double duration;
    private Double distance;
    private Double calories;
    private Double steps;

    public SummaryEntry(String activity, String group, Double duration, Double distance) {
        this.activity = activity;
        this.group = group;
        this.duration = duration;
        this.distance = distance;
    }

    public String getActivity() {
        return activity;
    }

    public String getGroup() {
        return group;
    }

    public Double getDuration() {
        return duration;
    }

    public Double getDistance() {
        return distance;
    }

    public Double getCalories() {
        try {
            return calories;
        }
        catch (NullPointerException e) {
            throw new NoSuchElementException("This entry does not have calories.");
        }
    }

    public void setCalories() {
        if (!this.activity.equals("walking") && !this.activity.equals("running")) {
            throw new NoSuchElementException("This activity does not use steps.");
        }
    }

    public void setSteps() {
        if (!this.activity.equals("walking") && !this.activity.equals("running")) {
            throw new NoSuchElementException("This activity does not use steps.");
        }
    }

    public Double getSteps() {
        try {
            return steps;
        }
        catch (NullPointerException e) {
            throw new NoSuchElementException("This entry does not have steps.");
        }
    }

}
