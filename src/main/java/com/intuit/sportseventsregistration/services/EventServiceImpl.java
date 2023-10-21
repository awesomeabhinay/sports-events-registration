package com.intuit.sportseventsregistration.services;

import com.intuit.sportseventsregistration.dto.Event;
import com.intuit.sportseventsregistration.dto.EventRegistration;
import com.intuit.sportseventsregistration.dto.User;
import com.intuit.sportseventsregistration.exceptions.UserException;
import com.intuit.sportseventsregistration.repository.EventRegistrationRepository;
import com.intuit.sportseventsregistration.repository.EventRepository;
import com.intuit.sportseventsregistration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService{

    @Autowired
    EventRepository eventRepository;
    @Autowired
    EventRegistrationRepository eventRegistrationRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public List<Event> getAllUserRegisteredEvents(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()) {
            List<EventRegistration> eventRegistrations = eventRegistrationRepository.findAllByUser(user.get());
            return eventRegistrations.stream().map(EventRegistration::getEvent)
                    .toList();
        }else{
            throw new UserException(username);
        }
    }
}
