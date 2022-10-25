package ru.university.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.university.portal.model.Student;

import java.util.Set;

@AllArgsConstructor
@Getter@Setter
public class CreateTaskAnswerDTO {
    private String comment;
    private Set<String> fileUri;
    //    после созании Security студент будет получаться из Principal
    private Student student;
    private Long taskId;
}
