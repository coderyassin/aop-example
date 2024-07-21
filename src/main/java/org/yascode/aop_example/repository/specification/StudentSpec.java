package org.yascode.aop_example.repository.specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.yascode.aop_example.entity.Student;
import org.yascode.aop_example.model.StudentModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class StudentSpec {
    public static Specification<Student> studentQuery(Optional<StudentModel> studentModel) {
        return (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            studentModel.ifPresent(student -> {
                if (Objects.nonNull(student.getFirstname())) {
                    predicates.add(cb.equal(root.get("firstname"), student.getFirstname()));
                }
                if (Objects.nonNull(student.getLastname())) {
                    predicates.add(cb.equal(root.get("lastname"), student.getLastname()));
                }
                if (Objects.nonNull(student.getStudentCode())) {
                    predicates.add(cb.equal(root.get("studentCode"), student.getStudentCode()));
                }
            });

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
