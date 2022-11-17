package ru.university.portal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.university.portal.dto.LoginDTO;
import ru.university.portal.dto.TeacherDTO;
import ru.university.portal.model.Teacher;
import ru.university.portal.repo.TeacherRepo;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeacherService implements UserDetailsService {

    private final TeacherRepo teacherRepo;
    private final PasswordEncoder passwordEncoder;

    public Teacher findTeacherById(Long teacherId) {
        return teacherRepo.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Преподаватель с id=" + teacherId + "не найден."));
    }

    public Teacher findTeacherByEmail(String email) {
        return teacherRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Преподаватель с email: " + email + "не найден."));
    }

    public Teacher registerTeacher(TeacherDTO dto) {
            Teacher teacher = new Teacher(dto);
            teacher.setPassword(passwordEncoder.encode(dto.getPassword()));
            teacherRepo.save(teacher);
            return teacher;
    }

    public void updateTeacher(Long teacherId, TeacherDTO dto) {
        try {
            Teacher teacher = findTeacherById(teacherId);
            teacher.setFullName(dto.getFullName());
            teacher.setEmail(dto.getEmail());
            teacher.setPassword(passwordEncoder.encode(dto.getPassword()));
            teacher.setPhoneNum(dto.getPhoneNum());
            teacher.setAcademicDegree(dto.getAcademicDegree());

            teacherRepo.save(teacher);

        } catch (RuntimeException e) {
            log.error("Преподватель с Id= " + teacherId + " не обновлён. {}"
                    + e.getLocalizedMessage());
        }
    }

    public void deleteTeacherById(Long teacherId) {
        if (teacherRepo.existsById(teacherId)) teacherRepo.deleteById(teacherId);
        else throw new RuntimeException("Преподавателя с Id=" + teacherId + " не существует!");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findTeacherByEmail(username);
    }
}
