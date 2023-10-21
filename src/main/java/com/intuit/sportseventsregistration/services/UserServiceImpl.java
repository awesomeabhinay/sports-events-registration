package com.intuit.sportseventsregistration.services;

import com.intuit.sportseventsregistration.entities.User;
import com.intuit.sportseventsregistration.exceptions.UserException;
import com.intuit.sportseventsregistration.repository.UserRepository;
import com.intuit.sportseventsregistration.responses.UserResponse;
import com.intuit.sportseventsregistration.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;
    @Override
    public UserResponse createUser(User user) {
        if(!checkIfUserExist(user.getUsername())){
            User user1 = userRepository.save(user);
            return UserResponse.builder().username(user1.getUsername()).email(user1.getEmail()).build();
        } else{
            throw new UserException(String.format("User with %s username already exist",user.getUsername()));
        }
    }

    private boolean checkIfUserExist(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent();
    }
}
