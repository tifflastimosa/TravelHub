package com.project.quantifiedself.service;

import com.project.quantifiedself.model.Location;
import com.project.quantifiedself.model.Place;
import com.project.quantifiedself.repositories.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.project.quantifiedself.read.SegmentProcessor.createPlace;

/**
 * PlaceService is a Service layer class that connect with the database.
 */
@Service
public class PlaceService {

    @Autowired
    private PlaceRepository placeRepository;

    /**
     * Method returns all the places in the places collection in the database.
     *
     * @return A list of all places in the places collection in the database.
     */
    public List<Place> getAll() {
        return this.placeRepository.findAll();
    }

    /**
     * Method returns a list of places when given a place.
     *
     * @param place Place to search for in the places collection in the database.
     * @return A list of places in the places collection in the database.
     */
    public List<Place> getPlacesByPlace(String place) {
        return this.placeRepository.findAllByPlace(place);
    }

    /**
     * Method returns a list of places when given a year.
     *
     * @param year Year to search for when a place was visited.
     * @return A list of places in the places collection in the database in a given year.
     */
    public List<Place> getPlacesByYear(String year) {
        Integer prevYear = Integer.parseInt(year) - 1;
        Integer nextYear = Integer.parseInt(year) + 1;
        String stringPreviousYear = Integer.toString(prevYear) + "1231";
        String stringEndOfYear = Integer.toString(nextYear) + "0101";
        LocalDate parsedBegYear = LocalDate.parse(stringPreviousYear, DateTimeFormatter.BASIC_ISO_DATE);
        LocalDate parsedEndYear = LocalDate.parse(stringEndOfYear, DateTimeFormatter.BASIC_ISO_DATE);
        return this.placeRepository.findAll().stream().filter(
                place -> place.getDate().isAfter(parsedBegYear) && place.getDate().isBefore(parsedEndYear)
        ).collect(Collectors.toList());
    }

    /**
     * Method creates a map of places and its count.
     *
     * @return Map where the key is the place, and the value is the number of times the place appears in the
     * places collection.
     */
    public Map<String, Long> countPlacesByPlace() {
        return this.placeRepository
                .findAll()
                .stream()
                .map(place -> Optional.ofNullable(place.getPlace()).orElse("unknown"))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    /**
     * Method adds a Place object into the places collection.
     *
     * @param date         The date the places was visited.
     * @param place        The name of the place.
     * @param type         The type of place.
     * @param latitude     The latitude of the location.  Used to create a Location object.
     * @param longitude    The longitude of the location. Used to create a Location object.
     * @param fourSquareID The fourSquareID of the location.
     * @return Returns the String id of the place.
     */
    public String addPlace(String date, String place, String type, Double latitude, Double longitude,
                           String fourSquareID) {
        Place newPlace = createPlace(date, place, type, latitude, longitude, fourSquareID);
        this.placeRepository.save(newPlace);
        return newPlace.getId();
    }

    /**
     * Method updates a Place object into the places collection.
     *
     * @param id           The id of the Place object in the places collection.
     * @param date         The date the places was visited.
     * @param placeName    The name of the place.
     * @param type         The type of place.
     * @param latitude     The latitude of the location.  Used to create a Location object.
     * @param longitude    The longitude of the location. Used to create a Location object.
     * @param fourSquareID The fourSquareID of the location.
     * @return Returns the String id of the place.
     */
    public String updatePlace(String id, String date, String placeName, String type, Double latitude,
                              Double longitude, String fourSquareID) {
        Optional<Place> placeData = this.placeRepository.findById(id);
        Place place = placeData.get();
        LocalDate parsedDate = LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE);
        place.setDate(parsedDate);
        place.setPlace(placeName);
        place.setType(type);
        Location location = new Location(latitude, longitude);
        place.setLocation(location);
        if (place.getFourSquareId() != null) {
            place.setFourSquareId(fourSquareID);
        }
        this.placeRepository.save(place);
        return place.getPlace();
    }

    /**
     * Method deletes a Place object from the places collection.
     *
     * @param id The id of the Place object in the places collection.
     */
    public void deletePlace(String id) {
        this.placeRepository.deleteById(id);
    }

}
