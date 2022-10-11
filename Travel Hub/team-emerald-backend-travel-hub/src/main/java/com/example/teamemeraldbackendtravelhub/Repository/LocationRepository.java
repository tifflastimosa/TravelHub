package com.example.teamemeraldbackendtravelhub.Repository;

import com.example.teamemeraldbackendtravelhub.Model.Location;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends MongoRepository<Location, String> {

}
