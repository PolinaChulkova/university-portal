package ru.university.portal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.university.portal.dto.MessageResponse;
import ru.university.portal.dto.SubjectDto;
import ru.university.portal.service.SubjectService;

@RestController
@RequestMapping("/subject")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @GetMapping("/all/{teacherId}/{page}")
    public ResponseEntity<?> findTeacherSubjects(@PathVariable Long teacherId,
                                                 @PathVariable int page,
                                                 @RequestParam("key") String key) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseEntity.ok()
                .body(subjectService.findTeacherSubjects(teacherId, key, pageable).getContent());
    }

    //    для админа
    @PostMapping("/create")
    public ResponseEntity<?> createSubject(@RequestBody SubjectDto dto) {
        subjectService.createSubject(dto);
        return ResponseEntity.ok().body(new MessageResponse("Создан предмет: \""
                + dto.getSubjectName() + "\""));
    }

    //    для админа
    @PostMapping("/add-teacher/{subjectName}/{teacherEmail}")
    public ResponseEntity<?> addTeacherToSubject(@PathVariable String subjectName,
                                                 @PathVariable String teacherEmail) {
        subjectService.addTeacherToSubject(teacherEmail, subjectName);
        return ResponseEntity.ok().body(new MessageResponse("Преподаватель с email: " + teacherEmail
                + " добавлен к предмету \"" + subjectName + "\""));
    }

    //    для админа
    @PostMapping("/add-group/{subjectName}/{groupName}")
    public ResponseEntity<?> addGroupToSubject(@PathVariable String subjectName,
                                                 @PathVariable String groupName) {
        subjectService.addGroupToSubject(groupName, subjectName);
        return ResponseEntity.ok().body(new MessageResponse("К предмету \"" + subjectName + "\" " +
                "добавлена группа " + groupName));
    }
}
