package com.example.olskr.weatherhm2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class BaseActivity extends AppCompatActivity
        implements BaseView.View,NavigationView.OnNavigationItemSelectedListener {

    private TextView tv_city;
    private FloatingActionButton buttonFab;
    private Boolean isPressed = false;
    private String nameCity;

    private final WeatherPresenter presenter = WeatherPresenter.getInstance();

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
        tv_city = findViewById(R.id.tv_city);

        //слушатель для нашей кнопки
        buttonFab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(!isPressed){              //проверка на то, чтобы пользователь не смог запустить случайно несколько активностей
                    isPressed = true;
                    startNewActivity();
                }
            }
        });
    }

    private void startNewActivity() {
        //при старте новой активити передаем компонент старой активити
        Intent intent = new Intent(this, SecondActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    //получаем данный из второй активити
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }

        nameCity = data.getStringExtra(getResources().getString(R.string.TEXT));
        presenter.initializationNameCity(nameCity);

        tv_city.setText(presenter.getNameCity());
        isPressed = false;
    }

    @Override
    //восстанавливаем данные после перезапуска
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null && savedInstanceState.containsKey(getResources().getString(R.string.nameCityKey))) {
            nameCity = savedInstanceState.getString(getResources().getString(R.string.nameCityKey));

            presenter.initializationNameCity(nameCity);
            tv_city.setText(presenter.getNameCity());
        }
    }

    @Override
    //сохраняем данные перед перезапуском
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(getResources().getString(R.string.nameCityKey), presenter.getNameCity());

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

        } else if (id == R.id.nav_info) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public Boolean inNetworkAvailable() {
        return true;
    }

    @Override
    public void initDrawer(String username, Bitmap profileImage) {}
}
