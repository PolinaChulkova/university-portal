package ru.university.portal.controller;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @GetMapping("/student/all/{groupId}/{page}")
    public ResponseEntity<?> searchAllGroupSubjects(@PathVariable Long groupId,
                                                    @PathVariable int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseEntity.ok()
                .body(subjectService.findAllGroupSubject(groupId, pageable).getContent());
    }

    @GetMapping("/teacher/search/{teacherId}/{page}")
    public ResponseEntity<?> searchTeacherSubject(@PathVariable Long teacherId,
                                                  @PathVariable int page,
                                                  @RequestParam("key") String key) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseEntity.ok()
                .body(subjectService.findTeacherSubjects(teacherId, key, pageable).getContent());
    }

    @GetMapping("/teacher/all/{teacherId}/{page}")
    public ResponseEntity<?> searchAllTeacherSubjects(@PathVariable Long teacherId,
                                                      @PathVariable int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseEntity.ok()
                .body(subjectService.findAllTeacherSubjects(teacherId, pageable).getContent());
    }

    //    для админа
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteSubject(Long subjectId) {
        subjectService.deleteSubjectById(subjectId);
        return ResponseEntity.ok().body(new MessageResponse("Предмет с id="
                + subjectId + " удалён"));
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

    //    для админа
    @DeleteMapping("/delete-teacher/{subjectName}/{teacherEmail}")
    public ResponseEntity<?> detachTeacherFromSubject(@PathVariable String subjectName,
                                                 @PathVariable String teacherEmail) {
        subjectService.detachTeacherFromSubject(teacherEmail, subjectName);
        return ResponseEntity.ok().body(new MessageResponse("Преподаватель с email: " + teacherEmail
                + " откреплён от предмета \"" + subjectName + "\""));
    }

    //    для админа
    @DeleteMapping("/delete-group/{subjectName}/{groupName}")
    public ResponseEntity<?> detachGroupFromSubject(@PathVariable String subjectName,
                                                      @PathVariable String groupName) {
        subjectService.detachGroupFromSubject(groupName, subjectName);
        return ResponseEntity.ok().body(new MessageResponse("Группа " + groupName
                + " откреплёна от предмета \"" + subjectName + "\""));
    }
}
