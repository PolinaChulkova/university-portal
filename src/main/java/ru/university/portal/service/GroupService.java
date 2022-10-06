package ru.university.portal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.university.portal.model.Group;
import ru.university.portal.repo.GroupRepo;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupService {

    private final GroupRepo groupRepo;

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
