package ru.university.portal.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.university.portal.model.Group;

import java.util.Optional;

public interface GroupRepo extends JpaRepository<Group, Long> {

    Optional<Group> findById(Long id);

    Page<Group> findAll(Pageable pageable);

    boolean existsById(Long id);

    void deleteByName(String name);

    Optional<Group> findByName(String name);

    boolean existsByName(String name);
}
