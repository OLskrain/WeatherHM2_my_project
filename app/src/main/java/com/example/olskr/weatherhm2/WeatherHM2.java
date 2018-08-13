package com.example.olskr.weatherhm2;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

        String instanceState;
        if (savedInstanceState == null) {
            instanceState =  getResources().getString(R.string.first_start);
        }
        else {
            instanceState = getResources().getString(R.string.restart);
        }

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
                    toastAndLog(R.string.start_new_activity);
                }
            }
        });

        Toasty.success(getApplicationContext(), instanceState + " - " + getResources().getString(R.string.onCreate), Toast.LENGTH_SHORT).show();
        Log.d(getResources().getString(R.string.TAG), getResources().getString(R.string.onCreate));
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
    protected void onStart() {
        super.onStart();
       toastAndLog(R.string.onStart);
    }

    @Override
    //восстанавливаем данные после перезапуска
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null && savedInstanceState.containsKey(getResources().getString(R.string.nameCityKey))) {
            nameCity = savedInstanceState.getString(getResources().getString(R.string.nameCityKey));

            presenter.initializationNameCity(nameCity);
            tv_city.setText(presenter.getNameCity());
        }
        Toasty.success(getApplicationContext(), getResources().getString(R.string.restart) + " - " + getResources().getString(R.string.onRestoreInstanceState), Toast.LENGTH_SHORT).show();
        Log.d(getResources().getString(R.string.TAG), getResources().getString(R.string.onRestoreInstanceState));
    }

    @Override
    protected void onPause() {
        super.onPause();
       toastAndLog(R.string.onPause);
    }

    @Override
    //сохраняем данные перед перезапуском
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(getResources().getString(R.string.nameCityKey), presenter.getNameCity());
        toastAndLog(R.string.onSaveInstanceState);
        Log.d(getResources().getString(R.string.TAG), getResources().getString(R.string.onSaveInstanceState));
    }

    @Override
    protected void onResume() {
        super.onResume();
      toastAndLog(R.string.onResume);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
       toastAndLog(R.string.onRestart);
    }

    @Override
    protected void onStop() {
        super.onStop();
        toastAndLog(R.string.onStop);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        toastAndLog(R.string.onDestroy);
    }
    public void toastAndLog(int resources){
        Toasty.success(getApplicationContext(), getResources().getString(resources), Toast.LENGTH_SHORT).show();
        Log.d(getResources().getString(R.string.TAG), getResources().getString(resources));
    }
}
