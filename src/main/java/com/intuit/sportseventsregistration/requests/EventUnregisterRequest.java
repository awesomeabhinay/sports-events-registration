package com.intuit.sportseventsregistration.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventUnregisterRequest {
    int eventId;
    String username;
}
