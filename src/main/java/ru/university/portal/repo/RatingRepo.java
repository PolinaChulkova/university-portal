package ru.university.portal.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.university.portal.model.Rating;

import java.util.Optional;

public interface RatingRepo extends JpaRepository<Rating, Long> {

    Optional<Rating> findByTaskId(Long taskId);

    Optional<Rating> findByStudentIdAndSubjectId(Long studentId, Long subjectId);

    Optional<Rating> findById(Long id);

    Page<Rating> findAll(Pageable pageable);

    boolean existsById(Long id);

    void deleteById(Long id);
}
