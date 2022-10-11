package edu.neu.madcourse.pettin.Classes;

public class RestaurantModel {

    private String course_name;
    private double course_rating;
    private String course_image;
    private String course_address;
    private String category;
    private String distance;
    private String longtitude;
    private String latitude;
    // Constructor

    public RestaurantModel(String title, double course_rating,String category, String distance,String address,String course_image,String longtitude,String latitude) {
        this.course_name = title;
        this.course_rating = course_rating;
        this.category = category;
        this.distance = distance;
        this.course_image = course_image;
        this.course_address = address;
        this.longtitude = longtitude;
        this.latitude = latitude;
    }

    public String getCourse_name() {
        return course_name;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public double getCourse_rating() {
        return course_rating;
    }

    public String getCourse_image() {
        return course_image;
    }

    public String getCourse_address() {
        return course_address;
    }

    public String getCategory() {
        return category;
    }

    public String getDistance() {
        return distance;
    }
}