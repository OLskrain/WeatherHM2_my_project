package com.example.olskr.weatherhm2;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

public class BaseFragment extends Fragment implements BaseView.View{

    private BaseActivity baseActivity;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initLayout(view, savedInstanceState);
    }

    private void initLayout(View view, Bundle savedInstanceState) {}

    @Override
    public void onAttach(Context context) { //когда связываемся с активностью
        super.onAttach(context);

    }

    @Override
    public Boolean inNetworkAvailable() {
        return null;
    }

    @Override
    public void initDrawer(String username, Bitmap profileImage) {

    }
}
