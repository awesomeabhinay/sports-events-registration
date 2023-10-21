package com.intuit.sportseventsregistration.controllers;

import com.intuit.sportseventsregistration.dto.Event;
import com.intuit.sportseventsregistration.exceptions.UserException;
import com.intuit.sportseventsregistration.services.EventService;
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
    public ResponseEntity<?> getAllEvents(){
        try{
            List<Event> events = eventService.getAllEvents();
            return ResponseEntity.status(HttpStatus.OK).body(events);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Exception in fetching events");
        }
    }

    @GetMapping("events/{username}")
    public ResponseEntity<?> getUserRegistered(@PathVariable String username){
        try{
            List<Event> events = eventService.getAllUserRegisteredEvents(username);
            return ResponseEntity.status(HttpStatus.OK).body(events);
        } catch (UserException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found with username: " + username);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Exception while fetching user events");
        }
    }
}
