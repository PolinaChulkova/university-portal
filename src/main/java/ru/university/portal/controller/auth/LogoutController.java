package ru.university.portal.controller.auth;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.token.TokenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.university.portal.service.AuthService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/logout")
@AllArgsConstructor
public class LogoutController {

    private final AuthService authService;

    public ResponseEntity<?> logout(HttpServletResponse httpServletResponse) {
        authService.clearCookie(httpServletResponse);
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Вы вышли из аккаунта");
    }
}
