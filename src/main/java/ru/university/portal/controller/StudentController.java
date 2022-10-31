package ru.university.portal.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.university.portal.dto.MessageResponse;
import ru.university.portal.dto.StudentDTO;
import ru.university.portal.service.StudentService;

@RestController
@RequestMapping("/student")
@AllArgsConstructor
@Slf4j
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/{studentId}")
    public ResponseEntity<?> findStudentById(@PathVariable Long studentId) {
        return ResponseEntity.ok().body(studentService.findStudentById(studentId));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createStudent(@RequestBody StudentDTO dto) {
        try {
            studentService.createStudent(dto);
            return ResponseEntity.ok().body(new MessageResponse("Создан студент с email: " + dto.getEmail()));

        } catch (RuntimeException e) {
            log.error("Студент с email: " + dto.getEmail() + " не создан. {}"
                    + e.getLocalizedMessage());

            return ResponseEntity.badRequest().body(new MessageResponse("Студент с email: " + dto.getEmail()
                    + " не создан. Error: " + e.getLocalizedMessage()));
        }
    }

    @PutMapping("/update/{studentId}")
    public ResponseEntity<?> updateStudent(@PathVariable Long studentId,
                                           @RequestBody StudentDTO dto) {
        try {
            studentService.updateStudent(studentId, dto);
            return ResponseEntity.ok().body(new MessageResponse("Обновлён студент с email: " + dto.getEmail()));

        } catch (RuntimeException e) {
            log.error("Студент с Id= " + studentId + " не обновлён. {}"
                    + e.getLocalizedMessage());

            return ResponseEntity.badRequest().body(new MessageResponse("Студент с email: " + dto.getEmail()
                    + " не обновлён. Error: " + e.getLocalizedMessage()));
        }
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<?> deleteStudentById(@PathVariable Long studentId) {
        studentService.deleteStudentById(studentId);
        return ResponseEntity.ok().body(new MessageResponse("Студент с id=" + studentId + " удалён."));
    }
}