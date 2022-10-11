package com.project.quantifiedself.read;

import com.project.quantifiedself.model.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;

/**
 * Class parses a json file to map out the json objects to insert into the database.
 */
public class JsonFileParser {

    // the json doc to be parsed
    private String jsonDoc;
    // contains a list of entries - represents a json object
    private List<Entry> entriesList;
    // each json object contains segments
    // parse activities in both move and place segments to get all activities in the json array
    private static List<Activity> activitiesList;
    private static List<Place> placesList;

    /**
     * Constructor for a JsonFileParser.  This constructor supports the feature of adding a json array into the
     * database.
     *
     * @param jsonDoc String json object.
     */
    public JsonFileParser(String jsonDoc) {
        this.jsonDoc = jsonDoc;
        this.entriesList = new LinkedList();
        this.activitiesList = new LinkedList();
        this.placesList = new LinkedList();
    }

    /**
     * Constructor for a JsonFilerParser.
     */
    public JsonFileParser() {
        this.entriesList = new LinkedList();
        this.activitiesList = new LinkedList();
        this.placesList = new LinkedList();
    }

    /**
     * Method parses the date string and converts it to a LocalDate object.
     *
     * @param date The date to parse.
     * @return A LocalDate object of the String date.
     */
    private static LocalDate parseDate(String date) {
        LocalDate parsedDate;

        try {
            parsedDate = LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE);
        } catch (DateTimeParseException e) {
            throw e;
        }
        return parsedDate;
    }

    /**
     * Method reads in a json array.  Supports the REST feature for inserting a json array into the database.
     */
    public void readJSON() {
        // create a parser using the simple json library that will parse the json document
        JSONParser parser = new JSONParser();
        try {
            // parse the json objects in the json array
            Object object = parser.parse(this.jsonDoc);
            // iterates through each json object in the json array
            JSONArray dataList = (JSONArray) object;
            // for each object in the json array, call parse json data object.

            dataList.forEach(dataObject -> {
                try {
                    parseDataObject((JSONObject) dataObject);
                } catch (Exception invalidSegmentCreation) {
                    invalidSegmentCreation.printStackTrace();
                }
            });
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method reads in a .json file.  Supports the feature for taking in a .json file inputted by the user to insert
     * into the database at the start of the web application.
     *
     * @param jsonFile The .json file to parse and add to the database.
     * @throws IOException An exception is thrown when there's an invalid file input.
     */
    public void readJSON(String jsonFile) throws IOException {
        // create a parser using the simple json library that will parse the json document
        File jsonFileResource = new ClassPathResource(jsonFile).getFile();
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(jsonFileResource)) {
            // parse the json objects in the json array
            Object object = parser.parse(reader);
            // iterates through each json object in the json array
            JSONArray dataList = (JSONArray) object;
            // for each object in the json array, call parse json data object.
            dataList.forEach(dataObject -> {
                try {
                    parseDataObject((JSONObject) dataObject);
                } catch (Exception invalidSegmentCreation) {
                    invalidSegmentCreation.printStackTrace();
                }
            });
        } catch (ParseException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that gets the list of entries after json array or json object is parsed.
     *
     * @return A list of entries after the json array is parsed.
     */
    public List<Entry> getEntriesList() {
        return this.entriesList;
    }

    /**
     * Method that gets a list of activities from the segments attribute (both move and place segments).  This provides
     * all the activities from the json array.
     *
     * @return A list of all activities in the json array.
     */
    public List<Activity> getActivitiesObject() {
        return this.activitiesList;
    }

    public List<Place> getPlacesList() {
        return this.placesList;
    }

    /**
     * Helper method that calls the parseEntry to parse the json object and adds the Entry object returned from
     * parseEntry into the entries list.
     *
     * @param dataObject The json object to be parsed in the json array.
     */
    private void parseDataObject(JSONObject dataObject) throws Exception {
        this.entriesList.add(parseEntry(dataObject));
    }

    /**
     * Helper method that parses a json object and maps it as an Entry.
     *
     * @param dataObject The json object to parse.
     * @return An Entry object.
     */
    public static Entry parseEntry(JSONObject dataObject) throws Exception {
        Entry entry = new Entry();
        // get date
        String entryDate = (String) dataObject.get("date");
        // converts the date into a local date object
        LocalDate parsedDate = parseDate(entryDate);

        // get caloriesIdle attribute
        Double caloriesIdle = null;
        if (dataObject.get("caloriesIdle") instanceof Number) {
            caloriesIdle = ((Number) dataObject.get("caloriesIdle")).doubleValue();
        }

        // get lastUpdate attribute
        String lastUpdate = (String) dataObject.get("lastUpdate");

        List<ISummaryEntry> summariesList = new LinkedList<>();

        JSONArray summariesJsonArray = (JSONArray) dataObject.get("summary");

        if (summariesJsonArray != null) {
            summariesJsonArray.forEach(summary -> parseSummary((JSONObject) summary, summariesList));
        }

        // get segments attributes is an array of segments that needs to be iterated and parsed
        JSONArray segmentsJsonArray = (JSONArray) dataObject.get("segments");
        List<Segment> segmentList = new LinkedList<>();
        if (segmentsJsonArray != null) {
            // parse the segments attribute - for each segment in segments list
//            segmentsJsonArray.forEach(segment -> segmentProcessor((JSONObject) segment, segmentList, parsedDate));
            for (Object segmentObject : segmentsJsonArray) {
                SegmentProcessor segmentProcessor = new SegmentProcessor((JSONObject) segmentObject);
                Segment segment = segmentProcessor.createSegment(parsedDate);
                segmentList.add(segment);
                if (segment != null) {
                    createActivitiesList(segment);
                    createPlacesList(segment);
                }
            }
        }
        entry.setDate(parsedDate);
        entry.setCaloriesIdle(caloriesIdle);
        entry.setLastUpdate(lastUpdate);
        entry.setSummaries(summariesList);
        entry.setSegments(segmentList);
        return entry;
    }

    private static void createActivitiesList(Segment segment) {
        for (Activity activity : segment.getActivities()) {
            activitiesList.add(activity);
        }
    }

    private static void createPlacesList(Segment segment) {
        if (segment.getType().equals("place")) {
            placesList.add(segment.getPlace());
        }
    }

    private static void parseSummary(JSONObject summaryObject, List<ISummaryEntry> summariesList) {

        // get activity
        String activity = (String) summaryObject.get("activity");

        // get group
        String group = (String) summaryObject.get("group");

        // get duration
        double dur = 0;
        if (summaryObject.get("duration") instanceof Number) {
            dur = ((Number) summaryObject.get("duration")).doubleValue();
        }

        // get distance
        double distance = 0;
        // if the distance attribute is a number, then the value of the double distance is equal to the value
        // of the distance attribute
        if (summaryObject.get("distance") instanceof Number) {
            distance = ((Number) summaryObject.get("distance")).doubleValue();
        }

        // initialize steps, calories attributes
        Double steps = null;
        Double calories = null;


        // if the activity attribute is equal to either walking or cycling
        //  TODO: review this part because calories is not setting as it should
        if (!activity.equals("transport")) {
            // if calories is a number, then the value of double calories is equal to the value of the calories
            // attribute
            if (summaryObject.get("calories") instanceof Number) {
                calories = ((Number) summaryObject.get("calories")).doubleValue();
            }

            // if walking activity get steps and calories
            if (activity.equals("walking")) {
                // if steps attribute is a number, then the value of the double steps is equal to the value of steps
                if (summaryObject.get("steps") instanceof Number) {
                    steps = ((Number) summaryObject.get("steps")).doubleValue();
                }
                // add the walking object to the activities list

                summariesList.add(new SummaryEntry(activity, group, dur, distance));
            }
            // otherwise add cycling object to the activities list
            else {
                summariesList.add(new SummaryEntry(activity, group, dur, distance));

            }
        }
        // otherwise add new transport to the activities list
        else {

            summariesList.add(new SummaryEntry(activity, group, dur, distance));
        }
    }

}
