package org.yascode.aop_example.service;

import org.yascode.aop_example.entity.Student;
import org.yascode.aop_example.exception.ResourceNotFoundException;

import java.time.LocalDateTime;

public interface StudentService {
    Student getStudentByName(String name) throws ResourceNotFoundException;
    Student getStudentByName(Student student, LocalDateTime localDateTime) throws ResourceNotFoundException;
    Integer getAge(String name) throws ResourceNotFoundException;
}
