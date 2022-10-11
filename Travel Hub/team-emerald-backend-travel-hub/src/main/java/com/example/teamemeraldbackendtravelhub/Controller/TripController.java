package com.example.teamemeraldbackendtravelhub.Controller;

import com.example.teamemeraldbackendtravelhub.Model.Activity;
import com.example.teamemeraldbackendtravelhub.Model.Trip;
import com.example.teamemeraldbackendtravelhub.Service.TripServiceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/trips")
@CrossOrigin(origins = {"http://localhost:3000", "https://travelhub-frontend-emerald.herokuapp.com/"})
public class TripController {

  @Autowired
  private TripServiceImpl tripService;

  /**
   * Method is an http request to the service layer to add new trip to the trips collection.
   * @param trip Trip object to add to the database.
   * @return The auto-generated id when trip added to database.
   */
  @PostMapping
  public ResponseEntity<String> save(@RequestBody Trip trip) {
    try {
      return new ResponseEntity<>(tripService.save(trip), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * Method gets trip by id.
   * @param id The id of the trip object in the database.
   * @return Trip object from the database.
   */
  @GetMapping("/{id}")
  public ResponseEntity<Trip> getTripById(@PathVariable String id) {
    try {
      return new ResponseEntity<>(tripService.getTripById(id), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * Finds all trips by the given user id.
   * @param id The user id assigned to the trip.
   * @return A list of all the trips that have the same user id.
   */
  // TODO: Review this get request - issue with getting list of trips by user id
  @GetMapping("/all/{id}")
  public ResponseEntity<List<Trip>> getTripByUserId(@PathVariable String id) {
    try {
      return new ResponseEntity<>(tripService.getAllTripsByUser(id), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * Method is a http request that updates the trip object in the trips collection in the db.
   * @param trip The trip with new information.
   * @return The id of the trip that was updated.
   */
  @PutMapping
  public ResponseEntity<String> updateTrip(@RequestBody Trip trip) {
    try {
      return new ResponseEntity<>(tripService.updateTrip(trip), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * Method is a http request to add collaborators to the array of collaborators who have access
   * to the trip.
   * @param id The trip to add the collaborator to.
   * @param userId The user to add to the collaborator array.
   * @return The HttpStatus of the request. Returns OK success, BAD_REQUEST otherwise.
   */
  @PutMapping("/collaborator/{id}")
  public HttpStatus addCollaborator(@PathVariable String id, @RequestBody String userId) {
    try {
      tripService.addCollaborator(id, userId);
      return HttpStatus.OK;
    } catch (Exception e) {
      return HttpStatus.BAD_REQUEST;
    }
  }

  /**
   * Method is a http request to delete a trip from the db when given the trip id.
   * @param id The id of the trip object from the trips collection.
   * @return The HttpStatus of the request. Returns OK success, BAD_REQUEST otherwise.
   */
  @DeleteMapping("/delete/{id}")
  public HttpStatus deleteTrip(@PathVariable String id) {
    try {
      System.out.println(id);
      tripService.deleteTrip(id);
      return HttpStatus.OK;
    } catch (Exception e) {
      return HttpStatus.BAD_REQUEST;
    }
  }

  @PutMapping("/add-activity/{id}")
  public ResponseEntity<String> addActivity(@PathVariable String id, @RequestBody Activity activity) {
    try {
      return new ResponseEntity<>(tripService.addActivity(id, activity), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
}
