package com.userservice.service;

import com.userservice.dto.CreateUserRequestDto;
import com.userservice.dto.UserDto;
import com.userservice.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {

 UserDto registerNewUser(CreateUserRequestDto createUserRequestDto);
 Optional<UserDto> getUserById(Long id);
}
