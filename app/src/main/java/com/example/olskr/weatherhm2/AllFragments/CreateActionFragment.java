package com.example.olskr.weatherhm2.AllFragments;

import android.content.Context;
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

import com.example.olskr.weatherhm2.AllFragments.BaseFragment;
import com.example.olskr.weatherhm2.R;

import java.util.regex.Pattern;

public class CreateActionFragment extends BaseFragment {

    private EditText et_action_fragment;
    private Button button_action_fragment;
    TextInputEditText login;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(getResources().getString(R.string.createActionFragment), getResources().getString(R.string.onViewCreated));
        return inflater.inflate(R.layout.create_action_fragment, container, false);

    }
 //демонстрация жизненного цикла
    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(getResources().getString(R.string.createActionFragment), getResources().getString(R.string.onDetach));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(getResources().getString(R.string.createActionFragment), getResources().getString(R.string.onAttach));
    }

//    @Override
////    public void changeIsPressed(boolean value) {
////        super.changeIsPressed(value);
////    }

    @Override
    protected void initLayout(View view, Bundle savedInstanceState) {

        button_action_fragment = view.findViewById(R.id.button_action_fragment);

        // регулярные выражения, позволяют проверить на соответсвие шаблону
        // Это имя первая буква большая латинская, остальные маленькие латинские
        final Pattern checkLogin = Pattern.compile("^[A-Z][a-z]{2,}$");

        login = view.findViewById(R.id.inputLoginName);

        // Чтобы не докучать пользователю при вводе каждой буквы, сделаем проверку при потере фокуса
        login.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            // как только фокус потерян, сразу проверяем на валидность данные
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) return;
                TextView tv = (TextView) v;
                // это сама валидация, огна вынесена в отдельный метод, чтобы не дублировать код
                // см вызов ниже
                validate(tv, checkLogin, "Неккоректный ввод!");
            }
        });

//        button_action_fragment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getBaseActivity().startWeatherFragment(login.getText().toString().trim()); //отправляем значение города
//                changeIsPressed(false);
//            }
//        });
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
}
