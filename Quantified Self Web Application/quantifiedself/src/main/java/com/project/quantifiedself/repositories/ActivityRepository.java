package com.project.quantifiedself.repositories;

import com.project.quantifiedself.model.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends MongoRepository<Activity, String> {

    /**
     * Method gets a collection of activities when given an activity.
     *
     * @param activity The activity to get from the activities collection.
     * @return A filtered collection of activities from the activities collection.
     */
    List<Activity> findByActivity(String activity);

    /**
     * Method gets a collection of activities when given a date.
     *
     * @param date The date of the activity to get from the activities collection.
     * @return A filtered collection of activities from the activities collection.
     */
    List<Activity> findByDate(LocalDate date);

    /**
     * Method finds an activity when given an activity.
     *
     * @param id The id of the activity to get from the activities collection.
     * @return An Optional, container object, of Activity.
     */
    Optional<Activity> findAllById(String id);

    /**
     * Method deletes an activity when given an id.
     *
     * @param id The id of the activity to delete  from the activities collection.
     */
    void deleteById(String id);

}
