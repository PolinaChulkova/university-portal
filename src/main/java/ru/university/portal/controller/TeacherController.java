package ru.university.portal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.university.portal.dto.CreateRatingDTO;
import ru.university.portal.dto.CreateTaskDTO;
import ru.university.portal.dto.MessageResponse;
import ru.university.portal.service.RatingService;
import ru.university.portal.service.SubjectService;
import ru.university.portal.service.TaskService;
import ru.university.portal.service.TeacherService;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final TaskService taskService;
    private final RatingService ratingService;
    private final TeacherService teacherService;
    private final SubjectService subjectService;

    @PostMapping("/subjects/{teacherId}")
    public ResponseEntity<?> findTeacherSubjects(@PathVariable Long teacherId,
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

    @PostMapping("group/{teacherId}/{subjectId}")
    public ResponseEntity<?> findStudentsBySubject(@PathVariable Long teacherId,
                                                   @PathVariable Long subjectId) {
        subjectService.deleteSubjectById(subjectId);
        return ResponseEntity.ok().body(teacherService.findTeacherGroups(teacherId));
    }

    @PostMapping("/create-task")
    public ResponseEntity<?> createTask(@RequestBody CreateTaskDTO dto) {
        taskService.createTask(dto);
        return ResponseEntity.ok().body(new MessageResponse(dto.getTeacher().getFullName() +
                ", вы создали задание " + dto.getName()));
    }

    @GetMapping("/task-answers")
    public ResponseEntity<?> getTaskAnswers(Long taskId) {
        return ResponseEntity.ok().body(taskService.getTaskAnswers(taskId));
    }

    @PostMapping("/create-rating")
    public ResponseEntity<?> createRating(@RequestBody CreateRatingDTO dto) {
        ratingService.createRating(dto);
        return ResponseEntity.ok().body(new MessageResponse("Выставлена оценка"));
    }
}
