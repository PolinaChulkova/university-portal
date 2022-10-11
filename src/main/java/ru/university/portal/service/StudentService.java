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

    public void createStudent(Student student) {
        try {
            studentRepo.save(student);
        } catch (RuntimeException e) {
            log.error("Студент с email: " + student.getEmail() + " не создан. {}"
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

    public void deleteStudentById(Long id) {
        if (studentRepo.existsById(id)) studentRepo.deleteById(id);
        else throw new RuntimeException("Студента с Id=" + id + " не существует!");
    }

    public Student findStudentById(Long id) {
        return studentRepo.findById(id).orElseThrow(() -> new RuntimeException("Не удалось найти студента с Id="
                + id + "."));
    }
}
