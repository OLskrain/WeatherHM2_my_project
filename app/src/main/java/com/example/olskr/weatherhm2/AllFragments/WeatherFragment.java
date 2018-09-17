package com.example.olskr.weatherhm2.AllFragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olskr.weatherhm2.CityPreference;
import com.example.olskr.weatherhm2.DataSourceBuilder;
import com.example.olskr.weatherhm2.R;
import com.example.olskr.weatherhm2.RequestMaker;
import com.example.olskr.weatherhm2.Soc;
import com.example.olskr.weatherhm2.SocnetAdapter;
import com.example.olskr.weatherhm2.WeatherPresenter;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WeatherFragment extends BaseFragment {

//    private final WeatherPresenter presenter = WeatherPresenter.getInstance();
//    private static final String ARG_CITY = "ARG_CITY";
//    private TextView city_field;
//    private String nameCity;

    //Классовые переменные
    private static final String LOG_TAG = "WeatherFragment";
    private static final String FONT_FILENAME = "fonts/weather.ttf";

    //Реализация иконок погоды через шрифт (но можно и через setImageDrawable)
    private Typeface weatherFont;
    private TextView cityTextView;
    private TextView updatedTextView;
    private TextView detailsTextView;
    private TextView currentTemperatureTextView;
    private TextView weatherIcon;
    private TextView status;

//    public static WeatherFragment newInstance(String city){
//        Bundle args = new Bundle();
//        args.putString(ARG_CITY, city); //сохраняем в Bundle наш город, для передачи данных
//
//        WeatherFragment weatherFragment = new WeatherFragment();
//        weatherFragment.setArguments(args);
//        return weatherFragment;
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        BaseActivity activity = (BaseActivity) getActivity();
        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), FONT_FILENAME);//находим наш шрифт
        updateWeatherData(new CityPreference(activity).getCity());
        //получаем или восстанавливаем значение города

//        if (savedInstanceState != null && savedInstanceState.containsKey(getResources().getString(R.string.nameCityKey))) {
//            nameCity = savedInstanceState.getString(getResources().getString(R.string.nameCityKey));
//        }else if(getArguments() != null){
//            nameCity = getArguments().getString(ARG_CITY);
//            presenter.initializationNameCity(nameCity);
//        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.weather_fragment, container, false);
        cityTextView = rootView.findViewById(R.id.city_field);
        updatedTextView = rootView.findViewById(R.id.updated_field);
        detailsTextView = rootView.findViewById(R.id.details_field);
        currentTemperatureTextView = rootView.findViewById(R.id.current_temperature_field);
        weatherIcon = rootView.findViewById(R.id.weather_icon);
        status = rootView.findViewById(R.id.status);

        weatherIcon.setTypeface(weatherFont);
        return rootView;
    }

//    @Override
//    //сохраняем данные перед перезапуском
//    public void onSaveInstanceState(Bundle savedInstanceState){
//        super.onSaveInstanceState(savedInstanceState);
//        savedInstanceState.putString(getResources().getString(R.string.nameCityKey), presenter.getNameCity());
//    }

    @Override
    protected void initLayout(View view, Bundle savedInstanceState) {
//        city_field = view.findViewById(R.id.city_field);
//        city_field.setText(presenter.getNameCity());                         //выводим значение

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        // установим аниматор по умолчанию
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // эта установка служит для повышения производительности системы.
        recyclerView.setHasFixedSize(true);

        // будем работать со встроенным менеджером
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // строим источник данных
        DataSourceBuilder builder = new DataSourceBuilder(getResources());
        final List<Soc> dataSource = builder.build();
        // установим адаптер
        final SocnetAdapter adapter = new SocnetAdapter(dataSource);
        recyclerView.setAdapter(adapter);
    }

    //Обновление/загрузка погодных данных
    //тут рекомендуеться отображать процесс загрузки
    private void updateWeatherData(final String city) {
        // Создадим объект класса делателя запросов и налету сделаем анонимный класс слушателя
        final RequestMaker requestMaker = new RequestMaker(new RequestMaker.OnRequestListener() {
            // Обновим прогресс
            @Override
            public void onStatusProgress(String updateProgress) {
                status.setText(updateProgress);
            }
            // по окончании загрузки страницы вызовем этот метод, который и вставит текст в WebView
            @Override
            public void onComplete(JSONObject json) {
                if (json == null) {
                    Toast.makeText(getActivity(), getActivity().getString(R.string.place_not_found),
                            Toast.LENGTH_LONG).show();
                    Log.d(LOG_TAG, "ERROR");
                }else{
                    renderWeather(json);
                }
            }
        });

        requestMaker.make(city);
    }

    //Обработка загруженных данных
    private void renderWeather(JSONObject json) {
        try {
            cityTextView.setText(json.getString("name").toUpperCase(Locale.US) + ", "
                    + json.getJSONObject("sys").getString("country"));

            JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            JSONObject main = json.getJSONObject("main");
            detailsTextView.setText(details.getString("description").toUpperCase(Locale.US) + "\n" + "Humidity: "
                    + main.getString("humidity") + "%" + "\n" + "Pressure: " + main.getString("pressure") + " hPa");

            currentTemperatureTextView.setText(String.format("%.1f", main.getDouble("temp")) + " ℃");

            DateFormat df = DateFormat.getDateTimeInstance();
            String updatedOn = df.format(new Date(json.getLong("dt") * 1000));
            updatedTextView.setText("Last update: " + updatedOn);

            //получение иконки
            setWeatherIcon(details.getInt("id"), json.getJSONObject("sys").getLong("sunrise") * 1000,
                    json.getJSONObject("sys").getLong("sunset") * 1000);

        } catch (Exception e) {
            Log.e("SimpleWeather", "One or more fields not found in the JSON data");
        }
    }

    //Подстановка нужной иконки
    private void setWeatherIcon(int actualId, long sunrise, long sunset) {
        int id = actualId / 100;
        String icon = "";
        if (actualId == 800) {
            long currentTime = new Date().getTime();
            if (currentTime >= sunrise && currentTime < sunset) {
                icon = getActivity().getString(R.string.weather_sunny);
            } else {
                icon = getActivity().getString(R.string.weather_clear_night);
            }
        } else {
            Log.d("SimpleWeather", "id " + id);
            switch (id) {
                case 2:
                    icon = getActivity().getString(R.string.weather_thunder);
                    break;
                case 3:
                    icon = getActivity().getString(R.string.weather_drizzle);
                    break;
                case 5:
                    icon = getActivity().getString(R.string.weather_rainy);
                    break;
                case 6:
                    icon = getActivity().getString(R.string.weather_snowy);
                    break;
                case 7:
                    icon = getActivity().getString(R.string.weather_foggy);
                    break;
                case 8:
                    icon = getActivity().getString(R.string.weather_cloudy);
                    break;
            }
        }
        weatherIcon.setText(icon);
    }

    //Метод для доступа кнопки меню к данным
    public void changeCity(String city) {
        updateWeatherData(city);
    }
}
