package com.intuit.sportseventsregistration.services;

import com.intuit.sportseventsregistration.requests.EventRegistrationRequest;
import com.intuit.sportseventsregistration.requests.EventUnregisterRequest;
import com.intuit.sportseventsregistration.responses.EventRegistrationResponse;

public interface EventRegistrationService {
    EventRegistrationResponse registerEvent(EventRegistrationRequest eventRegistrationRequest) throws Exception;
    String unregisterEvent(EventUnregisterRequest eventUnregisterRequest);
}
