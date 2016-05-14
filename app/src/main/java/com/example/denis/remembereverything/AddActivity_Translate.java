package com.example.denis.remembereverything;

import android.app.Activity;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;

public class AddActivity_Translate extends Activity
{
    String user_name;

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
    }

    public void getBack(View v)
    {
        Intent intent;
        intent = new Intent(this, MainScreenActivity.class);
        intent.putExtra("name", user_name);
        startActivity(intent);
    }
}
