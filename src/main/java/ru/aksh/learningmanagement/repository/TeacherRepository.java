package ru.aksh.learningmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.aksh.learningmanagement.domain.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
