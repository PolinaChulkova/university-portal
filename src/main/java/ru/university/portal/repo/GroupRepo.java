package ru.university.portal.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.university.portal.model.Group;

import java.util.Optional;

@Repository
public interface GroupRepo extends JpaRepository<Group, Long> {

    @Query(value = "SELECT g FROM Group g JOIN g.subjects s JOIN s.teachers t " +
            "WHERE t.email = ?1 AND g.id = ?2")
    Group findByTeacherEmailAndGroupId(String teacherEmail, Long id);

    Optional<Group> findById(Long id);

    Page<Group> findAll(Pageable pageable);

    boolean existsById(Long id);

    void deleteById(Long id);

    void deleteByName(String name);

    Optional<Group> findByName(String name);

    boolean existsByName(String name);
}
