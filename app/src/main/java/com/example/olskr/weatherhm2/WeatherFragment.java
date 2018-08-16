package com.example.olskr.weatherhm2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WeatherFragment extends BaseFragment {

    private static final String ARG_CITY = "ARG_CITY";
    private TextView tv_city;
    private String city;

    public static WeatherFragment newInstamce(String city){
        Bundle args = new Bundle();
        args.putString(ARG_CITY, city); //сохраняем в Bundle наш город, для передачи данных

        WeatherFragment weatherFragment = new WeatherFragment();
        weatherFragment.setArguments(args);
        return weatherFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            city = getArguments().getString(ARG_CITY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.weather_layout, container, false);
    }

    @Override
    protected void initLayout(View view, Bundle savedInstanceState) {
        tv_city = view.findViewById(R.id.tv_city);
        tv_city.setText(city); //передаем значение
    }
}
