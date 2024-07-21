package org.yascode.aop_example.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class StudentModel {
    private String firstname;
        private String lastname;
    private Long studentCode;
}
