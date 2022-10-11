package com.project.quantifiedself.service;

import com.project.quantifiedself.model.Activity;
import com.project.quantifiedself.repositories.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static com.project.quantifiedself.read.ActivityProcessor.createActivity;

/**
 * ActivityService is a class that provides a layer between the controller and the repository
 * to process HTTP requests.
 */
@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    /**
     * Method GETS all activities from the activities collection.
     *
     * @return A list of activities from the activities collection.
     */
    public List<Activity> getAll() {
        return this.activityRepository.findAll();
    }

    /**
     * Method GETS all activities on given activity.
     *
     * @param activity The activity to get from the activities collection.
     * @return A collection of activities when given a list of activities.
     */
    public List<Activity> getAllActivities(String activity) {
        return this.activityRepository.findByActivity(activity);
    }

    /**
     * Method creates and adds activity to the activities collection.
     *
     * @param date      The date of the activity.
     * @param activity  The activity type.
     * @param startTime The start time of the activity.
     * @param endTime   The end time of the activity.
     * @param duration  The length of the activity.
     * @param distance  The distance of the activity.
     * @param steps     The steps of an activity.
     * @param calories  The calories burned of an activity.
     * @return The id of the new activity added to the activities collection.
     */
    public String addActivity(String date, String activity, String startTime, String endTime, Double duration,
                              Double distance, Double steps, Double calories) {
        Activity newActivity = createActivity(date, activity, startTime, endTime, duration, distance, steps, calories);
        this.activityRepository.save(newActivity);
        return newActivity.getId();
    }

    /**
     * Method updates an Activity object into the places collection.
     *
     * @param id        The id of the Activity object in the activities collection.
     * @param date      The date of the activity.
     * @param activity  The activity type.
     * @param startTime The start time of the activity.
     * @param endTime   The end time of the activity.
     * @param duration  The length of the activity.
     * @param distance  The distance of the activity.
     * @param steps     The steps of an activity.
     * @param calories  The calories burned of an activity.
     * @return The activity of the updated activity8.
     */
    public String updateActivity(String id, String date, String activity, String startTime, String endTime, Double duration,
                                 Double distance, Double steps, Double calories) {
        Optional<Activity> activityData = this.activityRepository.findById(id);
        Activity updatedActivity = activityData.get();
        int[] trackPoints = new int[0];
        LocalDate parsedDate = LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE);
        updatedActivity.setDate(parsedDate);
        updatedActivity.setActivity(activity);
        updatedActivity.setStartTime(startTime);
        updatedActivity.setEndTime(endTime);
        updatedActivity.setDuration(duration);
        updatedActivity.setDistance(distance);
        updatedActivity.setTrackPoints(trackPoints);
        if (updatedActivity.getActivity().equals("walking") || updatedActivity.getActivity().equals("running")) {
            updatedActivity.setSteps(steps);
        }
        if (calories != null) {
            updatedActivity.setCalories(calories);
        }
        this.activityRepository.save(updatedActivity);
        return updatedActivity.getActivity();
    }

    /**
     * Method deletes an activity from the activities collection.
     *
     * @param id The id of the activity to delete from the activities collection.
     */
    public void deleteActivity(String id) {
        this.activityRepository.deleteById(id);
    }

}
