package com.intuit.sportseventsregistration.services;

import com.intuit.sportseventsregistration.entities.User;
import com.intuit.sportseventsregistration.requests.LoginRequest;

public interface LoginService {

    User loginUser(LoginRequest loginRequest);
}
