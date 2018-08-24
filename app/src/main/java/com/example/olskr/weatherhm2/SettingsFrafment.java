package com.example.olskr.weatherhm2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;

public class SettingsFrafment extends BaseFragment {

    protected String shape;
    protected Button button_settings_fragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.setting_fragment, container, false);

    }
    @Override
    protected void initLayout(View view, Bundle savedInstanceState) {
//        button_settings_fragment = view.findViewById(R.id.button_action_fragment);
//
//        shape = getString(R.string.temperatureC);
//
//        RadioButton radioButton1 = view.findViewById(R.id.radioButton11);
//        radioButton1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                shape = getString(R.string.temperatureC);
//            }
//        });
//
//        RadioButton radioButton2 = view.findViewById(R.id.radioButton22);
//        radioButton2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                shape = getString(R.string.temperatureF);
//            }
//        });
//
//        button_settings_fragment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                getBaseActivity().startWeatherFragment(shape); //отправляем значение города
////                changeIsPressed(false);
//            }
//        });
    }

}
