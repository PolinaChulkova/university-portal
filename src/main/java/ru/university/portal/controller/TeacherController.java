package ru.university.portal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.university.portal.dto.MessageResponse;
import ru.university.portal.dto.TeacherDTO;
import ru.university.portal.service.TeacherService;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping("/groups/{teacherId}")
    public ResponseEntity<?> findTeacherGroups(@PathVariable Long teacherId) {
        return ResponseEntity.ok().body(teacherService.findTeacherById(teacherId).getGroups());
    }

    @GetMapping("/{teacherId}")
    public ResponseEntity<?> findTeacherById(@PathVariable Long teacherId) {
        return ResponseEntity.ok().body(teacherService.findTeacherById(teacherId));
    }

    //    для админа
    @PostMapping("/create")
    public ResponseEntity<?> findTeacherGroups(@RequestBody TeacherDTO dto) {
        teacherService.createTeacher(dto);
        return ResponseEntity.ok().body(new MessageResponse("Создан преподаватель с email: " + dto.getEmail()));
    }

    //    для админа
    @PutMapping("/update/{teacherId}")
    public ResponseEntity<?> findTeacherGroups(@PathVariable Long teacherId,
                                               @RequestBody TeacherDTO dto) {
        teacherService.updateTeacher(teacherId, dto);
        return ResponseEntity.ok().body(new MessageResponse("Обновлён преподаватель с email: "
                + dto.getEmail()));
    }

    //    для админа
    @DeleteMapping("/{teacherId}")
    public ResponseEntity<?> deleteTeacherById(@PathVariable Long teacherId) {
        teacherService.deleteTeacherById(teacherId);
        return ResponseEntity.ok().body(new MessageResponse("Удалён преподаватель с id=" + teacherId));
    }
}
