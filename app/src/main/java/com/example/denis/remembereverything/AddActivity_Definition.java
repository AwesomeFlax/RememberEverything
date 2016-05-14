package com.example.denis.remembereverything;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class AddActivity_Definition extends Activity
{
    String user_name;
    EditText term;
    EditText definition;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_definition);

        Intent intent_prew = getIntent();
        user_name = intent_prew.getStringExtra("name");

        //чтобы был доступ к сети
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        term = (EditText) findViewById(R.id.wordText);
        definition = (EditText) findViewById(R.id.defText);
        send = (Button) findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener()
        {
            InputStream is = null;

            String _term;
            String _definition;

            @Override
            public void onClick(View arg0)
            {
                _term = term.getText().toString();
                _definition = definition.getText().toString();

                List<NameValuePair> nameValuePairs = new ArrayList<>(1);

                nameValuePairs.add(new BasicNameValuePair("term", _term));
                nameValuePairs.add(new BasicNameValuePair("name", user_name));
                nameValuePairs.add(new BasicNameValuePair("definition", _definition));

                try
                {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://remember-everything.ml/connections/add_def.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();

                    String msg = getResources().getString(R.string.def_add);
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getBack(View v)
    {
        Intent intent;
        intent = new Intent(this, MainScreenActivity.class);
        intent.putExtra("name", user_name);
        startActivity(intent);
    }
}
