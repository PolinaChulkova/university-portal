package ru.university.portal.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.university.portal.model.Teacher;

import java.util.Optional;

public interface TeacherRepo extends JpaRepository<Teacher, Long> {

    Optional<Teacher> findById(Long id);

    Page<Teacher> findAll(Pageable pageable);

    boolean existsById(Long id);

    void deleteById(Long id);
}