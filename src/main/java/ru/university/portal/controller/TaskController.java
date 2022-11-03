package ru.university.portal.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.university.portal.dto.CreateTaskDTO;
import ru.university.portal.dto.MessageResponse;
import ru.university.portal.dto.UpdateTaskDTO;
import ru.university.portal.service.TaskService;

@RestController
@RequestMapping("/task")
@AllArgsConstructor
@Slf4j
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/student/{studentId}/{taskId}")
    public ResponseEntity<?> getStudentTask(@PathVariable Long studentId,
                                            @PathVariable Long taskId) {
        return ResponseEntity.ok().body(taskService.findTaskByIdForStudent(taskId, studentId));
    }

    @GetMapping("/subject/{subjectId}/{studentId}")
    public ResponseEntity<?> getAllTaskBySubjectForStudent(@PathVariable Long subjectId,
                                                           @PathVariable Long studentId) {
        return ResponseEntity.ok().body(taskService.findAllTasksBySubjectForStudent(subjectId, studentId));
    }

    @GetMapping("/teacher/{taskId}/{teacherId}")
    public ResponseEntity<?> getTeacherTask(@PathVariable Long taskId,
                                            @PathVariable Long teacherId) {
        return ResponseEntity.ok().body(taskService.findTaskByIdForTeacher(taskId, teacherId));
    }

    @GetMapping("/all-for-teacher/{teacherId}/{page}")
    public ResponseEntity<?> getAllTeacherTasks(@PathVariable Long teacherId,
                                                @PathVariable int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseEntity.ok().body(taskService.findAllTeacherTasks(teacherId, pageable));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestBody CreateTaskDTO dto) {
        try {
            taskService.createTask(dto);
            return ResponseEntity.ok().body(new MessageResponse("Задание создано"));

        } catch (RuntimeException e) {
            log.error("Задание " + dto.getName() + " не создано. Error: "
                    + e.getLocalizedMessage());

            return ResponseEntity.badRequest().body(new MessageResponse("Задание не создано"));
        }
    }

    @PostMapping("/upload/{taskId}")
    public ResponseEntity<?> uploadFile(@PathVariable Long taskId,
                                        @RequestParam("files") MultipartFile[] files) {
        try {
            taskService.uploadFileToTask(taskId, files);

        } catch (RuntimeException e) {
            log.error("Файлы к заданию id=" + taskId + " не загужены. Error:" + e.getLocalizedMessage());
        }
        return ResponseEntity.ok().body(new MessageResponse("Файлы не загужены"));
    }

    @PutMapping("/update/{taskId}")
    public ResponseEntity<?> updateTeacherTask(@PathVariable Long taskId,
                                               @RequestBody UpdateTaskDTO dto) {
        try {
            taskService.updateTask(taskId, dto);
            return ResponseEntity.ok().body(new MessageResponse("Задание " + dto.getName() + " обновлено."));

        } catch (RuntimeException e) {
            log.error("Задание " + dto.getName() + " не обновлёно. Error: "
                    + e.getLocalizedMessage());
            return ResponseEntity.ok().body(new MessageResponse("Задание " + dto.getName()
                    + " не обновлено."));
        }

    }

    //    для админа
    @GetMapping("/{taskId}")
    public ResponseEntity<?> getTask(@PathVariable Long taskId) {
        return ResponseEntity.ok().body(taskService.findTaskById(taskId));
    }

    //    для админа
    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTaskById(taskId);
        return ResponseEntity.ok().body(new MessageResponse("Задание с id=" + taskId + " удалено."));
    }
}
