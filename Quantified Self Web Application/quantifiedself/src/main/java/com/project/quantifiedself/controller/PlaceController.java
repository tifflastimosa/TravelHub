package com.project.quantifiedself.controller;

import com.project.quantifiedself.model.Place;
import com.project.quantifiedself.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    // GET Requests
    /**
     * Method is a list of all the places in the repository.
     *
     * @return A list of all places in the repository.
     */
    @GetMapping(value = "/places")
    public ResponseEntity<List<Place>> getAll() {
        return new ResponseEntity<>(this.placeService.getAll(), HttpStatus.OK);
    }

    /**
     * Method is a GET request to get a list of places when given a place.
     *
     * @param place The place to get a list of from the places collection.
     * @return A list of places when given a place.
     */
    @GetMapping(value = "/places/place/{place}")
    public ResponseEntity<List<Place>> getPlacesByPlace(@PathVariable("place") String place) {
        List<Place> places = this.placeService.getPlacesByPlace(place);
        if (places.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(places, HttpStatus.OK);
    }

    /**
     * Method is a GET request to get a list of places for a given year.
     *
     * @param year The year to get a list of from the places collection.
     * @return A list of places when given a year.
     */
    @GetMapping(value = "/places/year/{year}")
    public ResponseEntity<List<Place>> getPlacesByYear(@PathVariable("year") String year) {
        return new ResponseEntity<>(this.placeService.getPlacesByYear(year), HttpStatus.OK);
    }

    /**
     * Method is a GET request to get a count of the number of times a place was visited.
     *
     * @return A json of places, where the attribute is the key or the place name, and the value is the number of times
     * the place was visited.
     */
    @GetMapping(value = "/places/count")
    public ResponseEntity<Map<String, Long>> countPlacesByPlace() {
        return new ResponseEntity<>(this.placeService.countPlacesByPlace(), HttpStatus.OK);
    }

    // POST, PUT, DELETE Requests

    /**
     * Method is a POST request, which will create a Place object and insert it into the places collection.
     *
     * @param date         The date place was visited.
     * @param place        The name of the place.
     * @param type         The type of place.
     * @param latitude     The latitude of the place.
     * @param longitude    The longitude of the place.
     * @param fourSquareId The four square id of the place.
     * @return The id of the added Place object.
     */
    @PostMapping(value = "/places/add")
    public ResponseEntity<String> addPlace(@RequestParam(value = "date") String date,
                                           @RequestParam(value = "place") String place,
                                           @RequestParam(value = "type") String type,
                                           @RequestParam(value = "latitude") Double latitude,
                                           @RequestParam(value = "longitude") Double longitude,
                                           @RequestParam(value = "fourSquareId", required = false) String fourSquareId) {

        verifyDate(date);
        return new ResponseEntity<>(this.placeService.addPlace(date, place.toLowerCase(Locale.ROOT),
                type, latitude, longitude, fourSquareId), HttpStatus.OK);
    }

    /**
     * Method updates a Place object in the place collection.
     *
     * @param id           The id of the Place object.
     * @param date         The date to be changed of the place object.
     * @param place        The name of the place.
     * @param type         The type of place.
     * @param latitude     The latitude of the place.
     * @param longitude    The longitude of the place.
     * @param fourSquareId The four square id of the place.
     * @return The id of the Place object changed.
     */
    @PutMapping(value = "/places/update/{id}")
    public ResponseEntity<String> updatePlace(@PathVariable("id") String id,
                                              @RequestParam(value = "date", required = false) String date,
                                              @RequestParam(value = "place", required = false) String place,
                                              @RequestParam(value = "type", required = false) String type,
                                              @RequestParam(value = "latitude", required = false) Double latitude,
                                              @RequestParam(value = "longitude", required = false) Double longitude,
                                              @RequestParam(value = "fourSquareId", required = false) String fourSquareId) {
        verifyDate(date);
        return new ResponseEntity<>(this.placeService.updatePlace(id, date, place.toLowerCase(Locale.ROOT),
                type, latitude, longitude, fourSquareId), HttpStatus.OK);
    }

    /**
     * Method deletes a Place object from the place collection.
     *
     * @param id The id of the Place object.
     * @return The status of the HttpStatus.
     */
    @DeleteMapping(value = "places/delete/{id}")
    public ResponseEntity<HttpStatus> deletePlace(@PathVariable("id") String id) {
        try {
            this.placeService.deletePlace(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Helper method that verifies the date before it is passed into the PlaceService class so that it can provide
     * an HTTP response that an invalid date was inputted.
     *
     * @param date The date place was visited.
     * @return A string of the print statement it
     */
    private ResponseEntity<String> verifyDate(String date) {
        try {
            LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String newString = "Invalid date";
        return new ResponseEntity<>(newString, HttpStatus.BAD_REQUEST);
    }

}
