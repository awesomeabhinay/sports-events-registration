package com.intuit.sportseventsregistration.services;

import com.intuit.sportseventsregistration.entities.Event;
import com.intuit.sportseventsregistration.entities.EventRegistration;
import com.intuit.sportseventsregistration.entities.User;
import com.intuit.sportseventsregistration.exceptions.EventException;
import com.intuit.sportseventsregistration.exceptions.SportsEventRegistrationException;
import com.intuit.sportseventsregistration.repository.EventRegistrationRepository;
import com.intuit.sportseventsregistration.repository.EventRepository;
import com.intuit.sportseventsregistration.repository.UserRepository;
import com.intuit.sportseventsregistration.requests.EventRegistrationRequest;
import com.intuit.sportseventsregistration.requests.EventUnregisterRequest;
import com.intuit.sportseventsregistration.responses.EventRegistrationResponse;
import com.intuit.sportseventsregistration.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventRegistrationServiceImpl implements EventRegistrationService{

    @Autowired
    EventRegistrationRepository eventRegistrationRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EventRepository eventRepository;
    @Override
    public EventRegistrationResponse registerEvent(EventRegistrationRequest eventRegistrationRequest) {
        try{
            Optional<User> user = userRepository.findByUsername(eventRegistrationRequest.getUsername());

            if(user.isPresent() && !checkIfUserCanRegisterMore(user.get())){
                throw new SportsEventRegistrationException("User already registered for 3 events");
            }
            EventRegistration  eventRegistration= createEventRegistration(user.get(), eventRegistrationRequest);
            return successFullEventRegistrationResponse(eventRegistrationRepository.save(eventRegistration));
        } catch (Exception e){
            throw new SportsEventRegistrationException(String.format(Constants.EVENT_REGISTRATION_ERROR_MESSAGE, eventRegistrationRequest.getEventId()));
        }
    }

    private EventRegistrationResponse successFullEventRegistrationResponse(EventRegistration eventRegistration) {
        return new EventRegistrationResponse(eventRegistration.getId(), eventRegistration.getEvent().getId(), eventRegistration.getUser().getUsername());
    }

    private EventRegistration createEventRegistration(User user, EventRegistrationRequest eventRegistrationRequest) {
        EventRegistration eventRegistration = new EventRegistration();
        eventRegistration.setUser(user);
        eventRegistration.setEvent(eventRepository.getReferenceById(eventRegistrationRequest.getEventId()));
        return eventRegistration;
    }

    @Override
    public String unregisterEvent(EventUnregisterRequest eventUnregisterRequest) {
        try{

            EventRegistration eventRegistration = fetchEventRegistration(eventUnregisterRequest);
            if(eventRegistration!=null){
                eventRegistrationRepository.delete(eventRegistration);
                return String.format("Event %s unregistered for %s",eventRegistration.getEvent().getEventName(),
                        eventRegistration.getUser().getUsername());
            } else{
                throw new EventException(String.format(Constants.EVENT_NOT_FOUND, eventUnregisterRequest.getEventId()));
            }
        } catch (Exception ex){
            throw new EventException(String.format(Constants.EVENT_UNREGISTRATION_ERROR_MESSAGE, eventUnregisterRequest.getEventId()));
        }
    }

    private EventRegistration fetchEventRegistration(EventUnregisterRequest eventUnregisterRequest) {
        Optional<User> user = userRepository.findByUsername(eventUnregisterRequest.getUsername());
        Optional<Event> event = eventRepository.findById(eventUnregisterRequest.getEventId());
        if(user.isPresent() && event.isPresent()){
            return eventRegistrationRepository.findByEventAndUser(event.get(), user.get());
        } else {
            return null;
        }
    }

    private boolean checkIfUserCanRegisterMore(User user) {
        return eventRegistrationRepository.countByUser(user)!=Constants.MAX_REGISTRATION_LIMIT;
    }
}
