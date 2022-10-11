package com.project.quantifiedself.repositories.activityweb;

import lombok.Data;

/**
 * Class that builds the ActivityCaloriesPerYear.
 * @Data creates the constructor, getters, and setters of ActivityCaloriesPerYear object.
 */
@Data
public class ActivityCaloriesPerYear {

    public ActivityYear _id;
    private double calories;

}
