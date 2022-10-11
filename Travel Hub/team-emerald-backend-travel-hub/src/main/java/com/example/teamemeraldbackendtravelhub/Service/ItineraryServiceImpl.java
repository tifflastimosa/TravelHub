package com.example.teamemeraldbackendtravelhub.Service;

import com.example.teamemeraldbackendtravelhub.Model.Activity;
import com.example.teamemeraldbackendtravelhub.Model.Itinerary;
import com.example.teamemeraldbackendtravelhub.Model.Trip;
import com.example.teamemeraldbackendtravelhub.Repository.ItineraryRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

@Service
public class ItineraryServiceImpl {

  @Autowired
  private ItineraryRepository itineraryRepository;

  // @Override
  public String save(Itinerary itinerary) {
    return itineraryRepository.insert(itinerary).getId();
  }

  public Itinerary getItineraryById(String id) {
    Optional<Itinerary> fromDB = itineraryRepository.findById(id);
    return fromDB.get();
  }

  public List<Itinerary> getItineraryByUserId(String userId) {
    return itineraryRepository.getItineraryByUserId(userId);
  }

  public String updateItinerary(String id, Itinerary newItinerary) {
    Optional<Itinerary> fromDB = itineraryRepository.findById(id);
    Itinerary itineraryConverted = fromDB.get();
    itineraryConverted.setDate(newItinerary.getDate());
    itineraryConverted.setHotel(newItinerary.getHotel());
    itineraryConverted.setActivities_booked(newItinerary.getActivities_booked());
    itineraryConverted.setOther_bookings(newItinerary.getOther_bookings());
    return itineraryRepository.save(itineraryConverted).getId();
  }

  public void addActivity(String id, Activity activity) {
    Optional<Itinerary> fromDB = itineraryRepository.findById(id);
    Itinerary itinerary = fromDB.get();
    itinerary.getActivities_booked().add(activity);
    itineraryRepository.save(itinerary);
  }

  public void deleteActivity(String id, Activity activity) {
    Optional<Itinerary> fromDB = itineraryRepository.findById(id);
    Itinerary itinerary = fromDB.get();
    itinerary.getActivities_booked().remove(activity);
    itineraryRepository.save(itinerary);
  }

  public void addOther_Bookings(String id, String other_bookings) {
    Optional<Itinerary> fromDB = itineraryRepository.findById(id);
    Itinerary itinerary = fromDB.get();
    itinerary.getOther_bookings().add(other_bookings);
    itineraryRepository.save(itinerary);
  }

  public void deleteOther_Bookings(String id, String other_bookings) {
    Optional<Itinerary> fromDB = itineraryRepository.findById(id);
    Itinerary itinerary = fromDB.get();
    itinerary.getOther_bookings().remove(other_bookings);
    itineraryRepository.save(itinerary);
  }

  public void deleteItinerary(String id) {
    itineraryRepository.deleteById(id);
  }
}
