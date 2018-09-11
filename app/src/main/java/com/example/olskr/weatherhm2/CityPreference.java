package com.example.olskr.weatherhm2;

import android.app.Activity;
import android.content.SharedPreferences;


//Класс, который отвечает за хранение города, когда приложение свернуто или отключено
public class CityPreference {

    private static final String KEY = "city";
    private static final String MOSCOW = "Moscow";
    private SharedPreferences userPreferences; //специальный класс для длительного хранения данных.
    // создаеться папка в приложении.в ней текстовый файл. Файл ханиться до удаления приложения
    //ЛУЧШЕ всего использовать если небольшие по количеству данные.

    //конструктор,где передаем контекст приложения
    public CityPreference(Activity activity) {
        userPreferences = activity.getPreferences(Activity.MODE_PRIVATE);//Activity.MODE_PRIVATE - флажок, который говорит, что данные доступны только нашему приложению
    }

    //Возврашаем город по умолчанию, если SharedPreferences пустой
    public String getCity() {
        return userPreferences.getString(KEY, MOSCOW);
    }

    //вызываем, когда хотим сохранить город перед закрытие
    public void setCity(String city) {
        userPreferences.edit().putString(KEY, city).apply();
    }

}