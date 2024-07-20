package org.yascode.aop_example.controller.response;

import lombok.Builder;
import org.yascode.aop_example.entity.Student;

@Builder
public record StudentResponse(Student student) {
}
