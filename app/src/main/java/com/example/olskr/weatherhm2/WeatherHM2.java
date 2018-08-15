package com.example.olskr.weatherhm2;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class WeatherHM2 extends AppCompatActivity {

    private TextView tv_city;
    private FloatingActionButton buttonFab;
    private Boolean isPressed = false;
    private String nameCity;

    private final WeatherPresenter presenter = WeatherPresenter.getInstance();

    //методы жизненного цикла
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_main);

        Toolbar toolbar = findViewById(R.id.toolbar); //реализуем туллбар
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        //вызываем элемент(кнопку) по id
        buttonFab = findViewById(R.id.buttonFab);
        tv_city = findViewById(R.id.tv_city);

        //слушатель для нашей кнопки
        buttonFab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(!isPressed){              //проверка на то, чтобы пользователь не смог запустить случайно несколько активностей
                    isPressed = true;
                    startNewActivity();
                }
            }
        });
    }

    private void startNewActivity(){
        //при старте новой активити передаем компонент старой активити
        Intent intent = new Intent(this, SecondActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    //получаем данный из второй активити
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }

        nameCity = data.getStringExtra(getResources().getString(R.string.TEXT));
        presenter.initializationNameCity(nameCity);

        tv_city.setText(presenter.getNameCity());
        isPressed = false;
    }

//методы жизненного цикла

    @Override
    //восстанавливаем данные после перезапуска
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null && savedInstanceState.containsKey(getResources().getString(R.string.nameCityKey))) {
            nameCity = savedInstanceState.getString(getResources().getString(R.string.nameCityKey));

            presenter.initializationNameCity(nameCity);
            tv_city.setText(presenter.getNameCity());
        }
    }

    @Override
    //сохраняем данные перед перезапуском
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(getResources().getString(R.string.nameCityKey), presenter.getNameCity());

    }
}
