package ru.university.portal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.university.portal.dto.MessageResponse;
import ru.university.portal.dto.TaskAnswerDTO;
import ru.university.portal.service.TaskAnswerService;

@RestController
@RequestMapping("/task-answer")
@RequiredArgsConstructor
public class TaskAnswerController {

    private final TaskAnswerService taskAnswerService;

    @PostMapping("/send")
    private ResponseEntity<?> sendTaskAnswer(@RequestBody TaskAnswerDTO dto) {
        taskAnswerService.sendTaskAnswer(dto);
        return ResponseEntity.ok().body(new MessageResponse(dto.getStudent().getFullName()) +
                ", вы отправили ответ на задание");
    }

    @PutMapping("/update/{taskAnswerId}")
    public ResponseEntity<?> updateAnswer(@PathVariable Long taskAnswerId,
                                              @RequestBody TaskAnswerDTO dto) {
        taskAnswerService.updateTaskAnswer(taskAnswerId, dto);
        return ResponseEntity.ok().body(new MessageResponse(dto.getStudent().getFullName() +
                ", вы обновили свой ответ на задние"));
    }

    @GetMapping("/{taskAnswerId}")
    public ResponseEntity<?> findTaskAnswerById(@PathVariable Long taskAnswerId) {
        return ResponseEntity.ok().body(taskAnswerService.findTaskAnswerById(taskAnswerId));
    }
}
