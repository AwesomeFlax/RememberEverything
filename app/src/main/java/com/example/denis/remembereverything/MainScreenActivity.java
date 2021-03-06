package com.example.denis.remembereverything;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainScreenActivity extends Activity
{
    String user_name;

    @Override
    protected void onResume()
    {
        super.onResume();
        ImageButton date = (ImageButton) findViewById(R.id.date);
        ImageButton note = (ImageButton) findViewById(R.id.note);
        ImageButton translate = (ImageButton) findViewById(R.id.translate);
        ImageButton gallery = (ImageButton) findViewById(R.id.gallery);
        ImageButton tests = (ImageButton) findViewById(R.id.test);


        tests.setBackground(getResources().getDrawable(R.drawable._test));
        gallery.setBackground(getResources().getDrawable(R.drawable._gallery));
        translate.setBackground(getResources().getDrawable(R.drawable._translate));
        note.setBackground(getResources().getDrawable(R.drawable._note));
        date.setBackground(getResources().getDrawable(R.drawable._callendar));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        Intent intent = getIntent();
        user_name = intent.getStringExtra("name");

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
                    ImageButton date = (ImageButton) findViewById(R.id.date);
                    date.setBackground(getResources().getDrawable(R.drawable.callendar));
                    Intent intent_date = new Intent(MainScreenActivity.this, AddActivity_Date.class);
                    intent_date.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent_date.putExtra("name", user_name);
                    startActivity(intent_date);

                    break;
                }

                case R.id.note:
                {
                    ImageButton note = (ImageButton) findViewById(R.id.note);
                    note.setBackground(getResources().getDrawable(R.drawable.note));
                    Intent intent_definition = new Intent(MainScreenActivity.this, AddActivity_Definition.class);
                    intent_definition.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent_definition.putExtra("name", user_name);
                    startActivity(intent_definition);

                    break;
                }

                case R.id.translate:
                {
                    ImageButton translate = (ImageButton) findViewById(R.id.translate);
                    translate.setBackground(getResources().getDrawable(R.drawable.translate));
                    Intent intent_translate = new Intent(MainScreenActivity.this, AddActivity_Translate.class);
                    intent_translate.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent_translate.putExtra("name", user_name);
                    startActivity(intent_translate);

                    break;
                }

                case R.id.gallery:
                {
                    ImageButton gallery = (ImageButton) findViewById(R.id.gallery);
                    gallery.setBackground(getResources().getDrawable(R.drawable.gallery));
                    Intent intent_gallery = new Intent(MainScreenActivity.this, Gallery.class);
                    intent_gallery.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent_gallery.putExtra("name", user_name);
                    startActivity(intent_gallery);

                    break;
                }

                case R.id.test:
                {
                    ImageButton test = (ImageButton) findViewById(R.id.test);
                    test.setBackground(getResources().getDrawable(R.drawable.test));
                    Intent intent_test = new Intent(MainScreenActivity.this, Tests.class);
                    intent_test.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent_test.putExtra("name", user_name);
                    startActivity(intent_test);

                    break;
                }
            }
        }
    }

}