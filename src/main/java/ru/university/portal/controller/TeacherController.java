package ru.university.portal.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.university.portal.dto.LoginDTO;
import ru.university.portal.dto.MessageResponse;
import ru.university.portal.dto.TeacherDTO;
import ru.university.portal.service.TeacherService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/teacher")
@AllArgsConstructor
@Slf4j
public class TeacherController {

    private final TeacherService teacherService;

    @RabbitListener(queues = "teacherQueue")
    public void notificationListener(String message) {
        log.info("Преподаватель получил сообщение: " + message);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<?> createTeacher(@RequestBody TeacherDTO dto) {
        try {
            return ResponseEntity.ok().body(teacherService.createTeacher(dto));

        } catch (RuntimeException e) {
            log.error("Преподаватель с email: " + dto.getEmail() + " не создан. Error: "
                    + e.getLocalizedMessage());

            return ResponseEntity.badRequest().body(new MessageResponse("Преподаватель с email: " + dto.getEmail())
                    + " не создан. Error: " + e.getLocalizedMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginTeacher(@RequestBody LoginDTO dto, HttpServletResponse response,
                                          HttpSession session) {
        try {
            Cookie cookie = new Cookie("SESSION", session.getId());
            response.addCookie(cookie);
            return ResponseEntity.ok().body(teacherService.findTeacherByEmail(dto.getEmail()));
        } catch (RuntimeException e) {
            log.error("Преподаватель с email: " + dto.getEmail() + " не создан. Error: "
                    + e.getLocalizedMessage());
            SecurityContextHolder.clearContext();
            return ResponseEntity.badRequest().body(new MessageResponse("Преподаватель с email: " + dto.getEmail())
                    + " не создан. Error: " + e.getLocalizedMessage());
        }
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("teacher/groups/{teacherId}")
    public ResponseEntity<?> findTeacherGroups(@PathVariable Long teacherId) {
        return ResponseEntity.ok().body(teacherService.findTeacherById(teacherId).getGroups());
    }

    @GetMapping("/{teacherId}")
    public ResponseEntity<?> findTeacherById(@PathVariable Long teacherId) {
        return ResponseEntity.ok().body(teacherService.findTeacherById(teacherId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{teacherId}")
    public ResponseEntity<?> updateTeacher(@PathVariable Long teacherId,
                                           @RequestBody TeacherDTO dto) {
        try {
            teacherService.updateTeacher(teacherId, dto);
            return ResponseEntity.ok().body(new MessageResponse("Обновлён преподаватель с email: "
                    + dto.getEmail()));

        } catch (RuntimeException e) {
            log.error("Преподватель с Id= " + teacherId + " не обновлён. {}"
                    + e.getLocalizedMessage());

            return ResponseEntity.badRequest().body(new MessageResponse("Преподаватель с email: "
                    + dto.getEmail() + " не обновлён. Error: ") + e.getLocalizedMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{teacherId}")
    public ResponseEntity<?> deleteTeacherById(@PathVariable Long teacherId) {
        teacherService.deleteTeacherById(teacherId);
        return ResponseEntity.ok().body(new MessageResponse("Удалён преподаватель с id=" + teacherId));
    }
}
