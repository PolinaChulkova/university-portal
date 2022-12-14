package ru.university.portal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.university.portal.dto.StudentDTO;
import ru.university.portal.model.Student;
import ru.university.portal.repo.StudentRepo;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepo studentRepo;

    public void createStudent(StudentDTO dto) {
            Student student = new Student(dto);
            studentRepo.save(student);
    }

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
}
