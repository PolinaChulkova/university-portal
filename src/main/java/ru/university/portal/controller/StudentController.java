package ru.university.portal.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('STUDENT')")
    @RabbitListener(queues = "studentQueue")
    public void notificationListener(String message) {
        log.info("Студент получил сообщение: " + message);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<?> findStudentById(@PathVariable Long studentId) {
        return ResponseEntity.ok().body(studentService.findStudentById(studentId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> createStudent(@RequestBody StudentDTO dto) {
        try {
            return ResponseEntity.ok().body(studentService.registerStudent(dto));

        } catch (RuntimeException e) {
            log.error("Студент с email: " + dto.getEmail() + " не создан. {}"
                    + e.getLocalizedMessage());

            return ResponseEntity.badRequest().body(new MessageResponse("Студент с email: " + dto.getEmail()
                    + " не создан. Error: " + e.getLocalizedMessage()));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{studentId}")
    public ResponseEntity<?> updateStudent(@PathVariable Long studentId,
                                           @RequestBody StudentDTO dto) {
        try {
            return ResponseEntity.ok().body(studentService.updateStudent(studentId, dto));

        } catch (RuntimeException e) {
            log.error("Студент с Id= " + studentId + " не обновлён. {}"
                    + e.getLocalizedMessage());

            return ResponseEntity.badRequest().body(new MessageResponse("Студент с email: " + dto.getEmail()
                    + " не обновлён. Error: " + e.getLocalizedMessage()));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{studentId}")
    public ResponseEntity<?> deleteStudentById(@PathVariable Long studentId) {
        studentService.deleteStudentById(studentId);
        return ResponseEntity.ok().body(new MessageResponse("Студент с id=" + studentId + " удалён."));
    }
}