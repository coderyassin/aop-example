package org.yascode.aop_example.service;

import org.yascode.aop_example.entity.Student;
import org.yascode.aop_example.exception.ResourceNotFoundException;
import org.yascode.aop_example.model.StudentModel;

import java.util.List;

public interface StudentService {
    Student getStudentById(int id);

    List<Student> getStudentByName(String name) throws ResourceNotFoundException;

    List<Student> getStudentByName(StudentModel student) throws ResourceNotFoundException;

    Student retrieveStudentCode(Long studentCode);
}
