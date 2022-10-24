package ru.university.portal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.university.portal.dto.CreateRatingDTO;
import ru.university.portal.model.Rating;
import ru.university.portal.model.Student;
import ru.university.portal.model.Task;
import ru.university.portal.repo.RatingRepo;

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

        try {
            Rating rating = new Rating(
                    dto.getMark(),
                    dto.getComment(),
                    task,
                    subjectService.findSubjectByName(dto.getSubjectName()),
                    student
            );
            ratingRepo.save(rating);

        } catch (RuntimeException e) {
            log.error("Оценка студенту с id= " + dto.getStudentId() + " не поставлена. {}"
                    + e.getLocalizedMessage());
        }
    }

    public Rating findRatingByStudentIdAndSubjectId(Long studentId, Long subjectId) {
        return ratingRepo.findByStudentIdAndSubjectId(studentId, subjectId).orElseThrow(()
                -> new RuntimeException("Оценка по предмету с id=" + subjectId + " у студента с id ="
                + studentId + "не найдена."));
    }

    public Rating findRatingByTaskId(Long taskId) {
        return ratingRepo.findByTaskId(taskId).orElseThrow(()
                -> new RuntimeException("Оценка по заданию с id=" + taskId + "не найдена."));
    }
}
