package com.example.olskr.weatherhm2.ArrayAdapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.olskr.weatherhm2.R;

import java.util.List;

// адаптер
public class SocnetAdapter extends RecyclerView.Adapter<SocnetAdapter.ViewHolder> {
    private final List<Soc> dataSource;                         // Наш источник данных

    // этот класс хранит связь между данными и элементами View
    // Сложные данные могут потребовать несколько View на один пункт списка
    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView description;

        public ViewHolder(View view) {
            super(view);
            description = view.findViewById(R.id.description);
        }
    }

    // Передаем в конструктор источник данных
    // В нашем случае это массив, но может быть и запросом к БД
    public SocnetAdapter(List<Soc> dataSource) {
        this.dataSource = dataSource;
    }

    // Создать новый элемент пользовательского интерфейса
    @NonNull
    @Override
    public SocnetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Создаём новый элемент пользовательского интерфейса через Inflater
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        // Здесь можно установить параметры
        ViewHolder vh = new ViewHolder(view);

        // на каком-то этапе будет переиспользование карточки, и в лог эта строка не попадет
        // а строка onBindViewHolder попадет, это будет означать, что старая карточка
        // переоткрыта с новыми данными
        Log.d("SocnetAdapter", "onCreateViewHolder");
        return vh;
    }

    // Заменить данные в пользовательском интерфейсе
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Получить элемент из источника данных (БД, интернет…)
        Soc item = dataSource.get(position);

        // Вывести на экран, используя ViewHolder
        holder.description.setText(item.getDescription());

        // отрабатывает при необходимости нарисовать карточку
        Log.d("SocnetAdapter", "onBindViewHolder");
    }

    // Вернуть размер данных
    @Override
    public int getItemCount() {
        return dataSource.size();
    }
}
