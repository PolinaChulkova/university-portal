package ru.university.portal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.university.portal.dto.CreateTaskDTO;
import ru.university.portal.dto.MessageResponse;
import ru.university.portal.service.TaskService;
import ru.university.portal.service.TeacherService;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;
    private final TaskService taskService;

    @PostMapping("/create-task")
    public ResponseEntity<?> createTask(@RequestBody CreateTaskDTO dto) {
        taskService.createTask(dto);
        return ResponseEntity.ok().body(new MessageResponse(dto.getTeacher().getFullName() +
                ", вы создали задание " + dto.getName()));
    }


}
