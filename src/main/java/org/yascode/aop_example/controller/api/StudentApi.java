package org.yascode.aop_example.controller.api;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.yascode.aop_example.controller.request.RetrieveStudentRequest;

public interface StudentApi {
    @GetMapping(value = "/{name}")
    ResponseEntity<?> retrieveStudent(@PathVariable("name") String name);

    @PostMapping(value = "/retrieveStudent")
    ResponseEntity<?> retrieveStudent(@RequestBody RetrieveStudentRequest retrieveStudentRequest,
                                      HttpServletRequest httpServletRequest);
}
