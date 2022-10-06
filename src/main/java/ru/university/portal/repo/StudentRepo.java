package ru.university.portal.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.university.portal.model.Student;

import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student, Long> {

    Optional<Student> findById(Long id);

    Page<Student> findAll(Pageable pageable);

    boolean existsById(Long id);

    void deleteById(Long id);
}
