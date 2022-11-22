package ru.university.portal.controller.auth;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import ru.university.portal.dto.LoginDTO;
import ru.university.portal.dto.MessageResponse;
import ru.university.portal.dto.StudentDTO;
import ru.university.portal.model.Student;
import ru.university.portal.service.StudentService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth/student")
@AllArgsConstructor
@Slf4j
public class StudentAuthController {

    private final StudentService studentService;
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/registration")
    public ResponseEntity<?> registrationStudent(@RequestBody StudentDTO dto,
                                                 HttpServletResponse response) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    dto.getEmail(),
                    dto.getPassword()));
            Student student = studentService.registerStudent(dto);
            authService.setAllTokens(student, dto.getRole(), response);
            return ResponseEntity.ok().body(student);

        } catch (RuntimeException e) {
            log.error("Студент с email: " + dto.getEmail() + " не создан. Error: "
                    + e.getLocalizedMessage());
            authService.clearCookie(response);
            return ResponseEntity.badRequest().body(new MessageResponse("Студент с email: "
                    + dto.getEmail()) + " не создан. Error: " + e.getLocalizedMessage());
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
    public ResponseEntity<?> loginStudent(@RequestBody LoginDTO dto,
                                          HttpServletResponse response) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    dto.getEmail(),
                    dto.getPassword()));
            Student student =  (Student) new CachingUserDetailsService(studentService)
                    .loadUserByUsername(dto.getEmail());
            authService.setAllTokens(student, student.getRole().name(), response);
            return ResponseEntity.ok().body(student);

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
