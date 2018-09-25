package com.example.olskr.weatherhm2.DataBaseAdapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

// Класс установки базы данных, создать базу даных, если ее нет, проапгрейдить ее
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "WeatherData.db"; // название бд
    private static final int DATABASE_VERSION = 2; // версия базы данных

    public static final String TABLE_WEATHER = "WeatherData"; // название таблицы в бд
    // названия столбцов
    public static final String COLUMN_ID = BaseColumns._ID;
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_CURRENT_TEMPERATURE = "current_temperature";
    public static final String COLUMN_DETAILS = "detail";
    public static final String COLUMN_UPDATE_TIME = "update_time";
    public static final String COLUMN_WEATHER_ICON = "weather_icon";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // вызывается при попытке доступа к базе данных, но когда еще эта база данных не создана
    //созддание бд
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_WEATHER + " (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_CITY + " TEXT," +
                COLUMN_CURRENT_TEMPERATURE + " REAL," + COLUMN_DETAILS + " TEXT,"
                + COLUMN_UPDATE_TIME + " INTEGER," + COLUMN_WEATHER_ICON + " INTEGER);");
    }

    // вызывается, когда необходимо обновление базы данных
    //изменение бд
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if ((oldVersion == 1) && (newVersion == 2)) {
//            String upgradeQuery = "ALTER TABLE " + TABLE_WEATHER + " ADD COLUMN " +
//                    COLUMN_NOTE_TITLE + " TEXT DEFAULT 'Title'";
//            db.execSQL(upgradeQuery);
        }
    }
}
