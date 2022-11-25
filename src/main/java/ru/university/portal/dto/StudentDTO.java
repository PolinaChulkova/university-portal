package ru.university.portal.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.*;

@AllArgsConstructor
@Getter
public class StudentDTO {
    @Size(min = 5, max = 30, message = "Укажите имя студента длиной от 5 до 30 символов")
    private final String fullName;
    @Email(message = "Укажите действительный email")
    private final String email;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[@#$%^&-+=()])(?=\\\\S+$).{8,20}$",
            message = "Создайте пароль длиной от 8 до 20 символов без пробелов, используя цифры, " +
                    "строчные буквы и специальные символы")
    private final String password;

    @NotEmpty(message = "Введите номер телефона")
    private final String phoneNum;
}
