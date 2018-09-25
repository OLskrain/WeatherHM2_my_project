package com.example.olskr.weatherhm2.DataBaseAdapter;

public class WeatherRecord {
    private long id;
    private String city;
    private float current_temperature;
    private String detail;
    private long update_time;
    private int weather_icon;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public float getCurrent_temperature() {
        return current_temperature;
    }

    public void setCurrent_temperature(float current_temperature) {
        this.current_temperature = current_temperature;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }

    public int getWeather_icon() {
        return weather_icon;
    }

    public void setWeather_icon(int weather_icon) {
        this.weather_icon = weather_icon;
    }
}
