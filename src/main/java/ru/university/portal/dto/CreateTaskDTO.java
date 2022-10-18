package ru.university.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.university.portal.model.Group;
import ru.university.portal.model.Teacher;

import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@Getter@Setter
public class CreateTaskDTO {
    private String name;
    private String description;
    private Date startLine;
    private Date deadLine;
    private Set<String> fileUri;
    //    после созании Security преподаватель будет получаться из Principal
    private Teacher teacher;
    private Group group;
}
