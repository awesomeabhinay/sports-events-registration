package com.intuit.sportseventsregistration.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventRegistrationResponse {
    int registrationId;
    int eventId;
    String username;
}
