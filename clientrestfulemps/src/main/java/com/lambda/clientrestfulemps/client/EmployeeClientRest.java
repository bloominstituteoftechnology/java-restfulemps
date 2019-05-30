package com.lambda.clientrestfulemps.client;

import com.lambda.clientrestfulemps.model.Employee;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EmployeeClientRest
{
    private RestTemplate restTemplate = new RestTemplate();

    public ArrayList<Employee> getAllEmployees()
    {
        ParameterizedTypeReference<ArrayList<Employee>> responseType =
                new ParameterizedTypeReference<ArrayList<Employee>>() {};
        ResponseEntity<ArrayList<Employee>> responseEntity =
                restTemplate.exchange("http://localhost:2019/data/allemployees",
                        HttpMethod.GET, null, responseType);
        ArrayList<Employee> allEmps = responseEntity.getBody();

        return allEmps;
    }

    public static void main(String[] args)
    {
        EmployeeClientRest client = new EmployeeClientRest();
        ArrayList<Employee> allEmployees = client.getAllEmployees();
        System.out.println(allEmployees);
    }
}
