package com.intuit.sportseventsregistration.services;

import com.intuit.sportseventsregistration.entities.Event;
import com.intuit.sportseventsregistration.entities.User;

import java.util.List;

public interface EventService {
    List<Event> getAllEvents();
    List<Event> getAllUserRegisteredEvents(String username);
}
