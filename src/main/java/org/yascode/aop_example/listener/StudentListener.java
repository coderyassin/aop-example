package org.yascode.aop_example.listener;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.yascode.aop_example.entity.Student;

import java.time.LocalDateTime;
import java.util.Objects;

public class StudentListener {

    @PrePersist
    void onPrePersist(Student student) {
        if (Objects.nonNull(student) && Objects.isNull(student.getCreationDate())) {
            student.setCreationDate(LocalDateTime.now());
        }
    }

    @PreUpdate
    void onPreUpdate(Student student) {
        if(Objects.nonNull(student) && Objects.isNull(student.getLastModifiedDate())) {
            student.setLastModifiedDate(LocalDateTime.now());
        }
    }

}
