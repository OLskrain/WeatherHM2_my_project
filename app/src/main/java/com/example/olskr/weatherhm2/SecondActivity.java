package com.example.olskr.weatherhm2;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class SecondActivity extends BaseActivity {

    private EditText et_secondActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        et_secondActivity = findViewById(R.id.et_secondActivity);

        //фильт на вводимые символы.
        /**Задачи на будущее:
         * 1)НУжна доработка по ограничению кол-ва,а так же фильтра, а лучше оргаизовать запросы в бд
         * 2)приделать подсказки при вводе*/
        et_secondActivity.setFilters(new InputFilter[] {
                new InputFilter() {
                    public CharSequence filter(CharSequence src, int start, int end, Spanned dst, int dstart, int dend) {
                       if(et_secondActivity.getText().toString().trim().length() < 25){
                        if (src.equals("")||src.equals("-")) { // for backspace
                            return src;
                        }
                        if (src.toString().matches("[a-zA-Z ]+")||src.toString().matches("[а-яА-Я ]+")) {
                            return src;
                        }
                        return "";
                    }
                    Toasty.warning(getApplicationContext(), getResources().getString(R.string.character_limit), Toast.LENGTH_SHORT).show();
                    return "";
                    }
                }
        });

        // обработка нажатия Enter
        et_secondActivity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

                    onBackPressed();
                    return true;
                }
                Toasty.success(getApplicationContext(), getResources().getString(R.string.repeated_input), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    //при возврате на первую активность передаем данные из второй активности
    public void onBackPressed() {
        Intent intent = new Intent();
        //проверка на пустое поле
        if(!et_secondActivity.getText().toString().equals("")) {
            intent.putExtra(getResources().getString(R.string.TEXT), et_secondActivity.getText().toString().trim());//передаем значение строки
            setResult(RESULT_OK, intent);
            finish();
        }else {
            Toasty.warning(getApplicationContext(), getResources().getString(R.string.repeated_input), Toast.LENGTH_SHORT).show();
        }
    }
}
