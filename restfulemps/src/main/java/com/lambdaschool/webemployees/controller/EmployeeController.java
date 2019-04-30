package com.lambdaschool.webemployees.controller;

import com.lambdaschool.webemployees.WebemployeesApplication;
import com.lambdaschool.webemployees.exception.ResourceNotFoundException;
import com.lambdaschool.webemployees.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@RestController
@RequestMapping("/data")
public class EmployeeController
{
    //localhost:2019/data/allemployees
    @GetMapping(value = "/allemployees",
                produces = {"application/json"})
    public ResponseEntity<?> getAllEmployees()
    {
        WebemployeesApplication.ourEmpList.empList.sort((e1, e2) -> e1.getName().compareToIgnoreCase(e2.getName()));
        return new ResponseEntity<>(WebemployeesApplication.ourEmpList.empList, HttpStatus.OK);
    }


    // localhost:2019/data/employee/2
    @GetMapping(value = "/employee/{id}",
                produces = {"application/json"})
    public ResponseEntity<?> getEmpDetail(
            @PathVariable
                    long id) throws ResourceNotFoundException
    {
        Employee rtnEmp;
        if (WebemployeesApplication.ourEmpList.findEmployee(e -> (e.getId() == id)) == null)
        {
            throw new ResourceNotFoundException("Employee with id " + id + " not found");
        } else
        {
            rtnEmp = WebemployeesApplication.ourEmpList.findEmployee(e -> (e.getId() == id));
        }
        return new ResponseEntity<>(rtnEmp, HttpStatus.OK);
    }

    // localhost:2019/data/employees/s
    @GetMapping(value = "/employees/{letter}",
                produces = {"application/json"})
    public ResponseEntity<?> getEmployees(
            @PathVariable
                    char letter)
    {
        ArrayList<Employee> rtnEmps = WebemployeesApplication.ourEmpList.
                findEmployees(e -> e.getFname().toUpperCase().charAt(0) == Character.toUpperCase(letter));

        if (rtnEmps.size() == 0)
        {
            throw new ResourceNotFoundException("No employees fname start with " + letter);
        }
        return new ResponseEntity<>(rtnEmps, HttpStatus.OK);
    }

    // localhost:2019/data/employeetable
    @GetMapping(value = "/employeetable")
    public ModelAndView displayEmployeeTable()
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("employees");
        mav.addObject("employeeList", WebemployeesApplication.ourEmpList.empList);

        return mav;
    }
}
