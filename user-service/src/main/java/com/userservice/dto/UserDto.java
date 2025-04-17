package com.userservice.dto;

import com.userservice.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
public record UserDto(Long userId,
                      @NotBlank
                      String username,
                      @Email @NotBlank
                      String email)
        implements Serializable {
}