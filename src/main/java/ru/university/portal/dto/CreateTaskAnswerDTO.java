package ru.university.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter@Setter
public class CreateTaskAnswerDTO {
    @Size(max = 200, message = "Длина комментария до 200 символов")
    private String comment;
    @NotNull(message = "Выберите задание")
    private Long taskId;
}
