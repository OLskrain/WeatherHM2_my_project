package com.example.olskr.weatherhm2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WeatherFragment extends BaseFragment {

    private final WeatherPresenter presenter = WeatherPresenter.getInstance();
    private static final String ARG_CITY = "ARG_CITY";
    private TextView tv_city;
    private String nameCity;

    public static WeatherFragment newInstance(String city){
        Bundle args = new Bundle();
        args.putString(ARG_CITY, city); //сохраняем в Bundle наш город, для передачи данных

        WeatherFragment weatherFragment = new WeatherFragment();
        weatherFragment.setArguments(args);
        return weatherFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        //получаем или восстанавливаем значение города
        if (savedInstanceState != null && savedInstanceState.containsKey(getResources().getString(R.string.nameCityKey))) {
            nameCity = savedInstanceState.getString(getResources().getString(R.string.nameCityKey));
        }else if(getArguments() != null){
            nameCity = getArguments().getString(ARG_CITY);
            presenter.initializationNameCity(nameCity);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.weather_fragment, container, false);

    }

    @Override
    //сохраняем данные перед перезапуском
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(getResources().getString(R.string.nameCityKey), presenter.getNameCity());
    }

    @Override
    protected void initLayout(View view, Bundle savedInstanceState) {
        tv_city = view.findViewById(R.id.tv_city);
        tv_city.setText(presenter.getNameCity());                         //выводим значение
    }
}
