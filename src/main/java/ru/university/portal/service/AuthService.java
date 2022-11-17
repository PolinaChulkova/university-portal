package ru.university.portal.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.university.portal.config.security.jwt.JwtTokenProvider;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
@AllArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;

    public void clearCookie(HttpServletResponse httpServletResponse) {
        Cookie authCookie = new Cookie(jwtTokenProvider.getAuthCookieName(), "-");
        authCookie.setPath(jwtTokenProvider.getCookiePath());

        Cookie refreshCookie = new Cookie(jwtTokenProvider.getRefreshCookieName(), "-");
        refreshCookie.setPath(jwtTokenProvider.getCookiePath());

        httpServletResponse.addCookie(authCookie);
        httpServletResponse.addCookie(refreshCookie);
    }

    public void setAllTokens(UserDetails userDetails, String role,
                             HttpServletResponse httpServletResponse) {
        setAuthToken(userDetails, role, httpServletResponse);
        setRefreshToken(userDetails, role, httpServletResponse);
    }

    public void setAuthToken(UserDetails userDetails, String role,
                             HttpServletResponse httpServletResponse) {
        String token = jwtTokenProvider.createAuthToken(
                userDetails.getUsername(),
                role);

        Cookie cookie = new Cookie(jwtTokenProvider.getAuthCookieName(), token);
        cookie.setPath(jwtTokenProvider.getCookiePath());
        cookie.setHttpOnly(true);
        cookie.setMaxAge(jwtTokenProvider.getAuthExpirationCookie());

        httpServletResponse.addCookie(cookie);
    }

    public void setRefreshToken(UserDetails userDetails, String role,
                                HttpServletResponse httpServletResponse) {
        String token = jwtTokenProvider.createRefreshToken(
                userDetails.getUsername(),
                role);

        Cookie cookie = new Cookie(jwtTokenProvider.getRefreshCookieName(), token);
        cookie.setPath(jwtTokenProvider.getCookiePath());
        cookie.setHttpOnly(true);
        cookie.setMaxAge(jwtTokenProvider.getRefreshExpirationCookie());

        httpServletResponse.addCookie(cookie);
    }
}
