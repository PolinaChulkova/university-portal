package ru.university.portal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.university.portal.model.Student;
import ru.university.portal.repo.StudentRepo;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {

    private final StudentRepo studentRepo;

    public Student findStudentById(Long id) {
        return studentRepo.findById(id).orElseThrow(() -> new RuntimeException("Не удалось найти студента с Id="
                + id + "."));
    }
}
