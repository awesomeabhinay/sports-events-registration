package com.intuit.sportseventsregistration.services;

import com.intuit.sportseventsregistration.dto.User;
import com.intuit.sportseventsregistration.exceptions.LoginException;
import com.intuit.sportseventsregistration.mapper.UserMapper;
import com.intuit.sportseventsregistration.repository.UserRepository;
import com.intuit.sportseventsregistration.requests.LoginRequest;
import com.intuit.sportseventsregistration.responses.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService{

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Autowired
    public LoginServiceImpl(UserRepository userRepository, UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponse loginUser(LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());
        if(user.isPresent()){
            return userMapper.toUserResponse(user.get());
        } else{
            throw new LoginException(String.format("User not found with username: %s", loginRequest.getUsername()));
        }
    }
}
