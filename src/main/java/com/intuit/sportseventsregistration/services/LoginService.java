package com.intuit.sportseventsregistration.services;

import com.intuit.sportseventsregistration.requests.LoginRequest;
import com.intuit.sportseventsregistration.responses.UserResponse;

public interface LoginService {

    UserResponse loginUser(LoginRequest loginRequest);
}
