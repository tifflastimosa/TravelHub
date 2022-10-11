package com.project.quantifiedself.controller;

import com.project.quantifiedself.model.Entry;

import com.project.quantifiedself.repositories.activityweb.ActivityCaloriesPerYear;
import com.project.quantifiedself.repositories.activityweb.ActivityCountPerYear;
import com.project.quantifiedself.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * EntryController allows the client to make http requests to the database.  Interacts with the client, handles
 * HTTP requests and sends a response back to the caller.
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class EntryController {

    @Autowired
    private EntryService entryService;

    // GET Requests - retrieves data from the entries collection
    /**
     * Method gets all entries from the database.
     * Example Input: http://localhost:8080/entries
     *
     * @return A list of all entries from the database.
     */
    @GetMapping("/entries")
    public ResponseEntity<List<Entry>> getAllEntries() {
        return new ResponseEntity<>(this.entryService.getAllEntries(), HttpStatus.OK);
    }

    /**
     * Method GETS the total number of activities per year.
     *
     * @return A list of activities with an associated count per year.
     */
    @GetMapping("/entries/activity/count")
    public List<ActivityCountPerYear> getActivityCount(){
        return this.entryService.findActivityCount();
    }

    /**
     * Method GETS the total number calories per year.
     *
     * @return A list of years containing the number of calories burned in the year.
     */
    @GetMapping("/entries/activity/calories")
    public List<ActivityCaloriesPerYear> getActivityCalories(){
        return this.entryService.findTotalCaloriesPerYear();
    }

    // POST Requests - adds entry or entries to the entries collection
    /**
     * Method adds a list of entries into the entries collection in the database.  This feature allows multiple entries,
     * or in this case a JSONArray to be added to the entries collection in the database.
     *
     * @param body Raw JSON containing a list of entries as a string to insert into the database.
     * @return A string message that says all entries were added to the database.
     */
    // if it contains 1600+ elements, the request takes a long time
    @PostMapping("/entries/save/jsonarray")  // tested in Postman - works
    public String processEntries(@RequestBody String body) {
        this.entryService.batchInsert(body);
        return "All entries added to database";
    }

}