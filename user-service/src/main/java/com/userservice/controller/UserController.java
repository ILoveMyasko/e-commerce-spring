package com.userservice.controller;

import com.userservice.dto.CreateUserRequestDto;
import com.userservice.dto.UserDto;
import com.userservice.exceptions.DuplicateResourceException;
import jakarta.validation.Valid;
import com.userservice.mappers.UserMapper;
import com.userservice.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.userservice.service.UserService;
import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody CreateUserRequestDto createUserRequestDto) {
        User newUser = userService.registerNewUser(createUserRequestDto);
        UserDto createdUserDto = userMapper.convertToDto(newUser);
        URI location = ServletUriComponentsBuilder //very strange
                .fromCurrentContextPath()
                .path("/{id}") // /api/users/{id}
                .buildAndExpand(newUser.getUserId())
                .toUri();

        return ResponseEntity.created(location).body(createdUserDto); //201
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return  userService.findByUserId(id)
                .map(userMapper::convertToDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<String> handleDuplicateResourceException(DuplicateResourceException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}
