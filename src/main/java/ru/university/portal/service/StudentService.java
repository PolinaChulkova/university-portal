package ru.university.portal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.university.portal.dto.LoginDTO;
import ru.university.portal.dto.StudentDTO;
import ru.university.portal.model.Student;
import ru.university.portal.repo.StudentRepo;

@Service
@RequiredArgsConstructor
public class StudentService implements UserDetailsService {

    private final StudentRepo studentRepo;
    private final PasswordEncoder passwordEncoder;
//    private final AuthenticationManager authenticationManager;

    public void registerStudent(StudentDTO dto) {
            Student student = new Student(dto);
            student.setPassword(passwordEncoder.encode(dto.getPassword()));
            studentRepo.save(student);
    }

//    public Student loginStudent(LoginDTO dto) {
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                dto.getEmail(),
//                dto.getPassword()));
//        return (Student) new CachingUserDetailsService(this)
//                .loadUserByUsername(dto.getEmail());
//    }

    public void updateStudent(Long studentId, StudentDTO dto) {
            Student student = findStudentById(studentId);
            student.setFullName(dto.getFullName());
            student.setEmail(dto.getEmail());
            student.setPassword(dto.getPassword());
            student.setPhoneNum(dto.getPhoneNum());

            studentRepo.save(student);
    }

    public Student findStudentByEmail(String studentEmail) {
        return studentRepo.findByEmail(studentEmail).orElseThrow(()
                -> new RuntimeException("Не удалось найти студента с email: " + studentEmail + "."));
    }

    public void deleteStudentById(Long studentId) {
        if (studentRepo.existsById(studentId)) studentRepo.deleteById(studentId);
        else throw new RuntimeException("Студента с Id=" + studentId + " не существует!");
    }

    public Student findStudentById(Long id) {
        return studentRepo.findById(id).orElseThrow(() -> new RuntimeException("Не удалось найти студента с Id="
                + id + "."));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findStudentByEmail(username);
    }
}
