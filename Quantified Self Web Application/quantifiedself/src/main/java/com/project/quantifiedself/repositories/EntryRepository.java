package com.project.quantifiedself.repositories;

import com.project.quantifiedself.model.Entry;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * EntryRepository allows interaction with Entries in the database.
 */
public interface EntryRepository extends MongoRepository <Entry, Long> {

    /**
     * Method that gets an Entry from the entries collection when given a date.
     *
     * @param date The date of the Entry.
     * @return Returns the Entry when given a date.
     */
    Entry getEntryByDate(String date);

}
