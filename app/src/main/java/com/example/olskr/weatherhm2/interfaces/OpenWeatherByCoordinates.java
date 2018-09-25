package com.example.olskr.weatherhm2.interfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import com.example.olskr.weatherhm2.model.WeatherRequest;

//определяем интерфейс
public interface OpenWeatherByCoordinates {
        //показываем как мы хотим запрашивать данные от сервера. в данном случае с помощью HTTP протокола и метода GET
        @GET("data/2.5/weather")
        //api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}

        //апрос с сервера всегда будет объектом CALL, но с помощью дженерика мы уточняем <WeatherRequest>
        Call<WeatherRequest> loadByCoordinatesWeatherData(@Query("lat") String lat, @Query("lon") String lon,
                                                          @Query("units") String units, @Query("appid") String keyApi);

}
