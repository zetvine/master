package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;

public class Invoice implements MyDoc{
    String number = "";
    String date = "";
    String userName = "";
    String total = "";
    String currency = "";
    String exchangeRate = "";
    String itemName = "";
    String itemQuantity = "";

    public Invoice(){

    }
    public Invoice(String number,
                   String date,
                   String userName,
                   String total,
                   String currency,
                   String exchangeRate,
                   String itemName,
                   String itemQuantity
                   ) {
        this.number = number;
        this.date = date;
        this.userName = userName;
        this.total = total;
        this.currency = currency;
        this.exchangeRate = exchangeRate;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
    }
    @Override
    public String toString() {
        return "Номер: "+ this.number
                +"\r\nДата: " + this.date
                +"\r\nИмя пользователя: " + this.userName
                +"\r\nСумма: " + this.total
                +"\r\nВалюта: " + this.currency
                +"\r\nКурс валюты: " + this.exchangeRate
                +"\r\nТовар: " + this.itemName
                +"\r\nКоличество: " + this.itemQuantity;
    }

    @Override
    public void saveToFile(String filename) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonNode = objectMapper.createObjectNode();
        jsonNode.put("number", this.number);
        jsonNode.put("date", this.date);
        jsonNode.put("userName", this.userName);
        jsonNode.put("total", this.total);
        jsonNode.put("currency", this.currency);
        jsonNode.put("exchangeRate", this.exchangeRate);
        jsonNode.put("itemName", this.itemName);
        jsonNode.put("itemQuantity", this.itemQuantity);
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
        this.currency = jsonNode.get("currency").asText();
        this.exchangeRate = jsonNode.get("exchangeRate").asText();
        this.itemName = jsonNode.get("itemName").asText();
        this.itemQuantity = jsonNode.get("itemQuantity").asText();


    }

    @Override
    public String getExtension() {
        return ".inv";
    }


}
