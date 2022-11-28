package ru.university.portal.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ru.university.portal.model.Role;
import ru.university.portal.service.StudentService;
import ru.university.portal.service.TeacherService;

@Component
@AllArgsConstructor
public class AuthenticationProviderImpl implements AuthenticationProvider {

    @Getter
    private UserDetailsService userDetailsService;
    private final StudentService studentService;
    private final TeacherService teacherService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        Role role = Role.valueOf(String.valueOf(authentication.getAuthorities().stream().findFirst()));

        userDetailsService = getUserDetailsService(role);

        if (userDetailsService.loadUserByUsername(name).getPassword().equals(password)) {
            return new UsernamePasswordAuthenticationToken(
                    name, password, authentication.getAuthorities());

        } else throw new BadCredentialsException("Некорректный пароль и имя пользователя");
    }

    private UserDetailsService getUserDetailsService(Role role) {
        UserDetailsService userDetailsService= null;
        switch (role) {
            case STUDENT: userDetailsService = studentService;
            break;
            case TEACHER:
            case ADMIN:
                userDetailsService = teacherService;
            break;
        }
        return userDetailsService;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
