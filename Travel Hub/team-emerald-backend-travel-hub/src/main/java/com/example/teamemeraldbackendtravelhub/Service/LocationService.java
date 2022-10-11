package com.example.teamemeraldbackendtravelhub.Service;

import com.example.teamemeraldbackendtravelhub.Model.Activity;
import com.example.teamemeraldbackendtravelhub.Model.Location;
import com.example.teamemeraldbackendtravelhub.Repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public List<Location> getLocations() { return locationRepository.findAll(); }

    public Location getLocationById(String id) {
        Optional<Location> fromDB = locationRepository.findById(id);
        return fromDB.get();
    }
}
