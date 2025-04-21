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
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody CreateUserRequestDto createUserRequestDto) {
        UserDto newUserDto = userService.registerNewUser(createUserRequestDto);
        URI location = ServletUriComponentsBuilder //very strange
                .fromCurrentContextPath()
                .path("/{id}") // /api/users/{id}
                .buildAndExpand(newUserDto.userId())
                .toUri();

        return ResponseEntity.created(location).body(newUserDto); //201
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return  userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<String> handleDuplicateResourceException(DuplicateResourceException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}
