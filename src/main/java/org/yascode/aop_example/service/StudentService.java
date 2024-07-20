package org.yascode.aop_example.service;

import org.yascode.aop_example.entity.Student;

import java.time.LocalDateTime;

public interface StudentService {
    Student getStudentByName(String name);
    Student getStudentByName(Student student, LocalDateTime localDateTime) throws Exception;
}
