package com.example.denis.remembereverything;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class AddActivity_Definition extends Activity
{
    TextView inform;
    EditText term;

    EditText definition;
    ListView date;

    int hotFix = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_definition);

        //чем заполняется спиннер
        String[] data = {getString(R.string.choice_1), getString(R.string.choice_2), getString(R.string.choice_3)};

        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //найстройки спиннера
        Spinner spinner = (Spinner) findViewById(R.id.typeSpinner);
        spinner.setAdapter(adapter);
        spinner.setSelection(2);
        spinner.setOnItemSelectedListener(makeYourChoice);
    }

    //действие на выбор элемента
    AdapterView.OnItemSelectedListener makeYourChoice = new AdapterView.OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            switch (position)
            {
                case 0:
                {
                    //сменить активити на "Дату"
                    Intent intent = new Intent(AddActivity_Definition.this, AddActivity_Date.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                    break;
                }
                case 1:
                {
                    //сменить активити на "Перевод"
                    Intent intent = new Intent(AddActivity_Definition.this, AddActivity_Translate.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

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
