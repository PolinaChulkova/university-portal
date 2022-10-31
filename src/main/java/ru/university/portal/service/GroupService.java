package ru.university.portal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.university.portal.dto.CreateGroupDTO;
import ru.university.portal.model.Group;
import ru.university.portal.model.Student;
import ru.university.portal.model.Subject;
import ru.university.portal.repo.GroupRepo;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupService {

    private final GroupRepo groupRepo;
    private final StudentService studentService;

    public Page<Group> findAllGroups(Pageable pageable) {
            return groupRepo.findAll(pageable);
    }

    public void createGroup(CreateGroupDTO dto) {
            Group group = new Group();
            group.setName(dto.getName());
            dto.getStudentsId().forEach(id ->
                    group.getStudents().add(studentService.findStudentById(id)));
            groupRepo.save(group);
    }

    public void addStudentToGroup(Long groupId, Long studentId) {
            Group group = findGroupByGroupId(groupId);
            group.getStudents().add(studentService.findStudentById(studentId));
            groupRepo.save(group);
    }

    public void deleteStudentFromGroup(Long groupId, Long studentId) {
            Group group = findGroupByGroupId(groupId);
            group.getStudents().remove(studentService.findStudentById(studentId));
            groupRepo.save(group);
    }

    public Group findGroupByGroupName(String groupName) {
        return groupRepo.findByName(groupName)
                .orElseThrow(() -> new RuntimeException("Группа с именем "
                        + groupName + " не найдена."));
    }

    public void deleteGroupByGroupName(String groupName) {
        if (groupRepo.existsByName(groupName)) groupRepo.deleteByName(groupName);
        else throw new RuntimeException("Нельзя совершить удаление! " +
                "Группа с именем " + groupName + " не существует.");
    }

    public void deleteGroupByGroupId(Long groupId) {
        if (groupRepo.existsById(groupId)) groupRepo.deleteById(groupId);
        else throw new RuntimeException("Нельзя совершить удаление! " +
                "Группа с id= " + groupId + " не существует.");
    }

    public Group findGroupByGroupId(Long groupId) {
        return groupRepo.findById(groupId).orElseThrow(() -> new RuntimeException("Группа с id= "
                + groupId + " не найдена."));
    }
}
