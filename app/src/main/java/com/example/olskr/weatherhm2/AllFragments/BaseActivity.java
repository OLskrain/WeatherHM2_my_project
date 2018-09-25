package com.example.olskr.weatherhm2.AllFragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.olskr.weatherhm2.CustomAppSetting;
import com.example.olskr.weatherhm2.R;

import es.dmoral.toasty.Toasty;

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

//    private FloatingActionButton buttonFab;
//    private boolean isPressed;
//    private final WeatherPresenter presenter = WeatherPresenter.getInstance();

    private static final String POSITIVE_BUTTON_TEXT = "Go";
    private static final String WEATHER_FRAGMENT_TAG = "43ddDcdd-c9e0-4794-B7e6-cf05af49fbf0";

    private static final int PERMISSION_REQUEST_CODE = 10; //здесь может быть любое число
    private LocationManager locationManager;
    private String provider;

    public CustomAppSetting customAppSetting;

//    public void setIsPressed(Boolean pressed) {
//        this.isPressed = pressed;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        // Проверим на пермиссии, и если их нет, запросим у пользователя
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // запросим координаты
            requestLocation();
        } else {
            // пермиссии нет, будем запрашивать у пользователя
            requestLocationPermissions();
        }

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
//                    addFragment(new ListCitiesFragment());
//                }
//
//            }
//        });

        customAppSetting = new CustomAppSetting(this);//теперь мы можем сохранять данные и получать их из нашего класса
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
                .addToBackStack("")
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
                addFragment(new SettingsFragment());
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
                addFragment(new ListCitiesFragment());
                return true;
            case R.id.menu_settings:
                addFragment(new SettingsFragment());
                return true;
            case R.id.change_city:
                showInputDialog();
                return true;
            case R.id.refresh:
                changeCoordinates();
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
    public void changeCoordinates() {
        //находим наш фрагмент по тегу
        WeatherFragment weatherFragment = (WeatherFragment) getSupportFragmentManager().findFragmentByTag(WEATHER_FRAGMENT_TAG);
        weatherFragment.changeCoordinates(customAppSetting.getLatitude(), customAppSetting.getLongitude());
    }

    //обновляем вид, сохраняем выбранный город
    public void changeCity(String city) {
        //находим наш фрагмент по тегу
        WeatherFragment weatherFragment = (WeatherFragment) getSupportFragmentManager().findFragmentByTag(WEATHER_FRAGMENT_TAG);
//        weatherFragment.changeCity(city);
        customAppSetting.setCity(city);//обновляем данные в нашем хранилише
    }

//    private void goToListCities() {
//        Toast.makeText(this, "Переходим в список городов", Toast.LENGTH_SHORT).show();
//    }

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

    /**Реализация запроса геоданных*/
    private void requestLocation() {
        // Если пермиссии все таки нет - то просто выйдем, приложение не имеет смысла
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;
        //возможно тут стоит показать тост о том, что разрешений нет(вдруг пользователь нажал ни когда не показывать запросы).

        //универсальный способ вызова метода сервиса в Android. В качестве праметра мы можем указать какой сервис вызовем.
        //например (LOCATION_SERVICE) - геолокация
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE); //указываем точность определения местоположения

        // получим наиболее подходящий провайдер геолокации по критериям
        // Но можно и самому назначать какой провайдер использовать.
        // В основном это LocationManager.GPS_PROVIDER или LocationManager.NETWORK_PROVIDER
        // но может быть и LocationManager.PASSIVE_PROVIDER, это когда координаты уже кто-то недавно получил.
        provider = locationManager.getBestProvider(criteria, true); //устанавливаем преоритет провайдера на усмотрении менеджера
        if (provider != null) {
            // Будем получать геоположение через каждые 10 секунд или каждые 10 метров
            //тут стоит быть осторожными, так как если будет слишком частые обновления, тто батарейка будет быстро садиться
            locationManager.requestLocationUpdates(provider, 10000, 10, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) { //получаем объект с данными геолокации
                    String latitude = Double.toString(location.getLatitude());  // Широта
                    String longitude = Double.toString(location.getLongitude());// Долгота

                    customAppSetting.setLatitude(latitude);
                    customAppSetting.setLongitude(longitude);
                }
                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }
                @Override
                public void onProviderEnabled(String provider) {
                }
                @Override
                public void onProviderDisabled(String provider) {
                }
            });
        }
    }

    // Запрос пермиссии для геолокации. Денамические разрешения
    private void requestLocationPermissions() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
            // Запросим эти две пермиссии у пользователя
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    PERMISSION_REQUEST_CODE);
        }
    }


    // Это результат запроса у пользователя пермиссии. сюда передаются разрешения от пользователя при запросе
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {   // Это та самая пермиссия, что мы запрашивали? проверяем PERMISSION_REQUEST_CODE
            if (grantResults.length == 2 &&
                    (grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                // Все препоны пройдены и пермиссия дана
                requestLocation();
            }
        }
    }
}
