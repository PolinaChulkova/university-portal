package ru.university.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter@Setter
public class CreateRatingDTO {
    @NotNull(message = "Поставьте оценку")
    private Short mark;
    @Max(value = 50, message = "Длина комментария до 50 символов")
    private String comment;
    @NotNull(message = "Оцениваемое задание")
    private Long taskId;
    @NotNull(message = "Выберите предмет")
    private Long subjectId;
    @NotNull(message = "Выберите студента")
    private Long studentId;
}
