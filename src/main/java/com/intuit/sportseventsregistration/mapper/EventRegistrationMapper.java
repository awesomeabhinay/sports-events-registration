package com.intuit.sportseventsregistration.mapper;

import com.intuit.sportseventsregistration.entities.Event;
import com.intuit.sportseventsregistration.entities.EventRegistration;
import com.intuit.sportseventsregistration.entities.User;
import com.intuit.sportseventsregistration.requests.EventRegistrationRequest;
import com.intuit.sportseventsregistration.responses.EventRegistrationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventRegistrationMapper {

    @Mapping(target = "registrationId", source = "eventRegistration.id")
    @Mapping(target = "eventId", source = "eventRegistration.event.id")
    @Mapping(target = "username", source = "eventRegistration.user.username")
    EventRegistrationResponse toEventRegistrationResponse(EventRegistration eventRegistration);

    @Mapping(target = "id", ignore = true)
    EventRegistration toDto(Event event, User user);
}
