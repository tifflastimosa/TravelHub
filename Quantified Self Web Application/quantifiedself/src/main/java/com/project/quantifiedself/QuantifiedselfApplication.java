package com.project.quantifiedself;

import com.project.quantifiedself.model.Activity;
import com.project.quantifiedself.model.Entry;
import com.project.quantifiedself.model.Place;
import com.project.quantifiedself.read.JsonFileParser;
import com.project.quantifiedself.repositories.ActivityRepository;
import com.project.quantifiedself.repositories.EntryRepository;
import com.project.quantifiedself.repositories.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class QuantifiedselfApplication implements CommandLineRunner {

	@Autowired
	private PlaceRepository placeRepository;

	@Autowired
	private ActivityRepository activityRepository;

	@Autowired
	private EntryRepository entryRepository;

	public static void main(String[] args) {
		SpringApplication.run(QuantifiedselfApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		while (true) {
			Scanner jsonFile = new Scanner(System.in);
			try {
				System.out.println("Please enter the file to inject into the database: ");
				String jsonFileString = jsonFile.nextLine();
				JsonFileParser jsonFileParser = new JsonFileParser();
				jsonFileParser.readJSON(jsonFileString);
				// TODO: Create Summary processor, make createActivity for JSONObject static method so don't have to instantiate
				List<Entry> entriesList = jsonFileParser.getEntriesList();
				List<Activity> activityList = jsonFileParser.getActivitiesObject();
				List<Place> placesList = jsonFileParser.getPlacesList();
				System.out.println("Activities Num Elements: " + activityList.size());
				System.out.println("Entries Num Elements: " + entriesList.size());
				System.out.println("Places Num Elements: " + placesList.size());

				// batch inserts the activity objects - activities collection
				if (this.activityRepository.count() == 0) {
					this.activityRepository.saveAll(activityList);
				}
				// batch inserts the place objects - place collection
				if (this.placeRepository.count() == 0) {
					this.placeRepository.saveAll(placesList);
				}
				// batch inserts the entry objects - entries collection
				if (this.entryRepository.count() == 0) {
					this.entryRepository.saveAll(entriesList);
				}

			} catch (Exception e) {
				System.out.println("Must be a json file.");
			}
		}
	}

}
