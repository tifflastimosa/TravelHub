package com.project.quantifiedself.model.SegmentAttribute;

import com.project.quantifiedself.model.Location;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LocationTest {

    private Location location1;
    private Location location2;
    private Location location3;

    @Before
    public void setUp() {
        location1 = new Location(47, 0);
        location2 = new Location(0, 180);
        location3 = new Location(1.2, 120.3);
    }

    // TODO: should throw an exception, test passes when it should fail
    @Test
    public void testInvalidInitialize() throws IllegalArgumentException {
        // Range for latitude -90 to 90
        Location problem = new Location(98, 173.4);
        Location problem1 = new Location(-108, 173.4);
        // Range for longitude -180 to 180
        Location problem2 = new Location(0, 201);
        Location problem3 = new Location(0, -301);
    }

    @Test
    public void getLon() {
        assertEquals(0.0, location1.getLon(), 0.0);
        assertEquals(180.0, location2.getLon(), 0.0);
        assertEquals(120.3, location3.getLon(), 0.0);
    }

    @Test
    public void getLat() {
        assertEquals(47.0, location1.getLat(), 0.0);
        assertEquals(0.0, location2.getLat(), 0.0);
        assertEquals(1.2, location3.getLat(), 0.0);
    }


}