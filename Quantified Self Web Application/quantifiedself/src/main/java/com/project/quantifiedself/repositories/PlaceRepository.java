package com.project.quantifiedself.repositories;

import com.project.quantifiedself.model.Place;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

/**
 * PlaceRepository allows interaction with places collection in the database.
 */
public interface PlaceRepository extends MongoRepository<Place, String> {

    /**
     * Method that finds the places when given a place.
     *
     * @param place The place to find in the places collection.
     * @return Returns a list of places when given a place.
     */
    List<Place> findAllByPlace(String place);

    /**
     * Method finds a Place object when given an id.
     *
     * @param id Finds Place object in the places object when given an id.
     * @return A Place object.
     */
    Optional<Place> findById(String id);

    /**
     * Method deletes a Place object from the places collection.
     *
     * @param id The id of the Place object to be deleted from the collection.
     */
    void deleteById(String id);

}
