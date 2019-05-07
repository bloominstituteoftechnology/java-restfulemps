package com.lambdaschool.webemployees;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// localhost:8080/calc/salary/1/0.5
@RestController
@RequestMapping("/calc")
public class CalculationController
{
    @GetMapping(value = "/salary/{id}/{raise}")
public ResponseEntity<?> checkRaise(
        @PathVariable
                long id,
        @PathVariable
                double raise)
{
    System.out.println(raise);
    Employee tempEmp = new Employee(WebemployeesApplication.ourEmpList.findEmployee(e -> (e.getId() == id)));
    tempEmp.setSalary(tempEmp.getSalary() * (1.0 + raise));
    return new ResponseEntity<>(tempEmp, HttpStatus.OK);
}
}
