package com.example.teamemeraldbackendtravelhub.Repository;

import com.example.teamemeraldbackendtravelhub.Model.Itinerary;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItineraryRepository extends MongoRepository<Itinerary, String> {
  List<Itinerary> getItineraryByUserId(String userId);
}