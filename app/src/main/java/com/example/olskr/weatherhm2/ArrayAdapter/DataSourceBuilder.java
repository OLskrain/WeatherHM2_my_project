package com.example.olskr.weatherhm2.ArrayAdapter;

import android.content.res.Resources;

import com.example.olskr.weatherhm2.ArrayAdapter.Soc;
import com.example.olskr.weatherhm2.R;

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
