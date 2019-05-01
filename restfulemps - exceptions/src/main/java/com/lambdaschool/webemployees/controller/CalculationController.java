package com.lambdaschool.webemployees.controller;

import com.lambdaschool.webemployees.exception.ResourceNotFoundException;
import com.lambdaschool.webemployees.model.Employee;
import com.lambdaschool.webemployees.WebemployeesApplication;
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
    @GetMapping(value = "/salary/{id}/{raise}", produces = {"application/json"})
    public ResponseEntity<?> checkRaise(
            @PathVariable
                    long id,
            @PathVariable
                    double raise)
    {
        Employee tempEmp;
        if (WebemployeesApplication.ourEmpList.findEmployee(e -> (e.getId() == id)) == null)
        {
            throw new ResourceNotFoundException("Employee with id " + id + " not found");
        } else
        {
            tempEmp = WebemployeesApplication.ourEmpList.findEmployee(e -> (e.getId() == id));
        }

        tempEmp.setSalary(tempEmp.getSalary() * (1.0 + raise));
        return new ResponseEntity<>(tempEmp, HttpStatus.OK);
    }
}
