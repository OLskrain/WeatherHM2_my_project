package com.example.olskr.weatherhm2;

import android.app.Application;

import es.dmoral.toasty.Toasty;


public class App extends Application { //класс чтобы подтянуть  Toasty. а вообще для общих настроек проекта

    @Override
    public void onCreate() {
        super.onCreate();
        Toasty.Config.getInstance().setSuccessColor(getResources().getColor(R.color.green)).apply(); //удачные сообщения теперь светятся зеленым
    }
}
