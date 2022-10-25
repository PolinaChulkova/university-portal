package ru.university.portal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.university.portal.dto.CreateTaskAnswerDTO;
import ru.university.portal.dto.MessageResponse;
import ru.university.portal.dto.UpdateTaskAnswerDTO;
import ru.university.portal.service.TaskAnswerService;

@RestController
@RequestMapping("/task-answer")
@RequiredArgsConstructor
public class TaskAnswerController {

    private final TaskAnswerService taskAnswerService;

    @GetMapping("/for-student/{studentId}/{taskId}")
    public ResponseEntity<?> getTaskAnswerForStudent(@PathVariable Long studentId,
                                                     @PathVariable Long taskId) {
        return ResponseEntity.ok().body(taskAnswerService.getTaskAnswerForStudent(taskId, studentId));
    }

    @GetMapping("/for-teacher/{teacherId}/{taskId}")
    public ResponseEntity<?> getTaskAnswersForTeacher(@PathVariable Long teacherId,
                                                      @PathVariable Long taskId) {
        return ResponseEntity.ok().body(taskAnswerService.getTaskAnswersForTeacher(taskId, teacherId));
    }

    @PostMapping("/send")
    private ResponseEntity<?> sendTaskAnswer(@RequestBody CreateTaskAnswerDTO dto) {
        taskAnswerService.sendTaskAnswer(dto);
        return ResponseEntity.ok().body(new MessageResponse(dto.getStudent().getFullName()) +
                ", вы отправили ответ на задание");
    }

    @PutMapping("/update/{taskAnswerId}")
    public ResponseEntity<?> updateTaskAnswer(@PathVariable Long taskAnswerId,
                                              @RequestBody UpdateTaskAnswerDTO dto) {
        taskAnswerService.updateTaskAnswer(taskAnswerId, dto);
        return ResponseEntity.ok().body(new MessageResponse("Вы обновили свой ответ на задние"));
    }

    @GetMapping("/{taskAnswerId}")
    public ResponseEntity<?> findTaskAnswerById(@PathVariable Long taskAnswerId) {
        return ResponseEntity.ok().body(taskAnswerService.findTaskAnswerById(taskAnswerId));
    }
}
