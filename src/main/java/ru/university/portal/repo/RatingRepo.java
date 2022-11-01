package ru.university.portal.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.university.portal.model.Rating;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepo extends JpaRepository<Rating, Long> {

    @Query(value = "SELECT r FROM Rating r JOIN r.subject.teachers t WHERE r.task.id = ?1 AND t.id = ?2")
    List<Rating> findByTaskIdAndTeacherId(Long taskId, Long teacherId);

    @Query(value = "SELECT r FROM Rating r JOIN r.subject.teachers t WHERE r.id = ?1 AND t.id = ?2")
    Optional<Rating> findByIdAndTeacherId(Long id, Long teacherId);

    List<Rating> findBySubjectIdAndStudentId(Long subjectId, Long studentId);

    Optional<Rating> findByTaskIdAndStudentId(Long taskId, Long studentId);

    Page<Rating> findAll(Pageable pageable);

    boolean existsById(Long id);

    void deleteById(Long id);
}
