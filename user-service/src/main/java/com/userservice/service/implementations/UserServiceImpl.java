package com.userservice.service.implementations;

import com.userservice.dto.CreateUserRequestDto;
import com.userservice.exceptions.DuplicateResourceException;
import com.userservice.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.userservice.repository.UserRepository;
import com.userservice.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    //@Transactional()
    public User registerNewUser(CreateUserRequestDto createUserRequestDto){
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
        return userRepository.save(user); //db is alive?
        //TODO kafka if needed? send confirm email through notificationService. transactional then
    }

    public Optional<User> findByUserId(Long id){
        return userRepository.findById(id);
    }
}
