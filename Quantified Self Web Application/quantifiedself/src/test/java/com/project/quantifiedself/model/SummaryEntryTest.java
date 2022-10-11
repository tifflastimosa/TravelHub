package com.project.quantifiedself.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SummaryEntryTest {

    SummaryEntry cycle1;
    SummaryEntry transport1;
    SummaryEntry walk1;
    SummaryEntry kayaking1;
    SummaryEntry transport2;

    @BeforeEach
    void setUp() {
        cycle1 = new SummaryEntry("cycling", "cycling", 119.4, 60.76);
        transport1 = new SummaryEntry("transport", "transport", 20.80, 70.67);
        walk1 = new SummaryEntry("walking", "walking", 180.90, 196.20);
        kayaking1 = new SummaryEntry("kayaking", "kayaking", 90.0, 89.76);
        transport2 = new SummaryEntry("transport", "transport", 78.40, 100.87);
    }

    @Test
    public void testInvalidInitialize() throws IllegalArgumentException {
        // Duration can not be negative
        SummaryEntry problem1 = new SummaryEntry("cycling", "cycling", -10.00, 10.00);
        // Distance can not be negative
        SummaryEntry problem2 = new SummaryEntry("walking", "walking", 10.00, -10.00);
    }


    @Test
    void getDuration() {
        assertEquals(60, cycle1.getDuration());
        assertEquals(41, transport1.getDuration());
        assertEquals(196, walk1.getDuration());
        assertEquals(188.35, kayaking1.getDuration());
        assertEquals(0, transport2.getDuration());
    }

    @Test
    void getDistance() {
        assertEquals(10, cycle1.getDistance());
        assertEquals(100, transport1.getDistance());
        assertEquals(1000, walk1.getDistance());
        assertEquals(10, kayaking1.getDistance());
        assertEquals(174.91, transport2.getDistance());
    }

    @Test
    void getType() {
        assertEquals("cycling", cycle1.getActivity());
        assertEquals("transport", transport1.getActivity());
        assertEquals("walking", walk1.getActivity());
        assertEquals("kayaking", kayaking1.getActivity());
        assertEquals("transport", transport2.getActivity());
    }


}