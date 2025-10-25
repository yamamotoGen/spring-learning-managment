package ru.aksh.learningmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aksh.learningmanagement.domain.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
