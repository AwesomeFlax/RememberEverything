package com.example.denis.remembereverything;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

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
        ImageButton date = (ImageButton) findViewById(R.id.date);
        ImageButton note = (ImageButton) findViewById(R.id.note);
        ImageButton translate = (ImageButton) findViewById(R.id.translate);
        ImageButton gallery = (ImageButton) findViewById(R.id.gallery);
        ImageButton tests = (ImageButton) findViewById(R.id.test);

        //обработчик на них
        translate.setOnClickListener(new CustomClickListener());
        note.setOnClickListener(new CustomClickListener());
        date.setOnClickListener(new CustomClickListener());

        gallery.setOnClickListener(new CustomClickListener());
        tests.setOnClickListener(new CustomClickListener());
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
                    intent_date.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent_date.putExtra("name", username);
                    startActivity(intent_date);

                    break;
                }

                case R.id.note:
                {
                    Intent intent_definition = new Intent(MainScreenActivity.this, AddActivity_Definition.class);
                    intent_definition.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent_definition.putExtra("name", username);
                    startActivity(intent_definition);

                    break;
                }

                case R.id.translate:
                {
                    Intent intent_translate = new Intent(MainScreenActivity.this, AddActivity_Translate.class);
                    intent_translate.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent_translate.putExtra("name", username);
                    startActivity(intent_translate);

                    break;
                }

                case R.id.gallery:
                {
                    Intent intent_gallery = new Intent(MainScreenActivity.this, Gallery.class);
                    intent_gallery.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent_gallery.putExtra("name", username);
                    startActivity(intent_gallery);

                    break;
                }
            }
        }
    }

}