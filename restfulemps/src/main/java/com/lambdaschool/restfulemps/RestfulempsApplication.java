package com.lambdaschool.restfulemps;

// jrmmba - commented out to make servlet
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class RestfulempsApplication
//{
//
//    public static void main(String[] args)
//    {
//        SpringApplication.run(RestfulempsApplication.class, args);
//    }
//
//}

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class RestfulempsApplication extends SpringBootServletInitializer
{
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        return application.sources(RestfulempsApplication.class);
    }

    public static void main(String[] args) throws Exception
    {
        SpringApplication.run(RestfulempsApplication.class, args);
    }
}

