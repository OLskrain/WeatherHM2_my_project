package com.example.olskr.weatherhm2.AllFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.olskr.weatherhm2.CustomAppSetting;
import com.example.olskr.weatherhm2.R;

import java.util.regex.Pattern;

public class ListCitiesFragment extends BaseFragment {

    private EditText et_action_fragment;
    private Button addNewCity;
    TextInputEditText city;
    private static final String WEATHER_FRAGMENT_TAG = "43ddDcdd-c9e0-4794-B7e6-cf05af49fbf0";
    public CustomAppSetting customAppSetting;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(getResources().getString(R.string.createActionFragment), getResources().getString(R.string.onViewCreated));
        return inflater.inflate(R.layout.list_cities_fragment, container, false);
    }

//    @Override
////    public void changeIsPressed(boolean value) {
////        super.changeIsPressed(value);
////    }

    @Override
    protected void initLayout(View view, Bundle savedInstanceState) {
        customAppSetting = new CustomAppSetting(getBaseActivity());//теперь мы можем сохранять данные и получать их из нашего класса

        addNewCity = view.findViewById(R.id.add_new_city);

        // регулярные выражения, позволяют проверить на соответсвие шаблону
        // Это имя первая буква большая латинская, остальные маленькие латинские
        final Pattern checkLogin = Pattern.compile("^[A-Z][a-z]{2,}$");

        city = view.findViewById(R.id.input_write_city);

//        // Чтобы не докучать пользователю при вводе каждой буквы, сделаем проверку при потере фокуса
//        city.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            // как только фокус потерян, сразу проверяем на валидность данные
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) return;
//                TextView tv = (TextView) v;
//                // это сама валидация, огна вынесена в отдельный метод, чтобы не дублировать код
//                // см вызов ниже
//                validate(tv, checkLogin, "Неккоректный ввод!");
//            }
//        });

        addNewCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getBaseActivity().startWeatherFragment(login.getText().toString().trim()); //отправляем значение города
//                changeIsPressed(false);

//                changeCity(city.getText().toString());

            }
        });
    }

    // Валидация
    private void validate(TextView tv, Pattern check, String message){
        String value = tv.getText().toString();
        if (check.matcher(value).matches()){    // проверим на основе регулярных выражений
            hideError(tv);
        }
        else{
            showError(tv, message);
        }
    }

    // показать ошибку
    private void showError(TextView view, String message) {
        view.setError(message);
    }

    // спрятать ошибку
    private void hideError(TextView view) {
        view.setError(null);
    }

//    //обновляем вид, сохраняем выбранный город
//    public void changeCity(String city) {
//        //находим наш фрагмент по тегу
//        WeatherFragment weatherFragment = (WeatherFragment) getSupportFragmentManager().findFragmentByTag(WEATHER_FRAGMENT_TAG);
//        weatherFragment.changeCity(city);
//        customAppSetting.setCity(city);//обновляем данные в нашем хранилише
//    }
}
