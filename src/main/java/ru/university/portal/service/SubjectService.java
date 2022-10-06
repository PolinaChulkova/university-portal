package ru.university.portal.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.university.portal.dto.SubjectDto;
import ru.university.portal.model.Subject;
import ru.university.portal.repo.SubjectRepo;

@Service
@AllArgsConstructor
@Slf4j
public class SubjectService {

    private final SubjectRepo subjectRepo;
    private final TeacherService teacherService;

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
}
