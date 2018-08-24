package com.example.olskr.weatherhm2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FloatingActionButton buttonFab;
    private boolean isPressed;
    private final WeatherPresenter presenter = WeatherPresenter.getInstance();

    public void setIsPressed(Boolean pressed) {
        this.isPressed = pressed;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        initLayout();
    }

    private void initLayout() { //метод, где мы работаем с конкретным Layout
        Toolbar toolbar = findViewById(R.id.toolbar); //наш тулбар
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = findViewById(R.id.drawer_layout); //панелька, которая выезжает
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);  //панель навигации
        navigationView.setNavigationItemSelectedListener(this);

        //вызываем элемент(кнопку) по id
        buttonFab = findViewById(R.id.buttonFab);

        //слушатель для нашей кнопки
        buttonFab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(!isPressed){              //проверка на то, чтобы пользователь не смог запустить случайно несколько активностей
                    isPressed = true;
                    getCurrentFragment();
                    addFragment(new CreateActionFragment());
                }

            }
        });

        addFragment(new WeatherFragment()); //наш первый фрагмент
    }

    private void addFragment(Fragment fragment){ // метод для добавления фрагмента
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }

    @Override
    //сохраняем данные перед перезапуском
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(getResources().getString(R.string.nameCityKey), presenter.getNameCity());

    }

    private void getCurrentFragment(){ //узнаем какой сейчас фрагмент, для настройки нашшей кнопки в дальнейшем
        getSupportFragmentManager().findFragmentById(R.id.content_frame);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_settings) {   // наши кнопки в меню навигации

            getCurrentFragment();
            addFragment(new SettingsFrafment());
        } else if (id == R.id.nav_info) {
            getCurrentFragment();
            addFragment(new AboutFragment());
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void startWeatherFragment(String city) {
        addFragment(WeatherFragment.newInstance(city));
    }

    public void toasty(String log, String resources){
        if(log.equals(getResources().getString(R.string.warning))){
            Toasty.warning(getApplicationContext(), resources, Toast.LENGTH_SHORT).show();
        }if(log.equals(R.string.success)){
            Toasty.success(getApplicationContext(), resources, Toast.LENGTH_SHORT).show();
        }
    }
}
