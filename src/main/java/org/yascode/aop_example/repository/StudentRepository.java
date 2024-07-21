package org.yascode.aop_example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.yascode.aop_example.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {
    Optional<Student> findStudentById(int id);

    @Query(value = "select s from Student s where (s.firstname like %:name% or s.lastname like %:name%)")
    List<Student> findStudentByName(String name);

    Optional<Student> findStudentByStudentCode(Long studentCode);

    @Query("SELECT MAX(s.studentCode) FROM Student s")
    Long findMaxStudentCode();
}
