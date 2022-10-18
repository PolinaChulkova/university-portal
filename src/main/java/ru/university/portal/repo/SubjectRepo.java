package ru.university.portal.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.university.portal.model.Subject;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepo extends JpaRepository<Subject, Long> {

    @Query(value = "SELECT s FROM Subject s WHERE s.teacher = ?1 AND s.subjectName LIKE %?1%")
    Page<Subject> findTeacherSubjects(Long teacherId, String key, Pageable pageable);

    Page<Subject> findAll(Pageable pageable);

    Optional<Subject> findById(Long id);

    boolean existsById(Long id);

    void deleteById(Long id);

    Optional<Subject> findBySubjectName(String subjectName);

}
