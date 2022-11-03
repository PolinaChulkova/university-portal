package ru.university.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.university.portal.model.Teacher;

import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@Getter@Setter
public class UpdateTaskDTO {
    private String name;
    private String description;
    private Date deadLine;
//    потом буду получать из principal
    private Long teacherId;
}

