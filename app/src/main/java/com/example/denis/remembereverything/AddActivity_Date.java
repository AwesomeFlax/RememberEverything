package com.example.denis.remembereverything;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddActivity_Date extends Activity
{
    //объявление 'лементов на экране
    CheckBox period;
    Button send;
    EditText wordText;

    //даты
    EditText day_1;
    EditText day_2;

    EditText month_1;
    EditText month_2;

    EditText year_1;
    EditText year_2;

    boolean singleDate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_date);

        //чтобы был доступ к сети
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

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

        //объекты
        period = (CheckBox) findViewById(R.id.period_box);
        period.setOnCheckedChangeListener(new myCheckBoxChangeClicker());
        send = (Button) findViewById(R.id.send_data);
        wordText = (EditText) findViewById(R.id.wordText);

        //даты
        day_1 = (EditText) findViewById(R.id.day);
        day_2 = (EditText) findViewById(R.id.day_2);

        month_1 = (EditText) findViewById(R.id.month);
        month_2 = (EditText) findViewById(R.id.month_2);

        year_1 = (EditText) findViewById(R.id.year);
        year_2 = (EditText) findViewById(R.id.year_2);

        send.setOnClickListener(new View.OnClickListener()
        {
            InputStream is = null;

            String term;
            String period;
            String name;

            //говнокод
            String _day1;
            String _day2;

            String _month1;
            String _month2;

            String _year1;
            String _year2;

            @Override
            public void onClick(View arg0)
            {
                term = "" + wordText.getText().toString();

                _day1 = "" + day_1.getText().toString() + "";
                _month1 = "" + month_1.getText().toString() + "";
                _year1 = "" + year_1.getText().toString() + "";

                if (singleDate)
                {
                    period = "0";

                    _day2 = "" + day_1.getText().toString() + "";
                    _month2 = "" + month_1.getText().toString() + "";
                    _year2 = "" + year_1.getText().toString() + "";
                } else
                {
                    period = "1";

                    _day2 = "" + day_2.getText().toString() + "";
                    _month2 = "" + month_2.getText().toString() + "";
                    _year2 = "" + year_2.getText().toString() + "";
                }

                //получение имени
                Intent intent = getIntent();
                name = intent.getStringExtra("name");

                List<NameValuePair> nameValuePairs = new ArrayList<>(1);

                nameValuePairs.add(new BasicNameValuePair("term", term));
                nameValuePairs.add(new BasicNameValuePair("name", name));

                nameValuePairs.add(new BasicNameValuePair("day_1", _day1));
                nameValuePairs.add(new BasicNameValuePair("month_1", _month1));
                nameValuePairs.add(new BasicNameValuePair("year_1", _year1));

                nameValuePairs.add(new BasicNameValuePair("day_2", _day2));
                nameValuePairs.add(new BasicNameValuePair("month_2", _month2));
                nameValuePairs.add(new BasicNameValuePair("year_2", _year2));

                nameValuePairs.add(new BasicNameValuePair("period", period));

                try
                {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://remember-everything.ml/connections/add_date.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();

                    String msg = getResources().getString(R.string.date_add);
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
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

    class myCheckBoxChangeClicker implements CheckBox.OnCheckedChangeListener
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

                singleDate = false;
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

                singleDate = true;
            }
        }
    }
}
