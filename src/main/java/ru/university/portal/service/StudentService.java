package ru.university.portal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.university.portal.dto.StudentDTO;
import ru.university.portal.model.Student;
import ru.university.portal.repo.StudentRepo;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {

    private final StudentRepo studentRepo;

    public void createStudent(StudentDTO dto) {
        try {
            Student student = new Student(dto);
            studentRepo.save(student);

        } catch (RuntimeException e) {
            log.error("Студент с email: " + dto.getEmail() + " не создан. {}"
                    + e.getLocalizedMessage());
        }
    }

    public void updateStudent(Long studentId, StudentDTO dto) {
        try {
            Student student = findStudentById(studentId);
            student.setFullName(dto.getFullName());
            student.setEmail(dto.getEmail());
            student.setPassword(dto.getPassword());
            student.setPhoneNum(dto.getPhoneNum());

            studentRepo.save(student);

        } catch (RuntimeException e) {
            log.error("Студент с Id= " + studentId + " не обновлён. {}"
                    + e.getLocalizedMessage());
        }
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
}
