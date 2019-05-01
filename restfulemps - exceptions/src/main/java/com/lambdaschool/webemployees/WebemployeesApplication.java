package com.lambdaschool.webemployees;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication

//TODO Add logging
//FIXME
public class WebemployeesApplication
{

    public static EmpList ourEmpList;
    public static void main(String[] args)
    {
        ourEmpList = new EmpList();
        ApplicationContext ctx = SpringApplication.run(WebemployeesApplication.class, args);

        DispatcherServlet dispatcherServlet = (DispatcherServlet)ctx.getBean("dispatcherServlet");
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
    }
}

