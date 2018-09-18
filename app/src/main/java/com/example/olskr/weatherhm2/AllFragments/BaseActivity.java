package com.example.olskr.weatherhm2.AllFragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.olskr.weatherhm2.CityPreference;
import com.example.olskr.weatherhm2.R;

import es.dmoral.toasty.Toasty;

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

//    private FloatingActionButton buttonFab;
//    private boolean isPressed;
//    private final WeatherPresenter presenter = WeatherPresenter.getInstance();

    private static final String POSITIVE_BUTTON_TEXT = "Go";
    private static final String WEATHER_FRAGMENT_TAG = "43ddDcdd-c9e0-4794-B7e6-cf05af49fbf0";

    public CityPreference cityPreference;

//    public void setIsPressed(Boolean pressed) {
//        this.isPressed = pressed;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        initLayout(savedInstanceState);
    }

    private void initLayout(Bundle savedInstanceState) { //метод, где мы работаем с конкретным Layout
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
//        buttonFab = findViewById(R.id.buttonFab);

//        //слушатель для нашей кнопки
//        buttonFab.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                if (!isPressed){              //проверка на то, чтобы пользователь не смог запустить случайно несколько активностей
//                    isPressed = true;
//                    getCurrentFragment();
//                    addFragment(new CreateActionFragment());
//                }
//
//            }
//        });

        cityPreference = new CityPreference(this);//теперь мы можем сохранять данные и получать их из нашего класса
        //если мы приложение только открыли
        if(savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content_frame, new WeatherFragment(), WEATHER_FRAGMENT_TAG)
                    .commit();
        }
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
//        savedInstanceState.putString(getResources().getString(R.string.nameCityKey), presenter.getNameCity());

    }

//    private void getCurrentFragment(){ //узнаем какой сейчас фрагмент, для настройки нашшей кнопки в дальнейшем
//        getSupportFragmentManager().findFragmentById(R.id.content_frame);
//    }

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
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        switch (item.getItemId()) {
            case R.id.nav_settings:                  // наши кнопки в меню навигации
                addFragment(new SettingsFrafment());
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_info:
                addFragment(new AboutFragment());
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_evaluate:
                goToRateApp();
                drawer.closeDrawer(GravityCompat.START);
                return true;
            default:
                drawer.closeDrawer(GravityCompat.START);
                return true;
        }
    }

    // Меню Action bar - установка меню в action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // Меню Action bar - выбор пункта меню
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_list_cities:
                goToListCities();
                return true;
            case R.id.menu_settings:
                addFragment(new SettingsFrafment());
                return true;
            case R.id.change_city:
                showInputDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //отображение диалогового окна
    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(getResources().getString(R.string.change_city_dialog));
        //добавляем едит текст
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT); //указываем что будет только текст,а не номер напрмер(тип данных)
        builder.setView(input);

        //добавляем кнопку
        builder.setPositiveButton(POSITIVE_BUTTON_TEXT, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changeCity(input.getText().toString());
            }
        });

        builder.show();
    }

    //обновляем вид, сохраняем выбранный город
    public void changeCity(String city) {
        //находим наш фрагмент по тегу
        WeatherFragment weatherFragment = (WeatherFragment) getSupportFragmentManager().findFragmentByTag(WEATHER_FRAGMENT_TAG);
        weatherFragment.changeCity(city);
        cityPreference.setCity(city);//обновляем данные в нашем хранилише
    }

    private void goToListCities() {
        Toast.makeText(this, "Переходим в список городов", Toast.LENGTH_SHORT).show();
    }

//    private void goToSettings(){
//        Toast.makeText(this, "Переходим в настройки", Toast.LENGTH_SHORT).show();
//    }
    private void goToRateApp(){
        Toast.makeText(this, "Переходим к оценке приложения", Toast.LENGTH_SHORT).show();
    }


//    public void startWeatherFragment(String city) {
//        addFragment(WeatherFragment.newInstance(city));
//    }

    public void toasty(String log, String resources){
        if(log.equals(getResources().getString(R.string.warning))){
            Toasty.warning(getApplicationContext(), resources, Toast.LENGTH_SHORT).show();
        }if(log.equals(R.string.success)){
            Toasty.success(getApplicationContext(), resources, Toast.LENGTH_SHORT).show();
        }
    }
}
