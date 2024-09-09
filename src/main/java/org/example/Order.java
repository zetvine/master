package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;

public class Order implements MyDoc {

    String number = "";
    String date = "";
    String userName = "";
    String counterAgent = "";
    String total = "";
    String currency = "";
    String exchangeRate = "";
    String comission = "";

    public Order(){

    }

    public Order(String number,
                 String date,
                 String userName,
                 String counterAgent,
                 String total,
                 String currency,
                 String exchangeRate,
                 String comission) {
        this.number = number;
        this.date = date;
        this.userName = userName;
        this.counterAgent = counterAgent;
        this.total = total;
        this.currency = currency;
        this.exchangeRate = exchangeRate;
        this.comission = comission;
    }

    @Override
    public String toString() {
        return "Номер: "+ this.number
                +"\r\nДата: " + this.date
                +"\r\nИмя пользователя: " + this.userName
                +"\r\nКонтрагент: " + this.counterAgent
                +"\r\nСумма: " + this.total
                +"\r\nВалюта: " + this.currency
                +"\r\nКурс валюты: " + this.exchangeRate
                +"\r\nКомиссия: " + this.comission;
    }

    @Override
    public void saveToFile(String filename) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonNode = objectMapper.createObjectNode();
        jsonNode.put("number", this.number);
        jsonNode.put("date", this.date);
        jsonNode.put("userName", this.userName);
        jsonNode.put("counterAgent", this.counterAgent);
        jsonNode.put("total", this.total);
        jsonNode.put("currency", this.currency);
        jsonNode.put("exchangeRate", this.exchangeRate);
        jsonNode.put("comission", this.comission);

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
        this.counterAgent = jsonNode.get("counterAgent").asText();
        this.total = jsonNode.get("total").asText();
        this.currency = jsonNode.get("currency").asText();
        this.exchangeRate = jsonNode.get("exchangeRate").asText();
        this.comission = jsonNode.get("comission").asText();


    }

    @Override
    public String getExtension() {
        return ".ord";
    }

}
