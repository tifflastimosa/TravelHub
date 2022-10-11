package com.project.quantifiedself.model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class SegmentTest {

    private Segment placeseg;
    private Segment moveseg;

    private List<Activity> activities1 = new LinkedList<Activity>();
    private List<Activity> activities2 = new LinkedList<Activity>();

    private Location locate1 = new Location(47.67, -122);
    private Location locate2 = new Location(0, 0);

    private LocalDate date;

    private Place place1 = new Place(date, "Georgia Tech", "School", locate1, "");
    private Place place2 = new Place(date, "A Friends Place", "Home", locate2, "");


    @Before
    public void setUp() {
        int[] trackPoints = new int[0];
        activities2.add(new Activity(date, "walking", "20130209T132707-0800",
                "20130209T133415-0800", 428.0, 508.0, 4000.00, 100.00, trackPoints));
        placeseg = new Segment("place", "20130209T171309-0800", "20130209T174352-0800", place1, activities1);
        moveseg = new Segment("move", "20130209T183422-0800", "20130209T182854-0800", place2, activities2);
    }

    @Test
    public void getPlace() {
        assertEquals("place", placeseg.getType());
        assertEquals("move", moveseg.getType());
    }

}