package com.intuit.sportseventsregistration.services;

import com.intuit.sportseventsregistration.dto.Event;
import com.intuit.sportseventsregistration.dto.EventRegistration;
import com.intuit.sportseventsregistration.dto.User;
import com.intuit.sportseventsregistration.exceptions.UserException;
import com.intuit.sportseventsregistration.repository.EventRegistrationRepository;
import com.intuit.sportseventsregistration.repository.EventRepository;
import com.intuit.sportseventsregistration.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventRegistrationRepository eventRegistrationRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private EventServiceImpl eventService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllEvents() {
        List<Event> events = new ArrayList<>();
        events.add(new Event());
        events.add(new Event());

        when(eventRepository.findAll()).thenReturn(events);

        List<Event> result = eventService.getAllEvents();

        assertEquals(2, result.size());
    }

    @Test
    public void testGetAllUserRegisteredEvents_Success() {
        User user = new User();
        user.setUsername("testuser");

        Event event1 = new Event();
        Event event2 = new Event();

        EventRegistration registration1 = new EventRegistration();
        registration1.setEvent(event1);

        EventRegistration registration2 = new EventRegistration();
        registration2.setEvent(event2);

        List<EventRegistration> registrations = new ArrayList<>();
        registrations.add(registration1);
        registrations.add(registration2);

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(eventRegistrationRepository.findAllByUser(user)).thenReturn(registrations);

        List<Event> result = eventService.getAllUserRegisteredEvents("testuser");

        assertEquals(2, result.size());
    }

    @Test
    public void testGetAllUserRegisteredEvents_UserNotFound() {
        when(userRepository.findByUsername("nonexistentuser")).thenReturn(Optional.empty());

        assertThrows(UserException.class, () -> eventService.getAllUserRegisteredEvents("nonexistentuser"));
    }
}

