package com.intuit.sportseventsregistration.controllers;

import com.intuit.sportseventsregistration.dto.Event;
import com.intuit.sportseventsregistration.services.EventService;
import com.intuit.sportseventsregistration.exceptions.UserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventControllerTest {

    @InjectMocks
    private EventController eventController;

    @Mock
    private EventService eventService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllEventsSuccess() {
        List<Event> mockEvents = new ArrayList<>();
        mockEvents.add(new Event());
        Mockito.when(eventService.getAllEvents()).thenReturn(mockEvents);

        ResponseEntity<?> response = eventController.getAllEvents();

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), mockEvents);
    }

    @Test
    public void testGetAllEventsException() {
        Mockito.when(eventService.getAllEvents()).thenThrow(new RuntimeException("Test exception"));

        ResponseEntity<?> response = eventController.getAllEvents();

        assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void testGetUserRegisteredSuccess() {
        String username = "testUser";
        List<Event> mockEvents = new ArrayList<>();
        mockEvents.add(new Event());
        Mockito.when(eventService.getAllUserRegisteredEvents(username)).thenReturn(mockEvents);

        ResponseEntity<?> response = eventController.getUserRegistered(username);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), mockEvents);
    }

    @Test
    public void testGetUserRegisteredUserException() {
        String username = "testUser";
        Mockito.when(eventService.getAllUserRegisteredEvents(username)).thenThrow(new UserException("User not found"));

        ResponseEntity<?> response = eventController.getUserRegistered(username);

        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testGetUserRegisteredException() {
        String username = "testUser";
        Mockito.when(eventService.getAllUserRegisteredEvents(username)).thenThrow(new RuntimeException("Test exception"));

        ResponseEntity<?> response = eventController.getUserRegistered(username);

        assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
