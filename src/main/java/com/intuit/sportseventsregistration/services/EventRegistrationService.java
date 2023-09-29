package com.intuit.sportseventsregistration.services;

import com.intuit.sportseventsregistration.entities.Event;
import com.intuit.sportseventsregistration.entities.EventRegistration;
import com.intuit.sportseventsregistration.entities.User;

public interface EventRegistrationService {
    EventRegistration registerEvent(EventRegistration eventRegistration);
    String unregisterEvent(int registrationId);
}
