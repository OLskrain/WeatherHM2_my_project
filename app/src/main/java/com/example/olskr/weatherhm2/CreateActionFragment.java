package com.example.olskr.weatherhm2;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class CreateActionFragment extends BaseFragment {

    private EditText et_action_fragment;
    private Button button_action_fragment;

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

    @Override
    public void changeIsPressed(boolean value) {
        super.changeIsPressed(value);
    }

    @Override
    protected void initLayout(View view, Bundle savedInstanceState) {
        et_action_fragment = view.findViewById(R.id.et_action_fragment);
        button_action_fragment = view.findViewById(R.id.button_action_fragment);

       et_action_fragment.setFilters(new InputFilter[] {
                new InputFilter() {
                    public CharSequence filter(CharSequence src, int start, int end, Spanned dst, int dstart, int dend) {
                        if(et_action_fragment.getText().toString().trim().length() < 25){
                            if (src.equals("")||src.equals("-")) { // for backspace
                                return src;
                            }
                            if (src.toString().matches("[a-zA-Z ]+")||src.toString().matches("[а-яА-Я ]+")) {
                                return src;
                            }
                            return "";
                        }
                        getToasty(getResources().getString(R.string.warning), getResources().getString(R.string.character_limit) );
                        return "";
                    }
                }
        });

        button_action_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBaseActivity().startWeatherFragment(et_action_fragment.getText().toString().trim()); //отправляем значение города
                changeIsPressed(false);
            }
        });

    }
}
