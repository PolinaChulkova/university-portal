package ru.university.portal.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.university.portal.dto.CreateGroupDTO;
import ru.university.portal.dto.MessageResponse;
import ru.university.portal.service.GroupService;

@RestController
@RequestMapping("/group")
@AllArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @GetMapping("/{groupId}")
    public ResponseEntity<?> getStudentsByGroup(@PathVariable Long groupId) {
        return ResponseEntity.ok().body(groupService.findGroupByGroupId(groupId).getStudents());
    }

    //    для админа
    @PostMapping("/create")
    public ResponseEntity<?> createGroup(@RequestBody CreateGroupDTO dto) {
        groupService.createGroup(dto);
        return ResponseEntity.ok().body(new MessageResponse("Созана группа " + dto.getName()));
    }

    //    для админа
    @DeleteMapping("/delete/{groupId}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long groupId) {
        groupService.deleteGroupByGroupId(groupId);
        return ResponseEntity.ok().body(new MessageResponse("Группа с " + groupId + " удалена."));
    }

    //    для админа
    @PostMapping("/add-student/{groupId}/{studentId}")
    public ResponseEntity<?> addStudentToGroup(@PathVariable Long groupId,
                                               @PathVariable Long studentId) {
        groupService.addStudentToGroup(groupId, studentId);
        return ResponseEntity.ok().body(new MessageResponse("В группу с id=" + groupId
                + " добавлен студент с id=" + studentId));
    }

    //    для админа
    @DeleteMapping("/delete-student/{groupId}/{studentId}")
    public ResponseEntity<?> deleteStudentFromGroup(@PathVariable Long groupId,
                                               @PathVariable Long studentId) {
        groupService.deleteStudentFromGroup(groupId, studentId);
        return ResponseEntity.ok().body(new MessageResponse("Из группы с id=" + groupId
                + " удалён студент с id=" + studentId));
    }
}
