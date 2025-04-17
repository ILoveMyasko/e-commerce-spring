package com.userservice.service;

import com.userservice.dto.CreateUserRequestDto;
import com.userservice.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {

 User registerNewUser(CreateUserRequestDto createUserRequestDto);
 Optional<User> findByUserId(Long id);
}
