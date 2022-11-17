package ru.university.portal.config.security.jwt;

import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ru.university.portal.model.Role;
import ru.university.portal.service.StudentService;
import ru.university.portal.service.TeacherService;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${auth.cookie.secret}")
    private String secretKey;

    @Getter
    @Value("${auth.cookie.auth}")
    private String authCookieName;

    @Getter
    @Value("${auth.cookie.refresh}")
    private String refreshCookieName;

    @Getter
    @Value("${auth.cookie.expiration-auth}")
    private Integer authExpirationCookie;

    @Getter
    @Value("${auth.cookie.expiration-refresh}")
    private Integer refreshExpirationCookie;

    @Getter
    @Value("${auth.cookie.path}")
    private String cookiePath;

    private final TeacherService teacherService;
    private final StudentService studentService;

    public JwtTokenProvider(TeacherService teacherService, StudentService studentService) {
        this.teacherService = teacherService;
        this.studentService = studentService;
    }

    @PostConstruct
    public void init() {
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createAuthToken(String username, String role) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", role);
        Date now = new Date();
        Date valid = new Date(now.getTime() + getAuthExpirationCookie());
        return Jwts.builder().setClaims(claims)
                .setIssuedAt(now).setExpiration(valid)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String createRefreshToken(String username, String role) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", role);
        Date now = new Date();
        Date valid = new Date(now.getTime() + getRefreshExpirationCookie());
        return Jwts.builder().setClaims(claims)
                .setIssuedAt(now).setExpiration(valid)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validate(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return claimsJws.getBody().getExpiration().after(new Date());
        } catch (ExpiredJwtException e) {
            log.error(e.getMessage());
        }
        return false;
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = null;
        switch (getAuthorities(token)) {
            case TEACHER:
            case ADMIN:
                userDetails = teacherService.loadUserByUsername(getUsername(token));
                break;
            case STUDENT:
                userDetails = studentService.loadUserByUsername(getUsername(token));
                break;
        }
        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
                userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public Role getAuthorities(String token) {
        return (Role) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("role");
    }

    public String resolveToken(HttpServletRequest httpServletRequest) {
        Cookie[] cookies = httpServletRequest.getCookies();
        String res = null;
        for(Cookie cookie : cookies) {
            if (cookie.getName().equals(getAuthCookieName())) {
                res = cookie.getValue();
            }
        }
        return res;
    }
}
