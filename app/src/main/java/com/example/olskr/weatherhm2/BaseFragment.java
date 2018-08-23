package com.example.olskr.weatherhm2;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

abstract class BaseFragment extends Fragment{

    private BaseActivity baseActivity;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initLayout(view, savedInstanceState);
    }

    protected abstract void initLayout(View view, Bundle savedInstanceState);

    @Override
    public void onAttach(Context context) { //когда связываемся с активностью
        super.onAttach(context);
        if (getActivity() != null){
             baseActivity = (BaseActivity) getActivity();
        }
    }

    @Override
    public void onDetach() { //больше не присоединены к нашей активности
        baseActivity = null;
        super.onDetach();
    }

    public BaseActivity getBaseActivity() { //чтобы мы могли получать от наследуюмых фрагментов baseActivity
        return baseActivity;
    }

    public void getToasty(String log, String resources) {
        baseActivity.toasty(log, resources);
    }

    public void changeIsPressed(boolean value){
        baseActivity.setIsPressed(value);
    }
}
