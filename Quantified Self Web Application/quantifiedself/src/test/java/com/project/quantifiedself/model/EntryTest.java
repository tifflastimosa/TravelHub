package com.project.quantifiedself.model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class EntryTest {

    private Entry entry1;
    private Entry entry2;
    private Entry entry3;

    List<Segment> segments1 = new LinkedList<Segment>();
    List<Segment> segments2 = new LinkedList<Segment>();
    List<Segment> segments3 = new LinkedList<Segment>();

    List<ISummaryEntry> summaries = new LinkedList<ISummaryEntry>();

    private LocalDate date;

    @Before
    public void setUp() {
        int[] trackPoints = new int[0];
        int[] trackPoints1 = new int[]{1, 2, 3};
        int[] trackPoints2 = new int[]{-10, 15, 100};

        List<Activity> activities1 = new LinkedList<Activity>();
        List<Activity> activities2 = new LinkedList<Activity>();
        List<Activity> activities3 = new LinkedList<Activity>();

        Location locate1 = new Location(47.67, -122);

        Place place1 = new Place(date, "Georgia Tech", "School", locate1, "1");
        activities1.add(new Activity(date, "walking", "20130209T132707-0800",
                "20130209T133415-0800",428.0, 508.0, 100.00, 100.00, trackPoints));
        activities2.add(new Activity(date, "walking", "20130212T083010-0800",
                "20130212T083040-0800",428.0, 508.0, 100.00, 100.00, trackPoints1));
        activities3.add(new Activity(date, "running", "20130216T190649-0800",
                "20130216T190749-0800",428.0, 508.0, 500.00, 300.00, trackPoints2));
        segments1.add(new Segment("place", "20170206T200613-0800", "20170206T200717-0800", place1,
                activities1));
        segments2.add(new Segment("place", "20170207T054734-0800", "20170207T054824-0800", place1, activities2));
        segments3.add(new Segment("place", "20170207T182509-0800", "20170207T200305-0800", place1, activities3));
        entry1 = new Entry(date, 234.00, "20140801T025223Z", segments1, summaries);
        entry2 = new Entry(date, 0.00, "20200801T025223Z", segments2, summaries);
        entry3 = new Entry(date, 25.00, "20050801T025223Z", segments3, summaries);
    }

    // TODO: this should throw an exception - test pass when it should fail
    @Test
    public void testInvalidInitialize() throws IllegalArgumentException {
        // Calories idle can not be negative
        Entry problem2 = new Entry(date, -35.00, "20140801T025223Z", segments3, summaries);
        // Can Calories idle be negative???

    }

    @Test
    public void getDate() {
        assertEquals(date, entry1.getDate());
        assertEquals(date, entry2.getDate());
        assertEquals(date, entry3.getDate());
    }

    // TODO: revisit - deprecated, failed test
    @Test
    public void getCaloriesIdle() {
        assertEquals(234.0, entry1.getCaloriesIdle(), 0.0);
        assertEquals(0.0, entry2.getCaloriesIdle(), 0.0);
        assertEquals(25.0, entry3.getCaloriesIdle(), 0.0);
    }

    @Test
    public void getLastUpdate() {
        assertEquals("20140801T025223Z", entry1.getLastUpdate());
        assertEquals("20200801T025223Z", entry2.getLastUpdate());
        assertEquals("20050801T025223Z", entry3.getLastUpdate());
    }

    @Test
    public void getSegments() {
        assertEquals(segments1, entry1.getSegments());
        assertEquals(segments2, entry2.getSegments());
        assertEquals(segments3, entry3.getSegments());
    }

}