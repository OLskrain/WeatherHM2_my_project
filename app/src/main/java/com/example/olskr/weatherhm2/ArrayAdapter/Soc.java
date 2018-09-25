package com.example.olskr.weatherhm2.ArrayAdapter;

// класс, где мы храним данные для карточки
public class Soc {

    private final String description; // описание

    public Soc(String description){
        this.description = description;
    }

    // геттеры
    public String getDescription(){
        return description;
    }
}
