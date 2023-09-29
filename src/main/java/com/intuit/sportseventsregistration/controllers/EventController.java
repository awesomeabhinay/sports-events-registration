package com.intuit.sportseventsregistration.controllers;

import com.intuit.sportseventsregistration.entities.Event;
import com.intuit.sportseventsregistration.exceptions.EventException;
import com.intuit.sportseventsregistration.services.EventService;
import com.intuit.sportseventsregistration.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EventController {

    @Autowired
    EventService eventService;

    @GetMapping("events/all")
    public ResponseEntity<List<Event>> getAllEvents(){
        try{
            List<Event> events = eventService.getAllEvents();
            return ResponseEntity.status(HttpStatus.OK).body(events);
        } catch (Exception ex){
            throw new EventException(String.format(Constants.EVENTS_ERROR_MESSAGE, ex.getMessage()));
        }
    }

    @GetMapping("events/{username}")
    public ResponseEntity<List<Event>> getUserRegistered(@PathVariable String username){
        try{
            List<Event> events = eventService.getAllUserRegisteredEvents(username);
            return ResponseEntity.status(HttpStatus.OK).body(events);
        } catch (Exception ex){
            throw new EventException(String.format(Constants.EVENTS_ERROR_MESSAGE, ex.getMessage()));
        }
    }



}
