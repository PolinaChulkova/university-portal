package ru.university.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import java.util.Date;

@AllArgsConstructor
@Getter@Setter
public class UpdateTaskDTO {
    @Size(min = 3, max = 10, message = "Длина названия от 3 до 10 символов")
    private String name;
    @Max(value = 200, message = "Размер описания до 200 символов")
    private String description;
    @Future(message = "Нельзя указать текущую или прошедшую дату")
    private Date deadLine;
}

