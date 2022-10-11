package com.project.quantifiedself.read;

import com.project.quantifiedself.model.Activity;
import com.project.quantifiedself.model.Location;
import com.project.quantifiedself.model.Place;
import com.project.quantifiedself.model.Segment;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

/**
 * PlaceProcessor iterates through the entries list to retrieve the Place objects from a segment in an Entry to create
 * a list of places.
 */
public class SegmentProcessor {

    private JSONObject jsonObject;
    private List<Activity> activities;
    private List<Place> places;

    /**
     * Constructor for a segment.
     *
     * @param jsonObject The json object to parse.
     */
    public SegmentProcessor(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        this.activities = new LinkedList();
        this.places = new LinkedList();
    }

    /**
     * Method parses the class variable jsonObject to parse and create a Segment.
     *
     * @param date The date of a the segment.
     * @return A Segment object.
     * @throws Exception
     */
    public Segment createSegment(LocalDate date) throws Exception {
        // initialize segment
        Segment segment = new Segment();

        // get type
        String type = (String) jsonObject.get("type");

        // get startTime
        String start = (String) jsonObject.get("startTime");

        // get endTime
        String end = (String) jsonObject.get("endTime");

        // build
        if (type.equals("place")) {
            // create a JSON object to be parsed, get place attribute
            JSONObject placeJSON = (JSONObject) jsonObject.get("place");
            Place place = createPlace(placeJSON, date);
            // get activities to be parsed in place
            JSONArray activityJsonArray1 = (JSONArray) jsonObject.get("activities");
            // if the json array is not null then parse activities
            List<Activity> placeActivities = new LinkedList();
            if (activityJsonArray1 != null) {
                // for each activity in activityJsonArray1, parse each activity object
                for (Object jsonActivity : activityJsonArray1) {
                    ActivityProcessor activityProcessor = new ActivityProcessor((JSONObject) jsonActivity);
                    placeActivities.add(activityProcessor.createActivity(date));
                    activities.add(activityProcessor.createActivity(date));
                }
            }
            // Build Segment object
            segment.setType(type);
            segment.setStartTime(start);
            segment.setEndTime(end);
            segment.setActivities((placeActivities));
            segment.setPlace(place);
            return segment;
        }
        // build a segment with type move
        else if (type.equals("move")) {
            // get activities to be parsed in move
            JSONArray activityJsonArray2 = (JSONArray) jsonObject.get("activities");
            List<Activity> moveActivities = new LinkedList();
            for (Object jsonActivity : activityJsonArray2) {
                ActivityProcessor activityProcessor = new ActivityProcessor((JSONObject) jsonActivity);
                moveActivities.add(activityProcessor.createActivity(date));
                activities.add(activityProcessor.createActivity(date));
            }
            segment.setType(type);
            segment.setStartTime(start);
            segment.setEndTime(end);
            segment.setActivities((moveActivities));
            return segment;
        }
        return null;
    }

    /**
     * Method parses a json object and creates a place.
     *
     * @param placeJSON The json object that has a place type.
     * @param date The date of when the place was visited.
     * @return A place object.
     */
    public static Place createPlace(JSONObject placeJSON, LocalDate date) {
        Place place = new Place();
        Location location = new Location();
        // get name
        String name = (String) placeJSON.get("name");

        // get type
        String type = (String) placeJSON.get("type");

        // get four square id
        String fourSquareId = (String) placeJSON.get("foursquareId");


        Double latitude = 0.0;
        Double longitude = 0.0;

        // get longitude
        // if the location-lat attribute is a number
        if (((JSONObject) (placeJSON.get("location"))).get("lat") instanceof Number) {
            // then latitude is equal to the value of the lat attribute
            latitude = ((Number) ((JSONObject) (placeJSON.get("location"))).get("lat")).doubleValue();
        }
        if (((JSONObject) (placeJSON.get("location"))).get("lon") instanceof Number) {
            // then longitude is equal to the value of the lon attribute
            longitude = ((Number) ((JSONObject) (placeJSON.get("location"))).get("lon")).doubleValue();
        }

        // set location fields
        location.setLat(latitude);
        location.setLon(longitude);

        // set place fields
        place.setDate(date);
        place.setPlace(name);
        place.setType(type);
        place.setLocation(location);
        if (fourSquareId != null) {
            place.setFourSquareId(fourSquareId);
        }
        return place;
    }

    /**
     * Method creates a place when given a parameters. Supports POST feature in REST api and allows the user to
     * add a place to the place collection.
     *
     * @param date The date of when the place was visited.
     * @param place The name of the place.
     * @param type The type of place.
     * @param latitude The latitude of the place, which will be used to create a Location object.
     * @param longitude The longitude of the place, which will be used to create a Location object.
     * @param fourSquareID The fourSquareID of the place, if provided.
     * @return A Place object.
     */
    public static Place createPlace(String date, String place, String type, Double latitude, Double longitude,
                                    String fourSquareID) {
        Place newPlace = new Place();
        Location location = new Location();
        LocalDate parsedDate = LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE);

        // set location fields
        location.setLat(latitude);
        location.setLon(longitude);

        // set place fields
        newPlace.setDate(parsedDate);
        newPlace.setPlace(place);
        newPlace.setType(type);
        newPlace.setLocation(location);
        if (fourSquareID != null || !fourSquareID.equals("")) {
            newPlace.setFourSquareId(fourSquareID);
        } else {
            newPlace.setFourSquareId("");
        }
        return newPlace;
    }

}
