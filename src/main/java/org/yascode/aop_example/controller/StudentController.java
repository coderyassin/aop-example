package org.yascode.aop_example.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yascode.aop_example.controller.api.StudentApi;
import org.yascode.aop_example.controller.request.RetrieveStudentRequest;
import org.yascode.aop_example.controller.response.StudentResponse;
import org.yascode.aop_example.entity.Student;
import org.yascode.aop_example.service.StudentService;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping(value = "/students")
public class StudentController implements StudentApi {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public ResponseEntity<?> retrieveStudent(String name) {
        return ResponseEntity.ok(studentService.getStudentByName(name));
    }

    @Override
    public ResponseEntity<?> retrieveStudent(RetrieveStudentRequest retrieveStudentRequest,
                                             HttpServletRequest httpServletRequest) {
        try {
            Student student = studentService.getStudentByName(retrieveStudentRequest.student(), LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(StudentResponse.builder()
                            .student(student)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}
