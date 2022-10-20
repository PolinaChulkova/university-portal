package ru.university.portal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.university.portal.dto.CreateRatingDTO;
import ru.university.portal.dto.CreateTaskDTO;
import ru.university.portal.dto.MessageResponse;
import ru.university.portal.model.Teacher;
import ru.university.portal.service.RatingService;
import ru.university.portal.service.SubjectService;
import ru.university.portal.service.TaskService;
import ru.university.portal.service.TeacherService;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final RatingService ratingService;
    private final TeacherService teacherService;
    private final SubjectService subjectService;

    @PostMapping("/subjects/{teacherId}/{page}")
    public ResponseEntity<?> findTeacherSubject(@PathVariable Long teacherId,
                                                 @PathVariable int page,
                                                 @RequestParam("key") String key) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseEntity.ok()
                .body(subjectService.findTeacherSubjects(teacherId, key, pageable).getContent());
    }

//    @PostMapping("/subjects/{teacherId}")
//    public ResponseEntity<?> findTeacherSubjects(@PathVariable Long teacherId) {
//        return ResponseEntity.ok().body(teacherService.findTeacherSubjects(teacherId));
//    }

    @PostMapping("group/{subjectId}")
    public ResponseEntity<?> getStudentsByGroup(@PathVariable Long subjectId) {
        return ResponseEntity.ok().body(subjectService.findSubjectById(subjectId).getGroup().getStudents());
    }

    @PostMapping("/create-rating")
    public ResponseEntity<?> createRating(@RequestBody CreateRatingDTO dto) {
        ratingService.createRating(dto);
        return ResponseEntity.ok().body(new MessageResponse("Выставлена оценка"));
    }
}
