package com.example.teamemeraldbackendtravelhub.Controller;

import com.example.teamemeraldbackendtravelhub.Model.Location;
import com.example.teamemeraldbackendtravelhub.Service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/locations")
@CrossOrigin(origins = {"https://travelhub-frontend-emerald.herokuapp.com/", "http://localhost:3000"})
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping("/all")
    public ResponseEntity<List<Location>> getLocations() {
        try {

            return new ResponseEntity<>(locationService.getLocations(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

//    @RequestMapping
    @GetMapping("/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable("id")  String id) {
        try {
            return new ResponseEntity<>(locationService.getLocationById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
