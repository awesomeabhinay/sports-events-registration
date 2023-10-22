package com.intuit.sportseventsregistration.services;

import com.intuit.sportseventsregistration.config.DistributedLockManager;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;

public class EventRegistrationServiceTest {

    @Mock
    private EventRegistrationRepository eventRegistrationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventRegistrationMapper eventRegistrationMapper;

    @Mock
    private DistributedLockManager distributedLockManager;

    @InjectMocks
    private EventRegistrationServiceImpl eventRegistrationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterEvent_Success() throws InterruptedException, TimeoutException {
        EventRegistrationRequest request = new EventRegistrationRequest();
        request.setEventId(1);
        request.setUsername("testuser");

        User user = new User();
        user.setUsername("testuser");

        Event event = new Event();
        event.setStartTime(ZonedDateTime.now().plusHours(2));
        event.setEndTime(ZonedDateTime.now().plusHours(3));
        event.setId(1);
        event.setMaxRegistrationLimit(10);
        event.setCurrentRegistrationCount(1);

        EventRegistration registration = new EventRegistration();
        registration.setEvent(event);
        registration.setUser(user);

        EventRegistrationResponse eventRegistrationResponse = new EventRegistrationResponse();
        eventRegistrationResponse.setUsername(user.getUsername());
        eventRegistrationResponse.setEventId(1);

        Mockito.when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        Mockito.when(eventRepository.findById(1)).thenReturn(Optional.of(event));
        Mockito.when(eventRegistrationRepository.findAllByUser(user)).thenReturn(Collections.emptyList());
        Mockito.when(eventRegistrationMapper.toDto(eq(event), eq(user))).thenReturn(registration);
        Mockito.when(eventRegistrationRepository.save(any(EventRegistration.class))).thenReturn(registration);
        Mockito.when(eventRegistrationMapper.toEventRegistrationResponse(any(EventRegistration.class))).thenReturn(eventRegistrationResponse);

        EventRegistrationResponse response = eventRegistrationService.registerEvent(request);
        assertEquals("testuser", response.getUsername());
    }

    @Test
    public void testRegisterEvent_UserAlreadyRegisteredForMaxEvents() throws InterruptedException, TimeoutException {
        EventRegistrationRequest request = new EventRegistrationRequest();
        request.setEventId(1);
        request.setUsername("testuser");

        User user = new User();
        user.setUsername("testuser");
        Event event = new Event();
        event.setCurrentRegistrationCount(1);
        event.setMaxRegistrationLimit(10);
        Mockito.when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event));
        Mockito.when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        Mockito.when(eventRegistrationRepository.countByUser(user)).thenReturn(Constants.MAX_REGISTRATION_LIMIT);

        try {
            eventRegistrationService.registerEvent(request);
        } catch (EventException | InterruptedException | TimeoutException ex) {
            assertEquals("Participants can only register for maximum of 3 events", ex.getMessage());
        }
    }


    @Test
    public void testRegisterEvent_Conflict() {
        EventRegistrationRequest request = new EventRegistrationRequest();
        request.setUsername("testuser");
        request.setEventId(1);

        User user = new User();
        user.setUsername("testuser");

        Event event = new Event();
        event.setId(1);
        event.setEventName("Test Event");
        event.setStartTime(ZonedDateTime.now().minusHours(1));
        event.setEndTime(ZonedDateTime.now().plusHours(1));

        EventRegistration existingRegistration = new EventRegistration();
        existingRegistration.setEvent(event);
        existingRegistration.setUser(user);

        List<EventRegistration> userRegisteredEvents = new ArrayList<>();
        userRegisteredEvents.add(existingRegistration);

        Mockito.when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        Mockito.when(eventRegistrationRepository.countByUser(any())).thenReturn(1);
        Mockito.when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event));
        Mockito.when(eventRegistrationRepository.findAllByUser(user)).thenReturn(userRegisteredEvents);
        assertThrows(EventException.class, () -> eventRegistrationService.registerEvent(request));
    }

    @Test
    public void testUnregisterEvent_Success() {
        User user = new User();
        user.setId(1);
        user.setUsername("testuser");

        Event event = new Event();
        event.setId(1);
        event.setEventName("Test Event");

        EventRegistration eventRegistration = new EventRegistration();
        eventRegistration.setEvent(event);
        eventRegistration.setUser(user);

        EventUnregisterRequest request = new EventUnregisterRequest();
        request.setUsername("testuser");
        request.setEventId(1);

        Mockito.when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        Mockito.when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event));
        Mockito.when(eventRegistrationRepository.findByEventAndUser(event, user)).thenReturn(eventRegistration);
        Mockito.doNothing().when(eventRegistrationRepository).delete(any(EventRegistration.class));

        String response = eventRegistrationService.unregisterEvent(request);
        assertEquals(String.format("Event %s unregistered for %s", event.getEventName(), user.getUsername()), response);
    }

    @Test
    public void testUnregisterEvent_EventNotFound() {
        EventUnregisterRequest request = new EventUnregisterRequest();
        request.setUsername("testuser");
        request.setEventId(1);

        Mockito.when(eventRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EventException.class, () -> eventRegistrationService.unregisterEvent(request));
    }

    @Test
    public void testUnregisterEvent_EventRegistrationNotFound() {
        Event event = new Event();
        event.setId(1);

        EventUnregisterRequest request = new EventUnregisterRequest();
        request.setUsername("testuser");
        request.setEventId(1);

        Mockito.when(eventRepository.findById(1)).thenReturn(Optional.of(event));
        Mockito.when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(new User()));
        Mockito.when(eventRegistrationRepository.findByEventAndUser(event, new User())).thenReturn(null);

        assertThrows(EventException.class, () -> eventRegistrationService.unregisterEvent(request));
    }
}
