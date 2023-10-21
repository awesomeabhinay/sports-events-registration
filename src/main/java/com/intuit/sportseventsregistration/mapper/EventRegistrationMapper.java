package com.intuit.sportseventsregistration.mapper;

import com.intuit.sportseventsregistration.dto.Event;
import com.intuit.sportseventsregistration.dto.EventRegistration;
import com.intuit.sportseventsregistration.dto.User;
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
