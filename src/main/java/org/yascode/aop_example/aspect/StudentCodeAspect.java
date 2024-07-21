package org.yascode.aop_example.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.yascode.aop_example.entity.Student;
import org.yascode.aop_example.repository.StudentRepository;

import java.util.Objects;

@Aspect
@Component
public class StudentCodeAspect {
    private final StudentRepository studentRepository;

    public StudentCodeAspect(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Before("execution(* org.yascode.aop_example.repository.StudentRepository.save(..)) && args(student)")
    public void beforeSave(Student student) {
        if (Objects.nonNull(student) && Objects.isNull(student.getStudentCode())) {
            student.setStudentCode(getNextStudentCode());
        }
    }

    private Long getNextStudentCode() {
        Long maxStudentCode = studentRepository.findMaxStudentCode();
        return (maxStudentCode != null ? maxStudentCode : 0L) + 1;
    }
}
