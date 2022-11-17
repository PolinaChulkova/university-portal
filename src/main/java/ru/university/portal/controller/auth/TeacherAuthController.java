package ru.university.portal.controller.auth;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.university.portal.config.security.jwt.JwtTokenProvider;
import ru.university.portal.dto.LoginDTO;
import ru.university.portal.dto.MessageResponse;
import ru.university.portal.dto.TeacherDTO;
import ru.university.portal.model.Teacher;
import ru.university.portal.service.TeacherService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/auth/teacher")
@AllArgsConstructor
@Slf4j
public class TeacherAuthController {

    private final TeacherService teacherService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/registration")
    public ResponseEntity<?> registrationTeacher(@RequestBody TeacherDTO dto,
                                                 HttpServletResponse response) {
        try {
//            Teacher teacher = teacherService.registerTeacher(dto);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    dto.getEmail(),
                    dto.getPassword()));
            Teacher teacher =  (Teacher) new CachingUserDetailsService(teacherService)
                    .loadUserByUsername(dto.getEmail());
            setAuthToken(teacher, response);
            setRefreshToken(teacher, response);
            return ResponseEntity.ok()
                    .body(new MessageResponse("Создан преподаватель с email: " + dto.getEmail()));

        } catch (RuntimeException e) {
            log.error("Преподаватель с email: " + dto.getEmail() + " не создан. Error: "
                    + e.getLocalizedMessage());
            clearCookie(response);
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
    public ResponseEntity<?> login(@RequestBody LoginDTO dto,
                                   HttpServletResponse response) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    dto.getEmail(),
                    dto.getPassword()));
            Teacher teacher =  (Teacher) new CachingUserDetailsService(teacherService)
                    .loadUserByUsername(dto.getEmail());
            setAuthToken(teacher, response);
            setRefreshToken(teacher, response);
            return ResponseEntity.ok().body(teacher);

        } catch (RuntimeException e) {
            log.error("Не удалось аутентифицироваться пользователю с username: "
                    + dto.getEmail() + ". Error: " + e.getLocalizedMessage());
            clearCookie(response);
            return ResponseEntity.badRequest().body(new MessageResponse("Преподаватель с email: " + dto.getEmail())
                    + " не создан. Error: " + e.getLocalizedMessage());
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse httpServletResponse) {
        clearCookie(httpServletResponse);
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Вы вышли из аккаунта");
    }

    public void clearCookie(HttpServletResponse httpServletResponse) {
        Cookie authCookie = new Cookie(jwtTokenProvider.getAuthCookieName(), "-");
        authCookie.setPath(jwtTokenProvider.getCookiePath());

        Cookie refreshCookie = new Cookie(jwtTokenProvider.getRefreshCookieName(), "-");
        refreshCookie.setPath(jwtTokenProvider.getCookiePath());

        httpServletResponse.addCookie(authCookie);
        httpServletResponse.addCookie(refreshCookie);
    }

    public void setAuthToken(@NotNull Teacher teacher, HttpServletResponse httpServletResponse) {
        String token = jwtTokenProvider.createAuthToken(
                teacher.getUsername(),
                teacher.getRole().name());

        Cookie cookie = new Cookie(jwtTokenProvider.getAuthCookieName(), token);
        cookie.setPath(jwtTokenProvider.getCookiePath());
        cookie.setHttpOnly(true);
        cookie.setMaxAge(jwtTokenProvider.getAuthExpirationCookie());

        httpServletResponse.addCookie(cookie);
    }

    public void setRefreshToken(@NotNull Teacher teacher, HttpServletResponse httpServletResponse) {
        String token = jwtTokenProvider.createRefreshToken(
                teacher.getUsername(),
                teacher.getRole().name());

        Cookie cookie = new Cookie(jwtTokenProvider.getRefreshCookieName(), token);
        cookie.setPath(jwtTokenProvider.getCookiePath());
        cookie.setHttpOnly(true);
        cookie.setMaxAge(jwtTokenProvider.getRefreshExpirationCookie());

        httpServletResponse.addCookie(cookie);
    }
}
