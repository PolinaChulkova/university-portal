package ru.university.portal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.university.portal.dto.CreateRatingDTO;
import ru.university.portal.dto.UpdateRatingDTO;
import ru.university.portal.model.Rating;
import ru.university.portal.model.Student;
import ru.university.portal.model.Task;
import ru.university.portal.repo.RatingRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RatingService {

    private final RatingRepo ratingRepo;
    private final TaskService taskService;
    private final SubjectService subjectService;
    private final StudentService studentService;

    public void createRating(CreateRatingDTO dto) {
        Task task = taskService.findTaskById(dto.getTaskId());
        Student student = studentService.findStudentById(dto.getStudentId());

        if (!task.getTeacher().getGroups().contains(student.getGroup()))
            throw new RuntimeException("Невозможно поставить оценку студенту " + student.getFullName()
                    + ", т.к. он закреплен за другим преподавателем");

        Rating rating = new Rating(
                dto.getMark(),
                dto.getComment(),
                task,
                subjectService.findSubjectById(dto.getSubjectId()),
                student
        );
        ratingRepo.save(rating);
    }

    public void updateRating(Long ratingId, Long teacherId, UpdateRatingDTO dto) {
        Rating rating = findRatingForTeacher(ratingId, teacherId);
        rating.setComment(dto.getComment());
        rating.setMark(dto.getMark());

        ratingRepo.save(rating);
    }

    public Rating findRatingForStudent(Long taskId, Long studentId) {
        return ratingRepo.findByTaskIdAndStudentId(taskId, studentId).orElseThrow(()
                -> new RuntimeException("Оценка по заданию с id=" + taskId + " не найдена."));
    }

    public List<Rating> findRatingsForTeacher(Long taskId, Long teacherId) {
        return ratingRepo.findByTaskIdAndTeacherId(taskId, teacherId);
    }

    public List<Rating> findRatingsForSubject(Long subjectId, Long studentId) {
        return ratingRepo.findBySubjectIdAndStudentId(subjectId, studentId);
    }

    public Rating findRatingForTeacher(Long ratingId, Long teacherId) {
        return ratingRepo.findByIdAndTeacherId(ratingId, teacherId).orElseThrow(()
                -> new RuntimeException("Оценка с id=" + ratingId + " не найдена."));
    }
}