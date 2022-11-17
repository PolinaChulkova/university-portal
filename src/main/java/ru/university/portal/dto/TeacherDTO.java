package ru.university.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class TeacherDTO{
    @NotNull
    private final String fullName;
    @NotNull
    @Email
    private final String email;
    @NotNull
    private final String password;
    @NotNull
    private final String phoneNum;
    @NotNull
    private final String role;

    private final String academicDegree;
}
