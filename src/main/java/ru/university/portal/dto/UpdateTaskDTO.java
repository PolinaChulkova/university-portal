package ru.university.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@Getter@Setter
public class UpdateTaskDTO {
    private String name;
    private String description;
    private Date deadLine;
    private Set<String> fileUri;
}

