package ru.university.portal.controller.auth;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.university.portal.dto.LoginDTO;
import ru.university.portal.dto.MessageResponse;
import ru.university.portal.dto.TeacherDTO;
import ru.university.portal.model.Teacher;
import ru.university.portal.service.TeacherService;
import ru.university.portal.service.AuthService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth/teacher")
@AllArgsConstructor
@Slf4j
public class TeacherAuthController {

    private final TeacherService teacherService;
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/registration")
    public ResponseEntity<?> registrationTeacher(@RequestBody TeacherDTO dto,
                                                 HttpServletResponse response) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    dto.getEmail(),
                    dto.getPassword()));
            Teacher teacher = teacherService.registerTeacher(dto);
            authService.setAllTokens(teacher, dto.getRole(), response);
            return ResponseEntity.ok().body(teacher);

        } catch (RuntimeException e) {
            log.error("Преподаватель с email: " + dto.getEmail() + " не создан. Error: "
                    + e.getLocalizedMessage());
            authService.clearCookie(response);
            return ResponseEntity.badRequest().body(new MessageResponse("Преподаватель с email: " + dto.getEmail())
                    + " не создан. Error: " + e.getLocalizedMessage());
        }
    }

//    @GetMapping("current")
//    public ResponseEntity<?> current() {
//        try {
//            AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            return buildUserResponse(appUser);
//        } catch (NullPointerException e) {
//            log.error(e.getLocalizedMessage());
//        }
//        return buildUserResponse(new AppUser());
//    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> loginTeacher(@RequestBody LoginDTO dto,
                                   HttpServletResponse response) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    dto.getEmail(),
                    dto.getPassword()));
            Teacher teacher =  (Teacher) new CachingUserDetailsService(teacherService)
                    .loadUserByUsername(dto.getEmail());
            authService.setAllTokens(teacher, teacher.getRole().name(), response);
            return ResponseEntity.ok().body(teacher);

        } catch (RuntimeException e) {
            log.error("Не удалось аутентифицироваться пользователю с username: "
                    + dto.getEmail() + ". Error: " + e.getLocalizedMessage());
            authService.clearCookie(response);
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Не удалось аутентифицироваться пользователю с username: "
                    + dto.getEmail() + ". Error: " + e.getLocalizedMessage()));
        }
    }
}
