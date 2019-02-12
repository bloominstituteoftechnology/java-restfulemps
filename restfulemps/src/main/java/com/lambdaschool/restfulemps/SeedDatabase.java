package com.lambdaschool.restfulemps;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j // Lombok autocreates Slf4j-based logs
@Configuration // Indicates that a class declares one or more @Beans
               // Bean - object, method controlled by Spring. Contains java + metadata
public class SeedDatabase
{
    @Bean
    // CommandLineRunner - Spring Boot Runs all Beans at startup
    public CommandLineRunner initDB(EmployeeRepository emprepos)
    {
        return args ->
        {
            log.info("Seeding " + emprepos.save(new Employee("Steve", "Green", true, 45000)));
            log.info("Seeding " + emprepos.save(new Employee("May", "Ford", true, 80000)));
            log.info("Seeding " + emprepos.save(new Employee("John", "Jones", false, 75000)));
        };
    }
}
