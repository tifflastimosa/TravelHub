package com.example.teamemeraldbackendtravelhub.Repository;

import com.example.teamemeraldbackendtravelhub.Model.Trip;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends MongoRepository<Trip, String> {

  @Query(value = "{user_id:?0}")
  List<Trip> getTripsByUserId(String userId);

}
