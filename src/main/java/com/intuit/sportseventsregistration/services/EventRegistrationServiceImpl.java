package com.intuit.sportseventsregistration.services;

import com.intuit.sportseventsregistration.dto.Event;
import com.intuit.sportseventsregistration.dto.EventRegistration;
import com.intuit.sportseventsregistration.dto.User;
import com.intuit.sportseventsregistration.exceptions.EventException;
import com.intuit.sportseventsregistration.mapper.EventRegistrationMapper;
import com.intuit.sportseventsregistration.repository.EventRegistrationRepository;
import com.intuit.sportseventsregistration.repository.EventRepository;
import com.intuit.sportseventsregistration.repository.UserRepository;
import com.intuit.sportseventsregistration.requests.EventRegistrationRequest;
import com.intuit.sportseventsregistration.requests.EventUnregisterRequest;
import com.intuit.sportseventsregistration.responses.EventRegistrationResponse;
import com.intuit.sportseventsregistration.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventRegistrationServiceImpl implements EventRegistrationService{

    private final EventRegistrationRepository eventRegistrationRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final EventRegistrationMapper eventRegistrationMapper;
    @Override
    public EventRegistrationResponse registerEvent(EventRegistrationRequest eventRegistrationRequest) {
        Event eventToRegister = eventRepository.findById(eventRegistrationRequest.getEventId())
                .orElseThrow(() -> new EventException("Event not found"));
        if(eventToRegister.getCurrentRegistrationCount()==eventToRegister.getMaxRegistrationLimit()){
            throw new EventException("Sorry this event is fully booked.");
        }
        Optional<User> user = userRepository.findByUsername(eventRegistrationRequest.getUsername());

        if (user.isPresent() && !checkIfUserCanRegisterMore(user.get())) {
            throw new EventException("Participants can only register for maximum of " + Constants.MAX_REGISTRATION_LIMIT + " events");
        }

        if (hasConflicts(user.get(), eventToRegister)) {
            throw new EventException("Event registration conflicts with existing registered event.");
        }

        EventRegistration eventRegistration = eventRegistrationMapper.toDto(eventToRegister, user.get());
        EventRegistrationResponse eventRegistrationResponse = eventRegistrationMapper.toEventRegistrationResponse(eventRegistrationRepository.save(eventRegistration));
        updateEventRegistrationCount(eventToRegister, Constants.REGISTER);
        return eventRegistrationResponse;
    }

    private void updateEventRegistrationCount(Event eventToRegister, String type) {
        if(Constants.UNREGISTER.equals(type)){
            eventToRegister.setCurrentRegistrationCount(eventToRegister.getCurrentRegistrationCount()-1);
        } else {
            eventToRegister.setCurrentRegistrationCount(eventToRegister.getCurrentRegistrationCount()+1);
        }
        eventRepository.save(eventToRegister);
    }

    private boolean hasConflicts(User user, Event eventToRegister) {
        List<Event> userRegisteredEvents = eventRegistrationRepository.findAllByUser(user)
                .stream()
                .map(EventRegistration::getEvent)
                .toList();
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

    @Override
    public String unregisterEvent(EventUnregisterRequest eventUnregisterRequest) {
        EventRegistration eventRegistration = fetchEventRegistration(eventUnregisterRequest);
        if(eventRegistration!=null){
            eventRegistrationRepository.delete(eventRegistration);
            Event eventToUnregister = eventRepository.findById(eventUnregisterRequest.getEventId())
                    .orElseThrow(() -> new EventException("Event not found"));
            updateEventRegistrationCount(eventToUnregister, Constants.UNREGISTER);
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
