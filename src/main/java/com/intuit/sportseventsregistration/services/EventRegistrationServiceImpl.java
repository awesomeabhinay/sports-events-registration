package com.intuit.sportseventsregistration.services;

import com.intuit.sportseventsregistration.entities.Event;
import com.intuit.sportseventsregistration.entities.EventRegistration;
import com.intuit.sportseventsregistration.entities.User;
import com.intuit.sportseventsregistration.exceptions.EventException;
import com.intuit.sportseventsregistration.repository.EventRegistrationRepository;
import com.intuit.sportseventsregistration.repository.UserRepository;
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
    @Override
    public EventRegistration registerEvent(EventRegistration eventRegistration) {
        try{
            Optional<User> user = userRepository.findByUsername(eventRegistration.getUser().getUsername());

            if(!checkIfUserCanRegisterMore(user.get())){
                throw new EventException("User already registered for 3 events");
            }
            return eventRegistrationRepository.save(eventRegistration);
        } catch (Exception e){
            throw new EventException(String.format(Constants.EVENT_REGISTRATION_ERROR_MESSAGE, eventRegistration));
        }
    }

    @Override
    public String unregisterEvent(int registrationId) {
        try{
            Optional<EventRegistration> eventRegistration = eventRegistrationRepository.findById(registrationId);
            if(eventRegistration.isPresent()){
                eventRegistrationRepository.delete(eventRegistration.get());
                return String.format("Event %s unregistered for %s",eventRegistration.get().getEvent().getEventName(),
                        eventRegistration.get().getUser().getUsername());
            } else{
                throw new EventException(String.format(Constants.EVENT_NOT_FOUND, registrationId));
            }
        } catch (Exception ex){
            throw new EventException(String.format(Constants.EVENT_UNREGISTRATION_ERROR_MESSAGE, registrationId));
        }
    }

    private boolean checkIfUserCanRegisterMore(User user) {
        return eventRegistrationRepository.countByUser(user)!=Constants.MAX_REGISTRATION_LIMIT;
    }
}
