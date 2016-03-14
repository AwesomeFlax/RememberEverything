package com.example.denis.remembereverything;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AddActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //чем заполняется спиннер
        String[] data = {getString(R.string.choice_1), getString(R.string.choice_2), getString(R.string.choice_3)};

        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //найстройки спиннера
        Spinner spinner = (Spinner) findViewById(R.id.typeSpinner);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(makeYourChoice);
    }

        //действие на выбор элемента
        AdapterView.OnItemSelectedListener makeYourChoice = new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: {

                        break;
                    }
                    case 1: {

                        break;
                    }
                    case 2: {

                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        };
}
