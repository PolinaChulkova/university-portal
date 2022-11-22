package ru.university.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter@Setter
public class LoginDTO {
    private String email;
    private String password;
}
