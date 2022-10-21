package ru.university.portal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.university.portal.dto.SubjectDto;
import ru.university.portal.model.Subject;
import ru.university.portal.repo.SubjectRepo;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubjectService {

    private final SubjectRepo subjectRepo;
    private final TeacherService teacherService;
    private final GroupService groupService;

    public Page<Subject> findTeacherSubjects(Long teacherId, String key, Pageable pageable) {
            return subjectRepo.findTeacherSubjects(teacherId, key, pageable);
    }

    public void createSubject(SubjectDto dto) {
        try {
            Subject subject = new Subject();
            subject.setSubjectName(dto.getSubjectName());
            subject.getTeachers().add(teacherService.findTeacherById(dto.getTeacherId()));

            subjectRepo.save(subject);

        } catch (RuntimeException e) {
            log.error("Предмет с названием " + dto.getSubjectName() + " не создан. {}"
                    + e.getLocalizedMessage());
        }
    }

    public void addGroupToSubject(String groupName, String subjectName) {
        try {
            Subject subject = findSubjectByName(subjectName);
            subject.setGroup(groupService.findGroupByGroupName(groupName));

            subjectRepo.save(subject);

        } catch (RuntimeException e) {
            log.error("Не удалось добавить группу " +  groupName
                    + " к предмету " + subjectName + ". {}"
                    + e.getLocalizedMessage());
        }
    }

    public void addTeacherToSubject(String email, String subjectName) {
        try {
            Subject subject = findSubjectByName(subjectName);
            subject.getTeachers().add(teacherService.findTeacherByEmail(email));

            subjectRepo.save(subject);

        } catch (RuntimeException e) {
            log.error("Не удалось добавить преподавателя с email: " +  email
                    + " к предмету " + subjectName + ". {}"
                    + e.getLocalizedMessage());
        }
    }

    public Subject findSubjectByName(String name) {
        return subjectRepo.findBySubjectName(name)
                .orElseThrow(() -> new RuntimeException("Не удалось найти предмет с именем "
                        + name + "."));
    }

    public void deleteSubjectById(Long subjectId) {
      if (subjectRepo.existsById(subjectId)) subjectRepo.deleteById(subjectId);
      else throw new RuntimeException("Нельзя совершить удаление! " +
              "Предмет с id=" + subjectId + " не существует.");
    }

    public Subject findSubjectById(Long subjectId) {
        return subjectRepo.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Предмет с id=" + subjectId + "не найден."));
    }
}
