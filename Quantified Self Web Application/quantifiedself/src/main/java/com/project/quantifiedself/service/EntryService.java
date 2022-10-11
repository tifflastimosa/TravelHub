package com.project.quantifiedself.service;

import com.project.quantifiedself.model.Entry;
import com.project.quantifiedself.read.JsonFileParser;
import com.project.quantifiedself.repositories.activityweb.ActivityCaloriesPerYear;
import com.project.quantifiedself.repositories.activityweb.ActivityCountPerYear;
import com.project.quantifiedself.repositories.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * EntryService provides a layer for HTTP requests.  This is where the business logic is implemented to make the
 * HTTP requests.
 */
@Service
public class EntryService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private EntryRepository entryRepository;

    // GET Requests
    /**
     * Method makes the request to get all entries from the database.
     *
     * @return A list of all entries from the entries collection.
     */
    public List<Entry> getAllEntries() {
        return this.entryRepository.findAll();
    }

    // POST Requests
    /**
     * Method takes in a JSONArray.  It is important to note that if the json array is large, then
     * with the HTTP request it will take a longer time to load.
     *
     * @param jsonDoc JSON array of json objects to be added to the entries collection.
     */
    public void batchInsert(String jsonDoc) {
        JsonFileParser parser = new JsonFileParser(jsonDoc);
        parser.readJSON();
        for (Entry entry : parser.getEntriesList()) {
            this.entryRepository.insert(entry);
        }
    }

    /**
     * Methods counts the number of activities in a given year and based on activities.
     *
     * @return A collection ActivityCountPerYear objects that contains the activity count per year.
     */
    public List<ActivityCountPerYear> findActivityCount(){
        UnwindOperation unwind1 = unwind("segments");
        UnwindOperation unwind2 = unwind("segments.activities");
        ProjectionOperation project1 = project("segments.activities.activity", "year");
        ProjectionOperation project2 =
                project("date").andExpression("substr(date, 0, 4)").as("year").andInclude("segments" +
                        ".activities.activity");
        ProjectionOperation project3 = project("activities.activity").andInclude("activities" +
                ".activity");
        GroupOperation group1 = group("activities.activity", "year").count().as("count");
        GroupOperation group2 = group("year");
        LimitOperation limit1 = limit(10);
        Aggregation aggregation = Aggregation.newAggregation(unwind1, unwind2,
                project2, group1);
        AggregationResults<ActivityCountPerYear> result1 =
                mongoTemplate.aggregate(aggregation, "entries", ActivityCountPerYear.class);
        System.out.println(result1);
        List<ActivityCountPerYear> mappedResult = result1.getMappedResults();
        return mappedResult;
    }

    /**
     * Method calculates the number of calories per year.
     *
     * @return A list of calories per year.
     */
    public List<ActivityCaloriesPerYear> findTotalCaloriesPerYear() {
        UnwindOperation unwind1 = unwind("segments");
        UnwindOperation unwind2 = unwind("segments.activities");
        ProjectionOperation p =
                project("date").andExpression("substr(date, 0, 4)").as("year").andInclude("segments" +
                        ".activities.activity").andInclude("segments.activities.calories");
        GroupOperation group3 = group("activities.activity", "year").sum("activities.calories").as(
                "calories");
        Aggregation aggregation1 = Aggregation.newAggregation(unwind1, unwind2,
                p, group3);
        AggregationResults<ActivityCaloriesPerYear> result2 =
                mongoTemplate.aggregate(aggregation1, "entries", ActivityCaloriesPerYear.class);
        System.out.println(result2);
        List<ActivityCaloriesPerYear> mappedResult2 = result2.getMappedResults();
        return mappedResult2;
    }

}
