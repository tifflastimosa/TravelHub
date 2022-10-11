package com.project.quantifiedself.repositories.activityweb;

import lombok.Data;

/**
 * ActivityCountPerYear class that gets the activity count per year.
 */
@Data
public class ActivityCountPerYear {

    public ActivityYear _id;
    public int count;

    /**
     * Method gets the id for a an ActivityYear.
     *
     * @return The id of the ActivityYear class.
     */
    public ActivityYear get_id() {
        return _id;
    }

    /**
     * Method that gets the count for a given year.
     *
     * @return The count of the given year.
     */
    public int getCount() {
        return count;
    }

}
