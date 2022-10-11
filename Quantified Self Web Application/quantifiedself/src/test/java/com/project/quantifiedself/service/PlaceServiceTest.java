package com.project.quantifiedself.service;

import com.project.quantifiedself.model.Location;
import com.project.quantifiedself.model.Place;
import com.project.quantifiedself.repositories.PlaceRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PlaceServiceTest {

    @Mock
    PlaceRepository placeRepository;

    @InjectMocks
    PlaceService placeService;

    Place placeTest;

    @Before
    public void setUp() throws Exception {
        Location location = new Location();
        location.setLat(-41.21);
        location.setLon(40.50);

        placeTest = new Place();
        String date = "20210813";
        LocalDate parsedDate = LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE);
        placeTest.setDate(parsedDate);
        placeTest.setPlace("home");
        placeTest.setType("Home");
        placeTest.setLocation(location);
        placeTest.setFourSquareId("test");
    }

//    https://www.freecodecamp.org/news/unit-testing-services-endpoints-and-repositories-in-spring-boot-4b7d9dc2b772/
    @Test
    public void testAdd() {
        when(placeRepository.save(any(Place.class))).thenReturn(new Place());
//        placeService.addPlace();
//        assertThat(placeTest.getDate().equals())
    }

    @Test
    public void getAll() {

    }

    @Test
    public void getPlacesByPlace() {
    }

    @Test
    public void getCountPlaces() {
    }

    @Test
    public void getPlacesByDate() {
    }
}