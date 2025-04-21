package com.userservice.service.implementations;

import com.userservice.dto.CreateUserRequestDto;
import com.userservice.dto.UserDto;
import com.userservice.exceptions.DuplicateResourceException;
import com.userservice.mappers.UserMapper;
import com.userservice.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.userservice.repository.UserRepository;
import com.userservice.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;     //to hide passwords

    //TODO bean for encoder
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.userMapper = userMapper;
    }

    @Transactional
    public UserDto registerNewUser(CreateUserRequestDto createUserRequestDto){
        //maybe put it in other checker function? don't think so
        if (userRepository.existsByUsername(createUserRequestDto.username()))
        {
            throw new DuplicateResourceException("Username " + createUserRequestDto.username() + " already exists");
        }
        if (userRepository.existsByEmail(createUserRequestDto.email()))
        {
            throw new DuplicateResourceException("User with email " + createUserRequestDto.email() + " already exists");
        }

        User user = new User();
        user.setUsername(createUserRequestDto.username());
        user.setEmail(createUserRequestDto.email());
        user.setHashedPassword(passwordEncoder.encode(createUserRequestDto.password()));
        //user.setActive = true;// or false (email confirm).


        User savedUser = userRepository.save(user); //db is alive?

        UserDto savedUserDto = userMapper.convertToDto(savedUser);
        //kafkaTemplate.send(topic = "", savedUserDto);
        return savedUserDto;
        //TODO kafka if needed? send confirm email through notificationService. transactional then
    }

    @Transactional(readOnly = true)
    public Optional<UserDto> getUserById(Long id){
        return userRepository.findById(id)
                .map(userMapper::convertToDto);
    }
}
