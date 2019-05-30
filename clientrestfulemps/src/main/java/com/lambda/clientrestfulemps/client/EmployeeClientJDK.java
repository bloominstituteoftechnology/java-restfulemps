package com.lambda.clientrestfulemps.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;

public class EmployeeClientJDK
{
    // localhost:2019/data/allemployees
    public void readEmployees()
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

            System.out.println(jsonData.toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            IOUtils.closeQuietly(reader);
        }
    }

    public static void main(String[] args)
    {
        EmployeeClientJDK client = new EmployeeClientJDK();
        client.readEmployees();
    }
}
