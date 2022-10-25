package ru.university.portal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.university.portal.dto.MessageResponse;
import ru.university.portal.dto.StudentDTO;
import ru.university.portal.service.StudentService;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/{studentId}")
    public ResponseEntity<?> findStudentById(@PathVariable Long studentId) {
        return ResponseEntity.ok().body(studentService.findStudentById(studentId));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createStudent(@RequestParam("groupId") Long groupId,
                                           @RequestBody StudentDTO dto) {
        studentService.createStudent(dto, groupId);
        return ResponseEntity.ok().body(new MessageResponse("Создан студент с email: " + dto.getEmail()));
    }

    @PostMapping("/update/{studentId}")
    public ResponseEntity<?> updateStudent(@PathVariable Long studentId,
                                           @RequestBody StudentDTO dto) {
        studentService.updateStudent(studentId, dto);
        return ResponseEntity.ok().body(new MessageResponse("Обновлён студент с email: " + dto.getEmail()));
    }

    @DeleteMapping("/delete/{studentId}")
    public ResponseEntity<?> deleteStudentById(@PathVariable Long studentId) {
        studentService.deleteStudentById(studentId);
        return ResponseEntity.ok().body(new MessageResponse("Студент с id=" + studentId + "удалён."));
    }
}