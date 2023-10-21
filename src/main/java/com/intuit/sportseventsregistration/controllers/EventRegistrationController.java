package com.intuit.sportseventsregistration.controllers;

import com.intuit.sportseventsregistration.exceptions.EventException;
import com.intuit.sportseventsregistration.exceptions.UserException;
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
    public ResponseEntity<?> registerEventForUser(@RequestBody EventRegistrationRequest eventRegistrationRequest){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(eventRegistrationService.registerEvent(eventRegistrationRequest));
        }catch (EventException eventException){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(eventException.getMessage());
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Exception while registering for event.");
        }
    }

    @DeleteMapping("event/unregister")
    public ResponseEntity<?> unregisterEventForUser(@RequestBody EventUnregisterRequest eventUnregisterRequest){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(eventRegistrationService.unregisterEvent(eventUnregisterRequest));
        }catch (EventException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Exception while unregistering for event.");
        }
    }
}
