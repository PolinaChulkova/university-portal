package ru.university.portal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.university.portal.dto.SubjectDto;
import ru.university.portal.model.Group;
import ru.university.portal.model.Subject;
import ru.university.portal.repo.SubjectRepo;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubjectService {

    private final SubjectRepo subjectRepo;
    private final TeacherService teacherService;
    private final GroupService groupService;

    public void createSubject(SubjectDto dto) {
        try {
            Subject subject = new Subject();
            subject.setSubjectName(dto.getSubjectName());
            subject.setTeacher(teacherService.findTeacherById(dto.getTeacherId()));
            subjectRepo.save(subject);
        } catch (RuntimeException e) {
            log.error("Предмет с названием " + dto.getSubjectName() + " не создан. {}"
                    + e.getLocalizedMessage());
        }
    }

    public void addGroupToSubject(String groupName, String subjectName) {
        try {
            Subject subject = findSubjectByName(subjectName);
            subject.getGroups().add(groupService.findGroupByGroupName(groupName));
            subjectRepo.save(subject);
        } catch (RuntimeException e) {
            log.error("Не удалось добавить группу " +  groupName
                    + " к предмету " + subjectName + ". {}"
                    + e.getLocalizedMessage());
        }
    }

    public Subject findSubjectByName(String name) {
        return subjectRepo.findBySubjectName(name)
                .orElseThrow(() -> new RuntimeException("Не удалось найти пользователя с именем "
                        + name + "."));
    }

    public void deleteSubjectById(Long subjectId) {
      if (subjectRepo.existsById(subjectId)) subjectRepo.deleteById(subjectId);
      else throw new RuntimeException("Нельзя совершить удаление! " +
              "Предмет с id=" + subjectId + " не существует.");
    }

}
