package ru.university.portal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class StudentDTO {
    private String fullName;
    private String email;
    private String password;
    private String phoneNum;
}
