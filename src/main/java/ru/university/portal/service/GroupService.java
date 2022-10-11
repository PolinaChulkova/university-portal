package ru.university.portal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.university.portal.dto.CreateGroupDTO;
import ru.university.portal.model.Group;
import ru.university.portal.model.Student;
import ru.university.portal.repo.GroupRepo;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupService {

    private final GroupRepo groupRepo;
    private final StudentService studentService;

    public void createGroup(CreateGroupDTO dto) {
        try {
            Group group = new Group();
            group.setName(dto.getName());
            List<Student> students = new ArrayList<>();
            dto.getStudentsId().forEach(id -> students.add(studentService.findStudentById(id)));
            group.setStudents(students);
            groupRepo.save(group);

        } catch (RuntimeException e) {
            log.error("Группа с названием  " + dto.getName() + " не создана. {}"
                    + e.getLocalizedMessage());
        }
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
}
