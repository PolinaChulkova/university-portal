package ru.university.portal.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.university.portal.dto.CreateGroupDTO;
import ru.university.portal.dto.MessageResponse;
import ru.university.portal.service.GroupService;

@RestController
@RequestMapping("/group")
@AllArgsConstructor
@Slf4j
public class GroupController {

    private final GroupService groupService;

    @GetMapping("/{groupId}")
    public ResponseEntity<?> getGroup(@PathVariable Long groupId) {
        return ResponseEntity.ok().body(groupService.findGroupByGroupId(groupId));
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/students/{groupId}")
    public ResponseEntity<?> getStudentsByGroup(@PathVariable Long groupId) {
        return ResponseEntity.ok().body(groupService.findGroupByGroupId(groupId).getStudents());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> createGroup(@RequestBody CreateGroupDTO dto) {
        try {
            groupService.createGroup(dto);
            return ResponseEntity.ok().body(new MessageResponse("Созана группа " + dto.getName()));

        } catch (RuntimeException e) {
            log.error("Группа с названием  " + dto.getName() + " не создана. Error: "
                    + e.getLocalizedMessage());

            return ResponseEntity.badRequest().body(new MessageResponse("Группа не создана. Error: "
                    + e.getLocalizedMessage()));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{groupId}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long groupId) {
        groupService.deleteGroupByGroupId(groupId);
        return ResponseEntity.ok().body(new MessageResponse("Группа с " + groupId + " удалена."));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-student/{groupId}/{studentId}")
    public ResponseEntity<?> addStudentToGroup(@PathVariable Long groupId,
                                               @PathVariable Long studentId) {
        try {
            groupService.addStudentToGroup(groupId, studentId);
            return ResponseEntity.ok().body(new MessageResponse("В группу с id=" + groupId
                    + " добавлен студент с id=" + studentId));

        } catch (RuntimeException e) {
            log.error("Студент с id= " + studentId + " не добавлен в группу с id=" + groupId + ". {}"
                    + e.getLocalizedMessage());

            return ResponseEntity.badRequest().body(new MessageResponse("Студент не добавлен в группу. " +
                    "Error: " + e.getLocalizedMessage()));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete-student/{groupId}/{studentId}")
    public ResponseEntity<?> deleteStudentFromGroup(@PathVariable Long groupId,
                                               @PathVariable Long studentId) {
        groupService.deleteStudentFromGroup(groupId, studentId);
        return ResponseEntity.ok().body(new MessageResponse("Из группы с id=" + groupId
                + " удалён студент с id=" + studentId));
    }
}
