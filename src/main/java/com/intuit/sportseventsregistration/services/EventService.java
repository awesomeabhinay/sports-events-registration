package com.intuit.sportseventsregistration.services;

import com.intuit.sportseventsregistration.dto.Event;

import java.util.List;

public interface EventService {
    List<Event> getAllEvents();
    List<Event> getAllUserRegisteredEvents(String username);
}
