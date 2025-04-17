package com.userservice.dto;

import com.userservice.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
public record CreateUserRequestDto(Long userId,
                                   @NotBlank
                                   @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
                                   String username,
                                   @NotBlank
                                   @Email(message = "Invalid email format")
                                   String email,
                                   @NotBlank
                                   @Size(min = 8, max = 30, message = "Password must be between 8 and 30 characters")
                                   String password //not hashed yet
) implements Serializable {
}