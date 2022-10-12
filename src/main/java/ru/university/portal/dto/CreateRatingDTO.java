package ru.university.portal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class CreateRatingDTO {
    private Short mark;
    private String comment;
    private Long taskId;
    private String subjectName;
    private Long studentId;
}
