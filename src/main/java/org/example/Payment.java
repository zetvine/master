package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;

public class Payment implements MyDoc{

    String number = "";
    String date = "";
    String userName = "";
    String total = "";
    String employee = "";
    public Payment() {

    }
    public Payment(String number,
                 String date,
                 String userName,
                 String total,
                   String employee) {
        this.number = number;
        this.date = date;
        this.userName = userName;
        this.total = total;
        this.employee = employee;
}
    @Override
    public String toString() {
        return "Номер: "+ this.number
                +"\r\nДата: " + this.date
                +"\r\nИмя пользователя: " + this.userName
                +"\r\nСумма: " + this.total
                +"\r\nСотрудник: " + this.employee;

    }

    @Override
    public void saveToFile(String filename) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonNode = objectMapper.createObjectNode();
        jsonNode.put("number", this.number);
        jsonNode.put("date", this.date);
        jsonNode.put("userName", this.userName);
        jsonNode.put("total", this.total);
        jsonNode.put("employee", this.employee);

        try {
            objectMapper.writeValue(new File(filename), jsonNode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void loadFromFile(String filename) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(new File(filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.number = jsonNode.get("number").asText();
        this.date = jsonNode.get("date").asText();
        this.userName = jsonNode.get("userName").asText();
        this.total = jsonNode.get("total").asText();
        this.employee = jsonNode.get("employee").asText();
    }

    @Override
    public String getExtension() {
        return ".pay";
    }
}
