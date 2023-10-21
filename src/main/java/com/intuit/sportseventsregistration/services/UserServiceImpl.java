package com.intuit.sportseventsregistration.services;

import com.intuit.sportseventsregistration.entities.User;
import com.intuit.sportseventsregistration.exceptions.UserException;
import com.intuit.sportseventsregistration.mapper.UserMapper;
import com.intuit.sportseventsregistration.repository.UserRepository;
import com.intuit.sportseventsregistration.responses.UserResponse;
import com.intuit.sportseventsregistration.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{


    private UserRepository userRepository;
    private UserMapper userMapper;
    @Autowired
    UserServiceImpl(UserRepository userRepository, UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }
    @Override
    public UserResponse createUser(User user) {
        if(!checkIfUserExist(user.getUsername())){
            return userMapper.toUserResponse(userRepository.save(user));
        } else{
            throw new UserException(String.format("User with %s username already exist",user.getUsername()));
        }
    }

    private boolean checkIfUserExist(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent();
    }
}
