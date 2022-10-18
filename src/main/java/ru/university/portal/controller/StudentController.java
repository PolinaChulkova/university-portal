package ru.university.portal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.university.portal.dto.MessageResponse;
import ru.university.portal.dto.TaskAnswerDTO;
import ru.university.portal.service.TaskAnswerService;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final TaskAnswerService taskAnswerService;

    @PostMapping("/task-answer")
    private ResponseEntity<?> sendTaskAnswer(@RequestBody TaskAnswerDTO dto) {
        taskAnswerService.sendTaskAnswer(dto);
        return ResponseEntity.ok().body(new MessageResponse(dto.getStudent().getFullName()) +
                ", вы отправили ответ на задание");
    }
}
