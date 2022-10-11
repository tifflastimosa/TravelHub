package com.example.teamemeraldbackendtravelhub.Service;

import com.example.teamemeraldbackendtravelhub.Model.Activity;
import com.example.teamemeraldbackendtravelhub.Model.Trip;
import com.example.teamemeraldbackendtravelhub.Repository.TripRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TripServiceImpl {

  @Autowired
  private TripRepository tripRepository;

  /**
   * Method accesses the database with the TripRepository to save the new trip to the database.
   * @param trip The trip object to add to the database.
   * @return The id of the new trip object that was added to the database.
   */
  public String save(Trip trip) {
    return tripRepository.insert(trip).getId();
  }

  /**
   * Method accesses the database with the TripRepository to retrieve a trip object when given the
   * id of the trip.
   * @param id The id of the trip object.
   * @return The Trip object that has the same trip id.
   */
  public Trip getTripById(String id) {
    Optional<Trip> fromDB = tripRepository.findById(id);
    return fromDB.get();
  }

  /**
   * Method accesses the database with the TripRepository to retrieve a list of all trips that has
   * the same user id.
   * @param userId The userId in the collaborators or user_id field.
   * @return A list of all trips that matches the user_id.
   */
  public List<Trip> getAllTripsByUser(String userId) {
    return tripRepository.getTripsByUserId(userId);
  }

  /**
   * Method accesses the database with the TripRepository to update the trip object given the
   * trip id.
   * @param trip The trip object with the updated information.
   * @return The id of the updated Trip object.
   */
  public String updateTrip(Trip trip) {
    Optional<Trip> fromDB = tripRepository.findById(trip.getId());
    Trip tripConverted = fromDB.get();
    tripConverted.setTripName(trip.getTripName());
    tripConverted.setDestination(trip.getDestination());
    tripConverted.setStartDate(trip.getStartDate());
    tripConverted.setEndDate(trip.getEndDate());
    tripConverted.setBudget(trip.getBudget());
    tripConverted.setCollaborators(trip.getCollaborators());
    tripConverted.setUserId(trip.getUserId());
    return tripRepository.save(tripConverted).getId();
  }

  /**
   * Method accesses the database to add a collaborator to the collaborators array when given the
   * trip id and the user id.
   * @param id The id of the trip object which is used to retrieve the trip object from the
   *           trips collection.
   * @param userId The id of the user to add to the collaborators array in the retrieved trip
   *               object.
   */
  public void addCollaborator(String id, String userId) {
    Optional<Trip> fromDB = tripRepository.findById(id);
    Trip trip = fromDB.get();
    trip.getCollaborators().add(userId);
    tripRepository.save(trip);
  }

  /**
   * Method accesses the database to delete the trip from the trips collection.
   * @param id The id of the trip object to be deleted from the database.
   */
  public void deleteTrip(String id) {
    tripRepository.deleteById(id);
  }

  public String addActivity(String tripId, Activity activity) {
    // get the trip
    Optional<Trip> fromDB = tripRepository.findById(tripId);
    Trip tripFromDb = fromDB.get();
    ArrayList<Activity> listOfActivities = tripFromDb.getActivitiesBooked();
    listOfActivities.add(activity);
    tripFromDb.setActivitiesBooked(listOfActivities);
    return tripRepository.save(tripFromDb).getId();
  }

}
