package org.yascode.aop_example.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.yascode.aop_example.entity.Student;
import org.yascode.aop_example.service.StudentService;

import java.time.LocalDateTime;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {
    @Override
    public Student getStudentByName(String name) {
        return name.equals("Yassin") ? Student.builder()
                .name("Yassin")
                .age(27)
                .build() : null;
    }

    @Override
    public Student getStudentByName(Student student, LocalDateTime localDateTime) throws Exception {
        if(student.getName().equals("Yassin") && student.getAge() == 27 ) {
            return Student.builder()
                    .name("Yassin")
                    .age(27)
                    .build();
        }
        throw new Exception("Student not exist");
    }
}
