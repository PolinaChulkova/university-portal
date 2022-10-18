package ru.university.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter@Setter
public class CreateGroupDTO {
    private String name;
    private List<Long> studentsId;
}
