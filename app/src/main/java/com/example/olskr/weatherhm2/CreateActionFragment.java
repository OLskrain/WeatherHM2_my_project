package com.example.olskr.weatherhm2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class CreateActionFragment extends BaseFragment {

    private EditText et_action_fragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_action_fragment, container, false);
    }

    @Override
    protected void initLayout(View view, Bundle savedInstanceState) {
        et_action_fragment = view.findViewById(R.id.et_action_fragment);

        getBaseActivity().startWeatherFragment();
    }
}
