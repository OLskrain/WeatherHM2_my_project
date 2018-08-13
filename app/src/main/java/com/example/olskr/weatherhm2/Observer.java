package com.example.olskr.weatherhm2;

// Наблюдатель, вызывается updateText, когда надо отправить событие по изменению текста
public interface Observer {
    void updateText(String text);
}
