package com.lambda.clientrestfulemps.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambda.clientrestfulemps.model.Employee;
import org.apache.commons.io.IOUtils;

public class EmployeeClientJDK
{
    // localhost:2019/data/allemployees
    public ArrayList<Employee> readEmployees()
    {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try
        {
            URL restAPIUrl = new URL("http://localhost:2019/data/allemployees");
            connection = (HttpURLConnection) restAPIUrl.openConnection();
            connection.setRequestMethod("GET");

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder jsonData = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
            {
                jsonData.append(line);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            ArrayList<Employee> myEmpList =
                    objectMapper.readValue(jsonData.toString(), new TypeReference<ArrayList<Employee>>(){});

            return myEmpList;

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            IOUtils.closeQuietly(reader);
        }

        return null;
    }

    public static void main(String[] args)
    {
        EmployeeClientJDK client = new EmployeeClientJDK();

        ArrayList<Employee> allEmployees = client.readEmployees();
        System.out.println(allEmployees);
    }
}
