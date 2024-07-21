package org.yascode.aop_example.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yascode.aop_example.controller.api.StudentApi;
import org.yascode.aop_example.controller.request.RetrieveStudentRequest;
import org.yascode.aop_example.exception.ResourceNotFoundException;
import org.yascode.aop_example.service.StudentService;

@Slf4j
@RestController
@RequestMapping(value = "/students")
public class StudentController implements StudentApi {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public ResponseEntity<?> retrieveStudent(String name,
                                             HttpServletRequest httpServletRequest) throws ResourceNotFoundException {
        return ResponseEntity.ok(studentService.getStudentByName(name));
    }

    @Override
    public ResponseEntity<?> retrieveStudent(RetrieveStudentRequest retrieveStudentRequest,
                                             HttpServletRequest httpServletRequest) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(studentService.getStudentByName(retrieveStudentRequest.student()));
    }

    @Override
    public ResponseEntity<?> retrieveStudentCode(Long studentCode, HttpServletRequest httpServletRequest) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(studentService.retrieveStudentCode(studentCode));
    }

    @Override
    public ResponseEntity<?> getStudent(@PathVariable int id, HttpServletRequest httpServletRequest) throws ResourceNotFoundException {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }
}
