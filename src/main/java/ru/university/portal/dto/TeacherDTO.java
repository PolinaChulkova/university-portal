package ru.university.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@AllArgsConstructor
@Getter@Setter
public class TeacherDTO {
    @Size(min = 5, max = 30, message = "Укажите имя преподавателя длиной от 5 до 30 символов")
    private String fullName;
    @Email(message = "Укажите действительный email")
    private String email;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[@#$%^&-+=()])(?=\\\\S+$).{8,20}$",
            message = "Создайте пароль длиной от 8 до 20 символов без пробелов, используя цифры, " +
                    "строчные буквы и специальные символы")
    private String password;

    @NotEmpty(message = "Введите номер телефона")
    private String phoneNum;
    @Max(value = 20, message = "Длина строки до 20 символов")
    private String academicDegree;
    @NotEmpty(message = "Выберите роль")
    private String role;
}
