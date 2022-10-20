package ru.university.portal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.university.portal.dto.CreateTaskDTO;
import ru.university.portal.dto.MessageResponse;
import ru.university.portal.service.TaskService;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/task/{taskId}/{teacherId}")
    public ResponseEntity<?> getTeacherTask(@PathVariable Long taskId,
                                            @PathVariable Long teacherId) {
        return ResponseEntity.ok().body(taskService.findTaskByIdForTeacher(taskId, teacherId));
    }

    @GetMapping("/all-task/{teacherId}/{page}")
    public ResponseEntity<?> getAllTeacherTask(@PathVariable Long teacherId,
                                               @PathVariable int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseEntity.ok().body(taskService.findAllTeacherTasks(teacherId, pageable));
    }

    @PostMapping("/create-task")
    public ResponseEntity<?> createTask(@RequestBody CreateTaskDTO dto) {
        taskService.createTask(dto);
        return ResponseEntity.ok().body(new MessageResponse(dto.getTeacher().getFullName() +
                ", вы создали задание " + dto.getName()));
    }

    @GetMapping("/task-answers/{teacherId}/{taskId}")
    public ResponseEntity<?> getTaskAnswersForTeacher(@PathVariable Long teacherId,
                                                      @PathVariable Long taskId) {
        return ResponseEntity.ok().body(taskService.getTaskAnswersForTeacher(taskId, teacherId));
    }

    //    для админа
    @GetMapping("/task-answers/{taskId}")
    public ResponseEntity<?> getTask(@PathVariable Long taskId) {
        return ResponseEntity.ok().body(taskService.findTaskById(taskId));
    }

    //    для админа
    @GetMapping("/task-answers/{taskId}")
    public ResponseEntity<?> getTaskAnswers(@PathVariable Long taskId) {
        return ResponseEntity.ok().body(taskService.findTaskById(taskId).getTaskAnswers());
    }
}
