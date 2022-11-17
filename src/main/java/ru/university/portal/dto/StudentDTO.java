package ru.university.portal.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class StudentDTO {
    @NotNull
    private final String fullName;
    @NotNull
    @Email
    private final String email;
    @NotNull
    private final String password;
    @NotNull
    private final String phoneNum;
}
