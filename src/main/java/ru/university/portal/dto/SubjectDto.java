package ru.university.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@AllArgsConstructor
@Getter@Setter
public class SubjectDto {
    private String subjectName;
    private Collection<Long> teachersId;
}
