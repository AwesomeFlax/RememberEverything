package com.example.denis.remembereverything;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainScreenActivity extends Activity
{
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        Intent intent = getIntent();
        username = intent.getStringExtra("name");

        //4 кнопки на этом бренном экране
        Button date = (Button) findViewById(R.id.date);
        Button note = (Button) findViewById(R.id.note);
        Button translate = (Button) findViewById(R.id.translate);
        Button galery = (Button) findViewById(R.id.galery);

        //обработчик на них
        translate.setOnClickListener(new CustomClickListener());
        note.setOnClickListener(new CustomClickListener());
        date.setOnClickListener(new CustomClickListener());
        galery.setOnClickListener(new CustomClickListener());
    }

    //реализация обработчика
    public class CustomClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(final View v)
        {
            switch (v.getId())
            {
                case R.id.date:
                {
                    Intent intent_date = new Intent(MainScreenActivity.this, AddActivity_Date.class);
                    intent_date.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent_date.putExtra("name", username);
                    startActivity(intent_date);

                    break;
                }

                case R.id.note:
                {
                    Intent intent_definition = new Intent(MainScreenActivity.this, AddActivity_Definition.class);
                    intent_definition.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent_definition.putExtra("name", username);
                    startActivity(intent_definition);

                    break;
                }

                case R.id.translate:
                {
                    Intent intent_translate = new Intent(MainScreenActivity.this, AddActivity_Translate.class);
                    intent_translate.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent_translate.putExtra("name", username);
                    startActivity(intent_translate);

                    break;
                }
            }
        }
    }

}