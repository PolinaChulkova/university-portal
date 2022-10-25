package ru.university.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
public class UpdateTaskAnswerDTO {
    private String comment;
    private Set<String> fileUri;
}
