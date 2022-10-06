package ru.university.portal.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.university.portal.model.Teacher;
import ru.university.portal.repo.TeacherRepo;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeacherService {

    private final TeacherRepo teacherRepo;

    public Teacher findTeacherById(Long teacherId) {
        return teacherRepo.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Преподаватель с id=" +  teacherId + "не найден."));
    }
}
