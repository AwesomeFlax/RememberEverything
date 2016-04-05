package com.example.denis.remembereverything;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class AddActivity extends Activity
{
    TextView inform;
    EditText term;

    EditText definition;
    DatePicker date;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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

    void hideMaterials()
    {
        inform = (TextView) findViewById(R.id.wordView);
        term = (EditText) findViewById(R.id.wordText);
        definition = (EditText) findViewById(R.id.defText);
        date = (DatePicker) findViewById(R.id.datePicker);

        inform.setVisibility(View.INVISIBLE);
        term.setVisibility(View.INVISIBLE);
        definition.setVisibility(View.INVISIBLE);
        date.setVisibility(View.INVISIBLE);
    }

    //действие на выбор элемента
    AdapterView.OnItemSelectedListener makeYourChoice = new AdapterView.OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            switch (position)
            {
                case 2:
                {
                    hideMaterials();

                    inform = (TextView) findViewById(R.id.wordView);
                    term = (EditText) findViewById(R.id.wordText);
                    definition = (EditText) findViewById(R.id.defText);

                    inform.setVisibility(View.VISIBLE);
                    term.setVisibility(View.VISIBLE);
                    definition.setVisibility(View.VISIBLE);

                    break;
                }
                case 0:
                {
                    hideMaterials();

                    inform = (TextView) findViewById(R.id.wordView);
                    term = (EditText) findViewById(R.id.wordText);
                    date = (DatePicker) findViewById(R.id.datePicker);
                    inform.setText(getResources().getString(R.string.date));

                    inform.setVisibility(View.VISIBLE);
                    term.setVisibility(View.VISIBLE);
                    date.setVisibility(View.VISIBLE);

                    break;
                }
                case 1:
                {
                    hideMaterials();

                    break;
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {
        }
    };
}
