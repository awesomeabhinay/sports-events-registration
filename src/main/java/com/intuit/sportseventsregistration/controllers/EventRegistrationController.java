package com.intuit.sportseventsregistration.controllers;

import com.intuit.sportseventsregistration.entities.EventRegistration;
import com.intuit.sportseventsregistration.exceptions.EventException;
import com.intuit.sportseventsregistration.requests.EventRegistrationRequest;
import com.intuit.sportseventsregistration.requests.EventUnregisterRequest;
import com.intuit.sportseventsregistration.responses.EventRegistrationResponse;
import com.intuit.sportseventsregistration.services.EventRegistrationService;
import com.intuit.sportseventsregistration.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EventRegistrationController {

    @Autowired
    EventRegistrationService eventRegistrationService;

    @PostMapping("event/register")
    public ResponseEntity<EventRegistrationResponse> registerEventForUser(@RequestBody EventRegistrationRequest eventRegistrationRequest){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(eventRegistrationService.registerEvent(eventRegistrationRequest));
        }catch (Exception ex){
            throw new EventException(String.format(Constants.EVENT_REGISTRATION_ERROR_MESSAGE, eventRegistrationRequest.getEventId()));
        }
    }

    @DeleteMapping("event/unregister")
    public ResponseEntity<String> unregisterEventForUser(@RequestBody EventUnregisterRequest eventUnregisterRequest){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(eventRegistrationService.unregisterEvent(eventUnregisterRequest));
        }catch (Exception ex){
            throw new EventException(String.format(Constants.EVENT_UNREGISTRATION_ERROR_MESSAGE, eventUnregisterRequest.getEventId()));
        }
    }
}
