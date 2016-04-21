package com.example.denis.remembereverything;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class AddActivity_Date extends Activity
{
    CheckBox period;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_date);

        //чем заполняется спиннер
        String[] data = {getString(R.string.choice_1), getString(R.string.choice_2), getString(R.string.choice_3)};

        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //найстройки спиннера
        Spinner spinner = (Spinner) findViewById(R.id.typeSpinner);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(makeYourChoice);

        period = (CheckBox) findViewById(R.id.period_box);
        period.setOnCheckedChangeListener(new myCheckBoxChnageClicker());
    }

    //действие на выбор элемента
    AdapterView.OnItemSelectedListener makeYourChoice = new AdapterView.OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            switch (position)
            {
                case 1:
                {
                    //сменить активити на "Перевод"
                    Intent intent = new Intent(AddActivity_Date.this, AddActivity_Translate.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                    break;
                }
                case 2:
                {
                    //сменить активити на "Определение"
                    Intent intent = new Intent(AddActivity_Date.this, AddActivity_Definition.class);
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

    class myCheckBoxChnageClicker implements CheckBox.OnCheckedChangeListener
    {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
        {
            if (isChecked)
            {
                EditText day = (EditText) findViewById(R.id.day_2);
                EditText month = (EditText) findViewById(R.id.month_2);
                EditText year = (EditText) findViewById(R.id.year_2);
                TextView sep_1 = (TextView) findViewById(R.id.sep_3);
                TextView sep_2 = (TextView) findViewById(R.id.sep_4);

                day.setVisibility(View.VISIBLE);
                month.setVisibility(View.VISIBLE);
                year.setVisibility(View.VISIBLE);
                sep_1.setVisibility(View.VISIBLE);
                sep_2.setVisibility(View.VISIBLE);
            } else
            {
                EditText day = (EditText) findViewById(R.id.day_2);
                EditText month = (EditText) findViewById(R.id.month_2);
                EditText year = (EditText) findViewById(R.id.year_2);
                TextView sep_1 = (TextView) findViewById(R.id.sep_3);
                TextView sep_2 = (TextView) findViewById(R.id.sep_4);

                day.setVisibility(View.INVISIBLE);
                month.setVisibility(View.INVISIBLE);
                year.setVisibility(View.INVISIBLE);
                sep_1.setVisibility(View.INVISIBLE);
                sep_2.setVisibility(View.INVISIBLE);
            }
        }
    }
}
