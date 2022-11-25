package ru.university.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.Date;

@AllArgsConstructor
@Getter@Setter
public class CreateTaskDTO {
    @Size(min = 3, max = 10, message = "Длина названия от 3 до 10 символов")
    private String name;
    @Max(value = 200, message = "Размер описания до 200 символов")
    private String description;
    @FutureOrPresent(message = "Нельзя указать прошедшую дату")
    private Date startLine;
    @Future(message = "Нельзя указать текущую или прошедшую дату")
    private Date deadLine;
    @NotNull(message = "Выберите группу, для которой создаёте задание")
    private Long groupId;
}
