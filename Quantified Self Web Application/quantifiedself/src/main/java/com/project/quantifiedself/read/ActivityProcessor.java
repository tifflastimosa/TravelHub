package com.project.quantifiedself.read;

import com.project.quantifiedself.model.Activity;
import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Method creates an Activity object and maps out the values when given either a JSONObject or params from a user in
 * the REST api.
 */
public class ActivityProcessor {

    // want to take in a JSONObject
    private JSONObject jsonObject;

    /**
     * Constructor for Activity Processor.
     *
     * @param jsonObject A single json object to parse and create an Activity.
     */
    public ActivityProcessor(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    /**
     * Method creates an Activity.
     *
     * @param date The date to associate with the activity.
     * @return An Activity object given values from the json object.
     */
    public Activity createActivity(LocalDate date) {
        // get activity
        String jsonActivity = (String) jsonObject.get("activity");

        // get startTime
        String start = (String) jsonObject.get("startTime");

        // get endTime
        String end = (String) jsonObject.get("endTime");

        // get duration
        Double duration = 0.0;
        if (jsonObject.get("duration") instanceof Number) {
            duration = ((Number) jsonObject.get("duration")).doubleValue();
        }

        // get distance
        Double distance = 0.0;
        // if the distance attribute is a number, then the value of the double distance is equal to the value
        // of the distance attribute
        if (jsonObject.get("distance") instanceof Number) {
            distance = ((Number) jsonObject.get("distance")).doubleValue();
        }

        // initialize steps, calories, and trackPoints attributes
        Double steps = 0.0;
        Double calories = 0.0;

        Activity activity = new Activity();

        int[] trackPoints = new int[0];

        activity.setDate(date);

        activity.setActivity(jsonActivity);
        activity.setStartTime(start);
        activity.setEndTime(end);
        activity.setDuration(duration);
        activity.setDistance(distance);
        activity.setTrackPoints(trackPoints);

        if (!activity.equals("transport")) {
            if (activity.getActivity().equals("walking") || activity.getActivity().equals("running")) {
                steps = ((Number) jsonObject.get("steps")).doubleValue();
                activity.setSteps(steps);
            }
            if (jsonObject.get("calories") instanceof Number) {
                calories = ((Number) jsonObject.get("calories")).doubleValue();
                activity.setCalories(calories);
            }
        }
        return activity;
    }

    /**
     * Method creates an activity when given parameters. Used to support POST request in REST api.  Allows the user to
     * add an activity to the collection.
     *
     * @param date      The date of the activity.
     * @param activity  The name of the activity.
     * @param startTime The start time of the activity.
     * @param endTime   The end time of the activity.
     * @param duration  The duration of the activity.
     * @param distance  The distance of the activity.
     * @param steps     If walking or running, the amount of steps taken in the activity.
     * @param calories  The number of calories burned in an activity.
     * @return An activity object.
     */
    public static Activity createActivity(String date, String activity, String startTime, String endTime, Double duration,
                                          Double distance, Double steps, Double calories) {
        Activity newActivity = new Activity();

        LocalDate parsedDate = LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE);

        int[] trackPoints = new int[0];

        newActivity.setDate(parsedDate);
        newActivity.setActivity(activity);
        newActivity.setStartTime(startTime);
        newActivity.setEndTime(endTime);
        newActivity.setDuration(duration);
        newActivity.setDistance(distance);
        newActivity.setTrackPoints(trackPoints);

        if (newActivity.getActivity().equals("walking") || newActivity.getActivity().equals("running")) {
            newActivity.setSteps(steps);
        }
        if (calories != null) {
            newActivity.setCalories(calories);
        }
        return newActivity;
    }

}
