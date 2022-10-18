package ru.university.portal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PutMapping("/update-answer/{taskAnswerId}")
    public ResponseEntity<?> updateAnswer(@PathVariable Long taskAnswerId,
                                              @RequestBody TaskAnswerDTO dto) {
        taskAnswerService.updateTaskAnswer(taskAnswerId, dto);
        return ResponseEntity.ok().body(new MessageResponse(dto.getStudent().getFullName() +
                ", вы обновили свой ответ на задние"));
    }
}
