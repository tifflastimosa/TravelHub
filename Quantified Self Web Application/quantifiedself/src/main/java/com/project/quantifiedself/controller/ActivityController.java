package com.project.quantifiedself.controller;

import com.project.quantifiedself.model.Activity;
import com.project.quantifiedself.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

/**
 * ActivityController is a class that provides HTTP requests to get information from the database .
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    // GET Requests

    /**
     * Method is a GET request that gets all activities in the activities collection in the database.
     *
     * @return The list of all activities in the activities collection in the database.
     */
    @GetMapping(value = "/activities")
    public ResponseEntity<List<Activity>> getAll() {
        List<Activity> activities = this.activityService.getAll();
        if (!activities.isEmpty()) {
            return new ResponseEntity<>(activities, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Method is a GET request that gets activities by activity.
     *
     * @param activity The activity to get from the activities collection.
     * @return A collection of activities when given a list of activities.
     */
    @GetMapping(value = "/activities/{activity}")
    public ResponseEntity<List<Activity>> getAllActivities(@PathVariable String activity) {
        List<Activity> activities = this.activityService.getAllActivities(activity);
        if (!activities.isEmpty()) {
            return new ResponseEntity<>(activities, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    // POST Request
    /**
     * Method that adds an Activity to the activities collection in the database.
     *
     * @param date      The date of the activity.
     * @param activity  The activity performed.
     * @param startTime The start time of the activity.
     * @param endTime   The end time of the activity.
     * @param duration  The duration of the activity.
     * @param distance  The distance of the activity.
     * @param steps     The number of steps, if the activity is walking or running.
     * @param calories  The number of calories burned in an activity.
     * @return When successfully created and added activity to the activities collection, then returns the id
     * of the new activity added to the database, otherwise returns a 400 Bad Request when the date is invalid.
     */
    @PostMapping(value = "/activities/add")
    public ResponseEntity<String> addActivity(@RequestParam(value = "date") String date,
                                              @RequestParam(value = "activity") String activity,
                                              @RequestParam(value = "startTime", required = false) String startTime,
                                              @RequestParam(value = "endTime", required = false) String endTime,
                                              @RequestParam(value = "duration") Double duration,
                                              @RequestParam(value = "distance") Double distance,
                                              @RequestParam(value = "steps", required = false) Double steps,
                                              @RequestParam(value = "calories", required = false) Double calories) {
        try {
            LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(this.activityService.addActivity(
                date, activity.toLowerCase(Locale.ROOT), startTime, endTime, duration, distance, steps, calories), HttpStatus.OK);
    }

    /**
     * Method that adds an Activity to the activities collection in the database.
     *
     * @param date      The date of the activity.
     * @param activity  The activity performed.
     * @param startTime The start time of the activity.
     * @param endTime   The end time of the activity.
     * @param duration  The duration of the activity.
     * @param distance  The distance of the activity.
     * @param steps     The number of steps, if the activity is walking or running.
     * @param calories  The number of calories burned in an activity.
     * @return When successfully created and added activity to the activities collection, then returns the id
     * of the new activity added to the database, otherwise returns a 400 Bad Request when the date is invalid.
     */
    @PutMapping(value = "/activities/update/{id}")
    public ResponseEntity<String> updateActivity(@PathVariable("id") String id,
                                                 @RequestParam(value = "date") String date,
                                                 @RequestParam(value = "activity") String activity,
                                                 @RequestParam(value = "startTime", required = false) String startTime,
                                                 @RequestParam(value = "endTime", required = false) String endTime,
                                                 @RequestParam(value = "duration") Double duration,
                                                 @RequestParam(value = "distance") Double distance,
                                                 @RequestParam(value = "steps", required = false) Double steps,
                                                 @RequestParam(value = "calories", required = false) Double calories) {
        try {
            LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(this.activityService.updateActivity(id,
                date, activity.toLowerCase(Locale.ROOT), startTime, endTime, duration, distance, steps, calories), HttpStatus.OK);
    }


    /**
     * Method deletes an activity from the activities collection.
     *
     * @param id The id of the Activity object to be deleted.
     * @ Returns the Http status of the request.
     */
    @DeleteMapping(value = "/activities/delete/{id}")
    public ResponseEntity<HttpStatus> deletePlace(@PathVariable("id") String id) {
        try {
            this.activityService.deleteActivity(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
