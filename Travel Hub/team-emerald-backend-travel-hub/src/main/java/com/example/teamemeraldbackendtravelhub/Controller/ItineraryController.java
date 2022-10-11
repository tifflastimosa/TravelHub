package com.example.teamemeraldbackendtravelhub.Controller;

import com.example.teamemeraldbackendtravelhub.Model.Itinerary;
import com.example.teamemeraldbackendtravelhub.Model.Activity;
import com.example.teamemeraldbackendtravelhub.Model.Trip;
import com.example.teamemeraldbackendtravelhub.Service.ItineraryServiceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/itinerary")
public class ItineraryController {

  @Autowired
  private ItineraryServiceImpl itineraryService;

  @PostMapping
  public ResponseEntity<String> save(@RequestBody Itinerary itinerary) {
    try {
      return new ResponseEntity<>(itineraryService.save(itinerary), HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Itinerary> getItineraryById(@PathVariable("id") String id) {
    try {
      return new ResponseEntity<>(itineraryService.getItineraryById(id), HttpStatus.FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/all/{id}")
  public ResponseEntity<List<Itinerary>> getTripByUserId(@PathVariable("id") String id) {
    try {
      return new ResponseEntity<>(itineraryService.getItineraryByUserId(id), HttpStatus.FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<String>updateItinerary(@PathVariable("id") String id, @RequestBody Itinerary
      newItinerary) {
    try {
      return new ResponseEntity<>(itineraryService.updateItinerary(id, newItinerary), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping("/delete/{id}")
  public HttpStatus deleteItinerary(@PathVariable("id") String id) {
    try {
      System.out.println(id);
      itineraryService.deleteItinerary(id);
      return HttpStatus.GONE;
    } catch (Exception e) {
      return HttpStatus.BAD_REQUEST;
    }
  }

  @PutMapping("/activities/{id}")
  public HttpStatus addActivity(@PathVariable("id") String id, @RequestBody Activity activity) {
    try {
      itineraryService.addActivity(id, activity);
      return HttpStatus.OK;
    } catch (Exception e) {
      return HttpStatus.BAD_REQUEST;
    }
  }

  @DeleteMapping("/activities/delete/{id}")
  public HttpStatus deleteActivity(@PathVariable("id") String id, @RequestBody Activity activity) {
    try {
      itineraryService.deleteActivity(id, activity);
      return HttpStatus.OK;
    } catch (Exception e) {
      return HttpStatus.BAD_REQUEST;
    }
  }

  @PutMapping("/other-bookings/{id}")
  public HttpStatus addOther_Bookings(@PathVariable("id") String id, @RequestBody String bookingsId) {
    try {
      itineraryService.addOther_Bookings(id, bookingsId);
      return HttpStatus.OK;
    } catch (Exception e) {
      return HttpStatus.BAD_REQUEST;
    }
  }

  @DeleteMapping("/other-bookings/delete/{id}")
  public HttpStatus deleteOther_Bookings(@PathVariable("id") String id, @RequestBody String other_bookingsId) {
    try {
      itineraryService.deleteOther_Bookings(id, other_bookingsId);
      return HttpStatus.OK;
    } catch (Exception e) {
      return HttpStatus.BAD_REQUEST;
    }
  }
}
