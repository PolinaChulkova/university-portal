package ru.university.portal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class TeacherDTO {
    private Long id;
    private String fullName;
    private String email;
    private String password;
    private String phoneNum;
    private String academicDegree;
}
