package ru.university.portal.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter@Setter
public class UpdateTaskDTO {
    private String name;
    private String description;
    private Date deadLine;
    private Set<String> fileUri;
}

