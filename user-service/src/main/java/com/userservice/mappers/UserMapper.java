package com.userservice.mappers;

import com.userservice.dto.UserDto;
import com.userservice.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto convertToDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserDto(
                user.getUserId(),
                user.getUsername(),
                user.getEmail());
    }
}
