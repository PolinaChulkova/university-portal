package ru.university.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.Collection;

@AllArgsConstructor
@Getter@Setter
public class SubjectDto {
    @Size(min = 3, max = 20, message = "Название предмета длиной от 3 до 20 символов")
    private String subjectName;
    private Collection<Long> teachersId;
}
