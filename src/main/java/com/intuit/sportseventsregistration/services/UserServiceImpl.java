package com.intuit.sportseventsregistration.services;

import com.intuit.sportseventsregistration.dto.User;
import com.intuit.sportseventsregistration.exceptions.UserException;
import com.intuit.sportseventsregistration.mapper.UserMapper;
import com.intuit.sportseventsregistration.repository.UserRepository;
import com.intuit.sportseventsregistration.responses.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{


    private final UserRepository userRepository;
    private final UserMapper userMapper;
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
