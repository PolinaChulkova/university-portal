package ru.university.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

@AllArgsConstructor
@Getter@Setter
public class CreateGroupDTO {
    @NotNull(message = "Ведите название группы")
    @Size(min = 5, max = 10, message = "Длина названия группы от 5 до 10 символов")
    private String name;
    private Collection<Long> studentsId;
}
