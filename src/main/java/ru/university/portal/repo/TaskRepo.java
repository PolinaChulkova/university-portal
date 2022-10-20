package ru.university.portal.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.university.portal.dto.CreateTaskDTO;
import ru.university.portal.model.Task;

import java.util.Optional;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {

    Page<Task> findAll(Pageable pageable);

    Optional<Task> findById(Long id);

    boolean existsById(Long id);

    void deleteById(Long id);
}
