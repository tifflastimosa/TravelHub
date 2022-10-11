package com.project.quantifiedself.model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class ActivityTest {

    private Activity walk1;
    private Activity run;
    private Activity cycling;
    private Activity walk2;

    private LocalDate date;

    @Before
    public void setUp() {
        walk1 = new Activity(date, "walking", "1400", "2400", 44.00, 180.00, 1000.0, 100.00, new int[]{});
        walk2 = new Activity(date, "walking", "1400", "2400", 44.00, 180.00, 1000.0, 100.00, new int[]{});
        run = new Activity(date, "running", "1400", "2400", 44.00, 180.00, 1000.0, 100.00, new int[]{});
        cycling = new Activity(date, "cycling", "1400", "2400", 44.00, 180.00, 1000.0, 100.00, new int[]{});
    }

    @Test
    public void testInvalidInitialize() throws IllegalArgumentException {
        // Steps can not be negative
        Activity problem4 = new Activity(date, "walking", "1400", "2400", 44.00, 180.00, -1000.0, 100.00, new int[]{});
        // Calories can not be negative
        Activity problem5 = new Activity(date, "kayaking", "1400", "2400", 44.00, 180.00, 1000.0, -100.00, new int[]{});;
    }

    @Test
    public void getSteps() {
        assertEquals(5.0, walk1.getSteps(), 0.0);
        assertEquals(44.0, walk2.getSteps(), 0.0);
    }

    @Test
    public void getCalories() {
        assertEquals(235.0, walk1.getCalories(), 0.0);
        assertEquals(24.0, walk2.getCalories(), 0.0);
        assertEquals(2351.0, run.getCalories(), 0.0);
        assertEquals(6.3, cycling.getCalories(), 0.0);
    }


}