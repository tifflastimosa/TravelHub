package com.project.quantifiedself.model;

/**
 * Location class that contains the latitude and longitude for a place.
 */

public class Location {

    private double lat;
    private double lon;

    /**
     * Constructor for the Location object.
     *
     * @param lat A coordinate value for a place, it is a measurement of distance north or south of the equator.
     * @param lon A coordinate value for a place, it is a measurement of distance south or west of the equator.
     */
    public Location(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    /**
     * Constructor for the Location object.
     */
    public Location() {
    }

    /**
     * Method that gets the latitude of the location.
     *
     * @return The latitude of the place.
     */
    public double getLat() {
        return lat;
    }

    /**
     * Method that sets the latitude of the location.
     *
     * @param lat The latitude of the place.
     */
    public void setLat(double lat) {
        this.lat = lat;
    }

    /**
     * Method that gets the longitude of the location.
     *
     * @return The longitude of the location.
     */
    public double getLon() {
        return lon;
    }

    /**
     * Method that sets the longitude of the location
     *
     * @param lon The longitude of the location.
     */
    public void setLon(double lon) {
        this.lon = lon;
    }

}
