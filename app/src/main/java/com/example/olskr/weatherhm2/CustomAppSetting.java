package com.example.olskr.weatherhm2;

import android.app.Activity;
import android.content.SharedPreferences;


//Класс, который отвечает за хранение города, когда приложение свернуто или отключено
public class CustomAppSetting {

    private static final String KEY = "city";
    private static final String MOSCOW = "Moscow";
    private static final String MY_KEY_LATITUDE = "lat";
    private static final String LATITUDE = "59.939095";
    private static final String MY_KEY_LONGITUDE = "lon";
    private static final String LONGITUDE = "30.315868";
    private SharedPreferences userPreferences; //специальный класс для длительного хранения данных.
    // создаеться папка в приложении.в ней текстовый файл. Файл ханиться до удаления приложения
    //ЛУЧШЕ всего использовать если небольшие по количеству данные.

    //конструктор,где передаем контекст приложения
    public CustomAppSetting(Activity activity) {
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

    public String getLatitude() {
        return userPreferences.getString(MY_KEY_LATITUDE, LATITUDE);
    }

    public String getLongitude() {
        return userPreferences.getString(MY_KEY_LONGITUDE, LONGITUDE);
    }

    public void setLatitude(String lat) {
        userPreferences.edit().putString(MY_KEY_LATITUDE, lat).apply();
    }
    public void setLongitude(String lon) {
        userPreferences.edit().putString(MY_KEY_LONGITUDE, lon).apply();
    }
}