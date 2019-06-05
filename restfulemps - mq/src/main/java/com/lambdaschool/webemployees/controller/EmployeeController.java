package com.lambdaschool.webemployees.controller;

import com.lambdaschool.webemployees.WebemployeesApplication;
import com.lambdaschool.webemployees.exception.ResourceNotFoundException;
import com.lambdaschool.webemployees.model.Employee;
import com.lambdaschool.webemployees.model.MessageDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@RestController
@RequestMapping("/data")
public class EmployeeController
{
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    RabbitTemplate rt;

    //localhost:2019/data/allemployees
    @GetMapping(value = "/allemployees",
                produces = {"application/json"})
    public ResponseEntity<?> getAllEmployees(HttpServletRequest request)
    {
        logger.info(request.getRequestURI() + " accessed");

        MessageDetail message = new MessageDetail(request.getRequestURI()  + " accessed", 7, false);
        rt.convertAndSend(WebemployeesApplication.QUEUE_NAME_LOW, message);

        WebemployeesApplication.ourEmpList.empList.sort((e1, e2) -> e1.getName().compareToIgnoreCase(e2.getName()));
        return new ResponseEntity<>(WebemployeesApplication.ourEmpList.empList, HttpStatus.OK);
    }


    // localhost:2019/data/employee/2
    @GetMapping(value = "/employee/{id}",
                produces = {"application/json"})
    public ResponseEntity<?> getEmpDetail(HttpServletRequest request,
                                          @PathVariable
                                                  long id) throws ResourceNotFoundException
    {
        logger.trace(request.getRequestURI() + " accessed");

        MessageDetail message = new MessageDetail(request.getRequestURI() + " accessed", 7, true);
        rt.convertAndSend(WebemployeesApplication.QUEUE_NAME_HIGH, message);

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
    public ResponseEntity<?> getEmployees(HttpServletRequest request,
                                          @PathVariable
                                                  char letter)
    {
        logger.trace(request.getRequestURI() + " accessed");

        MessageDetail message = new MessageDetail(request.getRequestURI() + " accessed", 7, true);
        rt.convertAndSend(WebemployeesApplication.QUEUE_NAME_HIGH, message);

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
    public ModelAndView displayEmployeeTable(HttpServletRequest request)
    {
        logger.trace(request.getRequestURI() + " accessed");

        MessageDetail message = new MessageDetail(request.getRequestURI() + " accessed", 7, false);
        rt.convertAndSend(WebemployeesApplication.QUEUE_NAME_HIGH, message);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("employees");
        mav.addObject("employeeList", WebemployeesApplication.ourEmpList.empList);

        return mav;
    }
}
