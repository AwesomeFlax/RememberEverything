package com.example.denis.remembereverything;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class AddActivity_Translate extends Activity
{
    String user_name;
    boolean toAddInDB = false;
    Button mainButton;
    TextView indicator;

    String source;
    String target;

    //суперпрекрасное сколичество ФЛАГОВ
    ImageButton Spain;
    ImageButton Czech;
    ImageButton France;
    ImageButton Germany;
    ImageButton Russia;
    ImageButton Italy;
    ImageButton Sweden;
    ImageButton Ukraine;
    ImageButton Poland;
    ImageButton USA;

    ImageButton _Spain;
    ImageButton _Czech;
    ImageButton _France;
    ImageButton _Germany;
    ImageButton _Russia;
    ImageButton _Italy;
    ImageButton _Sweden;
    ImageButton _Ukraine;
    ImageButton _Poland;
    ImageButton _USA;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_translate);

        Intent intent_prew = getIntent();
        user_name = intent_prew.getStringExtra("name");

        //чтобы был доступ к сети
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        source = getResources().getString(R.string.language);
        target = getResources().getString(R.string._language);
        String temp = getResources().getString(R.string.from) + " " + source + " " + getResources().getString(R.string.to) + " " + target;
        indicator = (TextView) findViewById(R.id.translate_info);
        indicator.setText(temp);

        //ловим эти замечательные кнопки
        Spain = (ImageButton) findViewById(R.id.Spain);
        Czech = (ImageButton) findViewById(R.id.Czech);
        France = (ImageButton) findViewById(R.id.France);
        Germany = (ImageButton) findViewById(R.id.Germany);
        Russia = (ImageButton) findViewById(R.id.Russia);
        Italy = (ImageButton) findViewById(R.id.Italy);
        Sweden = (ImageButton) findViewById(R.id.Sweden);
        Ukraine = (ImageButton) findViewById(R.id.Ukraine);
        Poland = (ImageButton) findViewById(R.id.Poland);
        USA = (ImageButton) findViewById(R.id.USA);

        _Spain = (ImageButton) findViewById(R.id._Spain);
        _Czech = (ImageButton) findViewById(R.id._Czech);
        _France = (ImageButton) findViewById(R.id._France);
        _Germany = (ImageButton) findViewById(R.id._Germany);
        _Russia = (ImageButton) findViewById(R.id._Russia);
        _Italy = (ImageButton) findViewById(R.id._Italy);
        _Sweden = (ImageButton) findViewById(R.id._Sweden);
        _Ukraine = (ImageButton) findViewById(R.id._Ukraine);
        _Poland = (ImageButton) findViewById(R.id._Poland);
        _USA = (ImageButton) findViewById(R.id._USA);

        //присваиваем обработчик
        Spain.setOnClickListener(new CountryListener());
        Czech.setOnClickListener(new CountryListener());
        France.setOnClickListener(new CountryListener());
        Germany.setOnClickListener(new CountryListener());
        Russia.setOnClickListener(new CountryListener());
        Italy.setOnClickListener(new CountryListener());
        Sweden.setOnClickListener(new CountryListener());
        Ukraine.setOnClickListener(new CountryListener());
        Poland.setOnClickListener(new CountryListener());
        USA.setOnClickListener(new CountryListener());

        _Spain.setOnClickListener(new CountryListener());
        _Czech.setOnClickListener(new CountryListener());
        _France.setOnClickListener(new CountryListener());
        _Germany.setOnClickListener(new CountryListener());
        _Russia.setOnClickListener(new CountryListener());
        _Italy.setOnClickListener(new CountryListener());
        _Sweden.setOnClickListener(new CountryListener());
        _Ukraine.setOnClickListener(new CountryListener());
        _Poland.setOnClickListener(new CountryListener());
        _USA.setOnClickListener(new CountryListener());
    }

    public void getBack(View v)
    {
        Intent intent;
        intent = new Intent(this, MainScreenActivity.class);
        intent.putExtra("name", user_name);
        startActivity(intent);
    }

    //формирование интерактивного поля
    public class CountryListener implements View.OnClickListener
    {
        @Override
        public void onClick(final View v)
        {
            switch (v.getId())
            {
                case R.id.Spain:
                {
                    source = getResources().getString(R.string.spanish);
                    break;
                }
                case R.id.Ukraine:
                {
                    source = getResources().getString(R.string.ukrainian);
                    break;
                }
                case R.id.Italy:
                {
                    source = getResources().getString(R.string.italian);
                    break;
                }
                case R.id.France:
                {
                    source = getResources().getString(R.string.french);
                    break;
                }
                case R.id.Germany:
                {
                    source = getResources().getString(R.string.german);
                    break;
                }
                case R.id.Russia:
                {
                    source = getResources().getString(R.string.russian);
                    break;
                }
                case R.id.USA:
                {
                    source = getResources().getString(R.string.english);
                    break;
                }
                case R.id.Poland:
                {
                    source = getResources().getString(R.string.polish);
                    break;
                }
                case R.id.Sweden:
                {
                    source = getResources().getString(R.string.swedish);
                    break;
                }
                case R.id.Czech:
                {
                    source = getResources().getString(R.string.czech);
                    break;
                }

                case R.id._Spain:
                {
                    target = getResources().getString(R.string._spanish);
                    break;
                }
                case R.id._Ukraine:
                {
                    target = getResources().getString(R.string._ukrainian);
                    break;
                }
                case R.id._Italy:
                {
                    target = getResources().getString(R.string._italian);
                    break;
                }
                case R.id._France:
                {
                    target = getResources().getString(R.string._french);
                    break;
                }
                case R.id._Germany:
                {
                    target = getResources().getString(R.string._german);
                    break;
                }
                case R.id._Russia:
                {
                    target = getResources().getString(R.string._russian);
                    break;
                }
                case R.id._USA:
                {
                    target = getResources().getString(R.string._english);
                    break;
                }
                case R.id._Poland:
                {
                    target = getResources().getString(R.string._polish);
                    break;
                }
                case R.id._Sweden:
                {
                    target = getResources().getString(R.string._swedish);
                    break;
                }
                case R.id._Czech:
                {
                    target = getResources().getString(R.string._czech);
                    break;
                }
            }

            String temp = getResources().getString(R.string.from) + " " + source + " " + getResources().getString(R.string.to) + " " + target;
            indicator.setText(temp);
        }
    }
}