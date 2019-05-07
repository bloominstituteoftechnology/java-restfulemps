package com.lambdaschool.webemployees.controller;

import com.lambdaschool.webemployees.WebemployeesApplication;
import com.lambdaschool.webemployees.exception.ResourceNotFoundException;
import com.lambdaschool.webemployees.model.Employee;
import com.lambdaschool.webemployees.model.MessageDetail;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// with EnableWebMvc on, anything after a . is truncated so must add regex
// localhost:8080/calc/salary/1/0.5
@RequestMapping("/calc")
public class CalculationController
{
    @Autowired
    RabbitTemplate rt;

    @GetMapping(value = "/salary/{id}/{raise:.+}")
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
            tempEmp = new Employee(WebemployeesApplication.ourEmpList.findEmployee(e -> (e.getId() == id)));
        }
        tempEmp.setSalary(tempEmp.getSalary() * (1.0 + raise));

        MessageDetail message = new MessageDetail("Checking raise for " + id + " for " + raise, 1, true);
        rt.convertAndSend(WebemployeesApplication.QUEUE_NAME_LOW, message);
        return new ResponseEntity<>(tempEmp, HttpStatus.OK);
    }
}
