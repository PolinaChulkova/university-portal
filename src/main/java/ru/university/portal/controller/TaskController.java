package ru.university.portal.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.university.portal.dto.CreateTaskDTO;
import ru.university.portal.dto.MessageResponse;
import ru.university.portal.dto.UpdateTaskDTO;
import ru.university.portal.model.Task;
import ru.university.portal.service.TaskService;

import java.security.Principal;

@RestController
@RequestMapping("/task")
@AllArgsConstructor
@Slf4j
public class TaskController {

    private final TaskService taskService;
    private final AmqpTemplate template;

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/student/{taskId}")
    public ResponseEntity<?> getStudentTask(@PathVariable Long taskId, Principal principal) {
        return ResponseEntity.ok().body(taskService.findTaskByIdForStudent(taskId, principal.getName()));
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/subject/{subjectId}/{studentId}")
    public ResponseEntity<?> getAllTaskBySubjectForStudent(@PathVariable Long subjectId,
                                                           @PathVariable Long studentId) {
        return ResponseEntity.ok().body(taskService.findAllTasksBySubjectForStudent(subjectId, studentId));
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/teacher/{taskId}/{teacherId}")
    public ResponseEntity<?> getTeacherTask(@PathVariable Long taskId,
                                            @PathVariable Long teacherId) {
        return ResponseEntity.ok().body(taskService.findTaskByIdForTeacher(taskId, teacherId));
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/all-for-teacher/{teacherId}/{page}")
    public ResponseEntity<?> getAllTeacherTasks(@PathVariable Long teacherId,
                                                @PathVariable int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseEntity.ok().body(taskService.findAllTeacherTasks(teacherId, pageable));
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestBody CreateTaskDTO dto, Principal principal) {
        try {
            Task task = taskService.createTask(dto, principal.getName());
            template.convertAndSend("studentQueue", "Создано новое задание по предмету \""
                    + task.getName() + "\"");
            return ResponseEntity.ok().body(task);

        } catch (RuntimeException e) {
            log.error("Задание " + dto.getName() + " не создано. Error: "
                    + e.getLocalizedMessage());

            return ResponseEntity.badRequest().body(new MessageResponse("Задание не создано. Error: "
                    + e.getLocalizedMessage()));
        }
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/upload/{taskId}")
    public ResponseEntity<?> uploadFilesToTask(@PathVariable Long taskId,
                                               @RequestParam("files") MultipartFile[] files) {
        try {
            taskService.uploadFilesToTask(taskId, files);
            return ResponseEntity.ok().body(new MessageResponse("Файлы загружены"));

        } catch (RuntimeException e) {
            log.error("Не удалось загрузить файлы к заданию с id=" + taskId
                    + " Error:" + e.getLocalizedMessage());

            return ResponseEntity.badRequest().body(new MessageResponse("Не удалось загрузить файлы." +
                    "Error: " + e.getLocalizedMessage()));
        }
    }

    @PreAuthorize("hasRole('TEACHER')")
    @DeleteMapping("/delete-file/{taskId}")
    public ResponseEntity<?> deleteFileFromTask(@PathVariable Long taskId,
                                                @RequestParam("file-uri") String fileUri) {
        try {
            taskService.deleteFileFromTask(taskId, fileUri);
            return ResponseEntity.ok().body(new MessageResponse("Файл удалён из задания"));

        } catch (RuntimeException e) {
            log.error("Не удалось удалить файл с uri: " + fileUri + " Error: " + e.getLocalizedMessage());

            return ResponseEntity.internalServerError()
                    .body(new MessageResponse("Не удалось удалить файл из задания. Error: "
                            + e.getLocalizedMessage()));
        }
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PutMapping("/update/{taskId}")
    public ResponseEntity<?> updateTeacherTask(@PathVariable Long taskId,
                                               @RequestBody UpdateTaskDTO dto,
                                               Principal principal) {
        try {
            taskService.updateTask(taskId, dto, principal.getName());
            return ResponseEntity.ok().body(new MessageResponse("Задание " + dto.getName() + " обновлено."));

        } catch (RuntimeException e) {
            log.error("Задание " + dto.getName() + " не обновлёно. Error: "
                    + e.getLocalizedMessage());

            return ResponseEntity.ok().body(new MessageResponse("Задание " + dto.getName()
                    + " не обновлено. Error: " + e.getLocalizedMessage()));
        }
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<?> getTask(@PathVariable Long taskId) {
        return ResponseEntity.ok().body(taskService.findTaskById(taskId));
    }

    @PreAuthorize("hasRole('TEACHER')")
    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTaskById(taskId);
        return ResponseEntity.ok().body(new MessageResponse("Задание с id=" + taskId + " удалено."));
    }
}
