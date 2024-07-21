package org.yascode.aop_example.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.yascode.aop_example.entity.Student;
import org.yascode.aop_example.exception.ResourceNotFoundException;
import org.yascode.aop_example.model.StudentModel;
import org.yascode.aop_example.repository.StudentRepository;
import org.yascode.aop_example.repository.specification.StudentSpec;
import org.yascode.aop_example.service.StudentService;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {
    public final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student getStudentById(int id) {
        Optional<Student> studentOptional = studentRepository.findStudentById(id);
        if (studentOptional.isPresent()) {
            return studentOptional.get();
        }
        throw new ResourceNotFoundException("Student with id " + id + " not found");
    }

    @Override
    public List<Student> getStudentByName(String name) throws ResourceNotFoundException {
        List<Student> students = studentRepository.findStudentByName(name);
        if (students.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Student with name %s not found", name));
        }
        return students;
    }

    @Override
    public List<Student> getStudentByName(StudentModel student) throws ResourceNotFoundException {
        List<Student> students = studentRepository.findAll(StudentSpec.studentQuery(Optional.ofNullable(student)));
        if (students.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Student with criteria %s not found", student));
        }
        return students;
    }

    @Override
    public Student retrieveStudentCode(Long studentCode) {
        Optional<Student> studentOptional = studentRepository.findStudentByStudentCode(studentCode);
        if (studentOptional.isEmpty()) {
            throw new ResourceNotFoundException("Student with code " + studentCode + " not found");
        }
        return studentOptional.get();
    }
}
