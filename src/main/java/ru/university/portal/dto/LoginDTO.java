package ru.university.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class LoginDTO {
    @NotNull
    @Email
    private final String email;
    @NotNull
    private final String password;
    @NotNull
    private final String role;
}
