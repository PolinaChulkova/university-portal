package ru.university.portal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.university.portal.dto.CreateTaskDTO;
import ru.university.portal.dto.MessageResponse;
import ru.university.portal.service.TaskAnswerService;
import ru.university.portal.service.TaskService;
import ru.university.portal.service.TeacherService;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;
    private final TaskService taskService;
    private final TaskAnswerService taskAnswerService;

    @PostMapping("/create-task")
    public ResponseEntity<?> createTask(@RequestBody CreateTaskDTO dto) {
        taskService.createTask(dto);
        return ResponseEntity.ok().body(new MessageResponse(dto.getTeacher().getFullName() +
                ", вы создали задание " + dto.getName()));
    }

    @GetMapping("/task-answers")
    public ResponseEntity<?> getTaskAnswers(Long taskId) {
        return ResponseEntity.ok().body(taskService.getTaskAnswers(taskId));
    }
}
