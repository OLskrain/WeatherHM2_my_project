package com.example.olskr.weatherhm2;

import android.content.res.Resources;
import android.content.res.TypedArray;

import java.util.ArrayList;
import java.util.List;

// построитель источника данных
public class DataSourceBuilder {

    private final List<Soc> dataSource;   // строим этот источник данных из наших объектов Soc
    private final Resources resources;    // ресурсы приложения

    public DataSourceBuilder(Resources resources) {
        dataSource = new ArrayList<>(6);
        this.resources = resources;
    }

    // строим данные
    public List<Soc> build() {
        // строки описаний из ресурсов
        String[] descriptions = resources.getStringArray(R.array.descriptions);

        // заполнение источника данных
        for (int i = 0; i < descriptions.length; i++)
            dataSource.add(new Soc(descriptions[i])); //строим наши объекты в список
        return dataSource;
    }



}
