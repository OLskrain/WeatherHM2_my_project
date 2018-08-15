package com.example.olskr.weatherhm2;

import android.graphics.Bitmap;

public interface BaseView { //небольшой скелет приложения

    interface View {
        Boolean inNetworkAvailable();  //будущая проверка на подключение к интернету

        void initDrawer(String username, Bitmap profileImage); //метод проверки аккаунта, если будет регистрация в приложении
    }

    interface Presenter<V> {

        void onAttach(V view);

        void onDetach();

        void onDetachView();
    }
}
