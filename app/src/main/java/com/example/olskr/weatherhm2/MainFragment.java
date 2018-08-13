package com.example.olskr.weatherhm2;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

// Главный фрагмент
public class MainFragment extends Fragment {

    private Publisher publisher; // Обработчик подписок
    String i;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        publisher = ((PublishGetter) context).getPublisher(); // получим обработчика подписок
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        final EditText textView = view.findViewById(R.id.editTextMainF);

//        savedInstanceState = this.getArguments();
//        if (savedInstanceState != null) {
//           i = savedInstanceState.getString("ntcn");
//        }

        Button button = view.findViewById(R.id.buttonMainF); // по этой кнопке будем отправлять события
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = textView.getText().toString();
                publisher.notify(text); // Отправить изменившуюся строку
            }
        });
        return view;
    }
}
