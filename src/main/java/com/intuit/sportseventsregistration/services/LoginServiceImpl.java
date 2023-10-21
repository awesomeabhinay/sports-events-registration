package com.intuit.sportseventsregistration.services;

import com.intuit.sportseventsregistration.entities.User;
import com.intuit.sportseventsregistration.exceptions.LoginException;
import com.intuit.sportseventsregistration.repository.UserRepository;
import com.intuit.sportseventsregistration.requests.LoginRequest;
import com.intuit.sportseventsregistration.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService{

    @Autowired
    UserRepository userRepository;

    @Override
    public User loginUser(LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());
        if(user.isPresent()){
            return user.get();
        } else{
            throw new LoginException(String.format("User not found with username: %s", loginRequest.getUsername()));
        }
    }
}
