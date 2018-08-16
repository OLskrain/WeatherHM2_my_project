package com.example.olskr.weatherhm2;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

abstract class BaseFragment extends Fragment implements BaseView.View{

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
        baseActivity = (BaseActivity) context;
        baseActivity.onFragmentAttached();
    }

    @Override
    public void onDetach() { //больше не присоединены к нашей активности
        baseActivity = null;
        super.onDetach();
    }

    public BaseActivity getBaseActivity() { //чтобы мы могли получать от наследуюмых фрагментов baseActivity
        return baseActivity;
    }

    @Override
    public Boolean inNetworkAvailable() {
        if(baseActivity != null){
            return baseActivity.inNetworkAvailable();
        }
        return false;
    }

    @Override
    public void initDrawer(String username, Bitmap profileImage) {}

    interface Callback{
        void onFragmentAttached(); //здесь мы поймем, что фрагмент подключен к активити

        void onFragmentDetached(String tag); // что фрагмент отключен,передаем название фрагмента
    }
}
