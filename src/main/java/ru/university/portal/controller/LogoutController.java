package ru.university.portal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.university.portal.dto.MessageResponse;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/logout")
public class LogoutController {

    @PostMapping
    public ResponseEntity<?> logout(HttpServletRequest request) {
        SecurityContextHolder.clearContext();
        request.getSession().invalidate();
        return ResponseEntity.ok().body(new MessageResponse("Вы вышли из системы"));
    }
}
