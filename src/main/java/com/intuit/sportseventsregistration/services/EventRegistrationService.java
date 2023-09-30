package com.intuit.sportseventsregistration.services;

import com.intuit.sportseventsregistration.entities.Event;
import com.intuit.sportseventsregistration.entities.EventRegistration;
import com.intuit.sportseventsregistration.entities.User;
import com.intuit.sportseventsregistration.requests.EventRegistrationRequest;
import com.intuit.sportseventsregistration.requests.EventUnregisterRequest;
import com.intuit.sportseventsregistration.responses.EventRegistrationResponse;

public interface EventRegistrationService {
    EventRegistrationResponse registerEvent(EventRegistrationRequest eventRegistrationRequest);
    String unregisterEvent(EventUnregisterRequest eventUnregisterRequest);
}
