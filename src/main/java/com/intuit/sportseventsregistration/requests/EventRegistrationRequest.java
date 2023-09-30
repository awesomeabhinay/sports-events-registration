package com.intuit.sportseventsregistration.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventRegistrationRequest {
    int eventId;
    String username;
}
