package com.intuit.sportseventsregistration.mapper;

import com.intuit.sportseventsregistration.dto.User;
import com.intuit.sportseventsregistration.responses.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(User user);
}
