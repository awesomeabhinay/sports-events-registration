package com.intuit.sportseventsregistration.utils;

public interface Constants {
    String EVENTS_ERROR_MESSAGE = "Exception in fetching all events: %s";
    String USER_EVENTS_ERROR_MESSAGE = "Exception in fetching all events: %s";
    String EVENT_REGISTRATION_ERROR_MESSAGE = "Exception while registering for event: %s";
    String EVENT_UNREGISTRATION_ERROR_MESSAGE = "Exception while unregistering for event: %s";
    int MAX_REGISTRATION_LIMIT = 3;
    String EVENT_NOT_FOUND = "Event with %s id not found";
    String USER_CREATION_ERROR_MESSAGE = "Exception while creating user with username: %s";
    String LOGIN_ERROR_MESSAGE = "Exception while login with username: %s";
}
