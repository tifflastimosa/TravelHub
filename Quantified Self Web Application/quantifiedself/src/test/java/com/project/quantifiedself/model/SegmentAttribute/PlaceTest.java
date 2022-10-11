package com.project.quantifiedself.model.SegmentAttribute;

import com.project.quantifiedself.model.Location;
import com.project.quantifiedself.model.Place;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

public class PlaceTest {
    private Place place1;
    private Place place2;
    private Place place3;

    private Location locate1 = new Location(47.67, -122);
    Location locate2 = new Location(0, 0);
    Location locate3 = new Location(2.3, 10.6789);

    private LocalDate date;

    @Before
    public void setUp() {
        place1 = new Place(date, "Georgia Tech", "School", locate1, "1");
        place2 = new Place(date, "A Friends Place", "Home", locate2, "2");
        place3 = new Place(date, "Name of School", "School", locate3, "3");
    }


    @Test
    public void getLocation() {
        assertEquals(locate1, place1.getLocation());
        assertEquals(locate2, place2.getLocation());
        assertEquals(locate3, place3.getLocation());
    }

    @Test
    public void getType() {
        assertEquals("School", place1.getType());
        assertEquals("Home", place2.getType());
        assertEquals("School", place3.getType());
    }

    @Test
    public void getName() {
        assertEquals("Georgia Tech", place1.getPlace());
        assertEquals("A Friends Place", place2.getPlace());
        assertEquals("Name of School", place3.getPlace());
    }

    @Test
    public void getId() {
        assertEquals("1", place1.getFourSquareId());
        assertEquals("2", place2.getFourSquareId());
        assertEquals("3", place3.getFourSquareId());
    }
}