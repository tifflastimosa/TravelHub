package com.project.quantifiedself.controller;

import com.project.quantifiedself.service.PlaceService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, WebApplicationContext.class})
@WebAppConfiguration
public class PlaceControllerTest {

    private MockMvc mockMvc;

    @Autowired
//    private PlaceService placeServiceMock;

    @Test
    public void getAll() {
    }

    @Test
    public void getPlacesByPlace() {
    }

    @Test
    public void getPlacesCount() {
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetAll() {
    }

    @Test
    public void testGetPlacesByPlace() {
    }

    @Test
    public void testGetPlacesCount() {
    }
}