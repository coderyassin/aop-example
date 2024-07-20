package org.yascode.aop_example.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Student {
    private Integer age;
    private String name;
}
