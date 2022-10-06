package ru.university.portal.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.university.portal.model.Subject;

import java.util.Optional;

public interface SubjectRepo extends JpaRepository<Subject, Long> {

    Page<Subject> findAll(Pageable pageable);

    Optional<Subject> findById(Long id);

    boolean existsById(Long id);

    void deleteById(Long id);

    Optional<Subject> findBySubjectName(String subjectName);

}
