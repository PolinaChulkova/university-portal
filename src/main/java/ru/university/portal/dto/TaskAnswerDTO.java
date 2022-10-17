package ru.university.portal.dto;

import lombok.Getter;
import lombok.Setter;
import ru.university.portal.model.Student;

import java.util.Date;
import java.util.Set;

@Getter@Setter
public class TaskAnswerDTO {
    private String comment;
    private Date date;
    private Set<String> fileUri;
    //    после созании Security студент будет получаться из Principal
    private Student student;
    private Long taskId;
}
