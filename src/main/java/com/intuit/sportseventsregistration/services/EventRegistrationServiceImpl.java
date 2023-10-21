package com.intuit.sportseventsregistration.services;

import com.intuit.sportseventsregistration.entities.Event;
import com.intuit.sportseventsregistration.entities.EventRegistration;
import com.intuit.sportseventsregistration.entities.User;
import com.intuit.sportseventsregistration.exceptions.EventException;
import com.intuit.sportseventsregistration.mapper.EventRegistrationMapper;
import com.intuit.sportseventsregistration.repository.EventRegistrationRepository;
import com.intuit.sportseventsregistration.repository.EventRepository;
import com.intuit.sportseventsregistration.repository.UserRepository;
import com.intuit.sportseventsregistration.requests.EventRegistrationRequest;
import com.intuit.sportseventsregistration.requests.EventUnregisterRequest;
import com.intuit.sportseventsregistration.responses.EventRegistrationResponse;
import com.intuit.sportseventsregistration.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventRegistrationServiceImpl implements EventRegistrationService{

    private final EventRegistrationRepository eventRegistrationRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final EventRegistrationMapper eventRegistrationMapper;
    @Autowired
    public EventRegistrationServiceImpl(EventRegistrationRepository eventRegistrationRepository, EventRepository eventRepository,
                                        UserRepository userRepository, EventRegistrationMapper eventRegistrationMapper){
        this.eventRegistrationRepository = eventRegistrationRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.eventRegistrationMapper = eventRegistrationMapper;
    }
    @Override
    public EventRegistrationResponse registerEvent(EventRegistrationRequest eventRegistrationRequest) throws Exception {
        Optional<User> user = userRepository.findByUsername(eventRegistrationRequest.getUsername());

        if (user.isPresent() && !checkIfUserCanRegisterMore(user.get())) {
            throw new EventException("Participants can only register for maximum of " + Constants.MAX_REGISTRATION_LIMIT + " events");
        }
        Event eventToRegister = eventRepository.findById(eventRegistrationRequest.getEventId())
                .orElseThrow(() -> new EventException("Event not found"));

        if (hasConflicts(user.get(), eventToRegister)) {
            throw new EventException("Event registration conflicts with existing registered event.");
        }

        EventRegistration eventRegistration = eventRegistrationMapper.toDto(eventToRegister, user.get());
        return eventRegistrationMapper.toEventRegistrationResponse(eventRegistrationRepository.save(eventRegistration));
    }

    private boolean hasConflicts(User user, Event eventToRegister) {
        List<Event> userRegisteredEvents = eventRegistrationRepository.findAllByUser(user)
                .stream()
                .map(EventRegistration::getEvent)
                .collect(Collectors.toList());
        for (Event registeredEvent : userRegisteredEvents) {
            if (doEventsOverlap(eventToRegister, registeredEvent)) {
                return true;
            }
        }
        return false;
    }

    private boolean doEventsOverlap(Event event1, Event event2) {
        return event1.getEndTime().isAfter(event2.getStartTime()) &&
                event2.getEndTime().isAfter(event1.getStartTime());
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
        EventRegistration eventRegistration = fetchEventRegistration(eventUnregisterRequest);
        if(eventRegistration!=null){
            eventRegistrationRepository.delete(eventRegistration);
            return String.format("Event %s unregistered for %s",eventRegistration.getEvent().getEventName(),
                    eventRegistration.getUser().getUsername());
        } else {
            throw new EventException(String.format(Constants.EVENT_NOT_FOUND, eventUnregisterRequest.getEventId()));
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
