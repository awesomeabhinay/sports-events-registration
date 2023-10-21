package com.intuit.sportseventsregistration.services;

import com.intuit.sportseventsregistration.dto.User;
import com.intuit.sportseventsregistration.responses.UserResponse;

public interface UserService {
    UserResponse createUser(User user);
}
