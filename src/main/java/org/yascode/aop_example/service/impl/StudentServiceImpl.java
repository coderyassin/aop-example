package org.yascode.aop_example.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.yascode.aop_example.entity.Student;
import org.yascode.aop_example.exception.ResourceNotFoundException;
import org.yascode.aop_example.service.StudentService;

import java.time.LocalDateTime;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {
    @Override
    public Student getStudentByName(String name) throws ResourceNotFoundException {
        if(name.equals("Yassin")) {
            return Student.builder()
                    .name("Yassin")
                    .age(27)
                    .build();
        }
        throw new ResourceNotFoundException(String.format("Student with name %s not found", name));
    }

    @Override
    public Student getStudentByName(Student student, LocalDateTime localDateTime) throws ResourceNotFoundException {
        if(student.getName().equals("Yassin") && student.getAge() == 27 ) {
            return Student.builder()
                    .name("Yassin")
                    .age(27)
                    .build();
        }
        throw new ResourceNotFoundException(String.format("Student %s not found", student));
    }

    @Override
    public Integer getAge(String name) throws ResourceNotFoundException {
        if(name.equals("Yassin")) {
            return 27;
        }
        throw new ResourceNotFoundException(String.format("Student with name %s not found", name));
    }
}
