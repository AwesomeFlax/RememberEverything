package com.example.denis.remembereverything;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

public class RegisterActivity extends Activity
{
    EditText login;
    EditText password_1;
    EditText password_2;
    ImageButton register;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        login = (EditText) findViewById(R.id.loginEdit);
        password_1 = (EditText) findViewById(R.id.passEdit);
        password_2 = (EditText) findViewById(R.id.passEdit2);
        register = (ImageButton) findViewById(R.id.registerButton);

        register.setOnClickListener(new View.OnClickListener()
        {
            InputStream is = null;

            @Override
            public void onClick(View arg0)
            {
                String name = "" + login.getText().toString();
                String pass = "" + password_1.getText().toString();
                String conf = "" + password_2.getText().toString();

                boolean used_name = checkNameAvailable(name);

                if (pass.equals(conf) && !used_name)
                {
                    List<NameValuePair> nameValuePairs = new ArrayList<>(1);
                    nameValuePairs.add(new BasicNameValuePair("name", name));
                    nameValuePairs.add(new BasicNameValuePair("pass", pass));

                    try
                    {
                        HttpClient httpClient = new DefaultHttpClient();
                        HttpPost httpPost = new HttpPost("http://remember-everything.ml/connections/registration.php");
                        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                        HttpResponse response = httpClient.execute(httpPost);
                        HttpEntity entity = response.getEntity();
                        is = entity.getContent();

                        String msg = getResources().getString(R.string.reg_success);
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    if (used_name)
                    {
                        String msg = getResources().getString(R.string.used_name);
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        String msg = getResources().getString(R.string.reg_alert);
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public boolean checkNameAvailable(String user)
    {
        InputStream is = null;
        String result = "";
        boolean matches = false;

        String url_select = "http://remember-everything.ml/connections/login.php";

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url_select);

        ArrayList<NameValuePair> param = new ArrayList<>();

        try
        {
            httpPost.setEntity(new UrlEncodedFormEntity(param));

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();

            //read content
            is = httpEntity.getContent();
        }
        catch (Exception e)
        {
            Log.e("log_tag", "Connection error " + e.toString());
        }
        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null)
                sb.append(line).append("\n");

            is.close();
            result = sb.toString();

        }
        catch (Exception e)
        {
            // TODO: handle exception
            Log.e("log_tag", "Error parsing data " + e.toString());
        }

        try
        {
            JSONArray Jarray = new JSONArray(result);
            for (int i = 0; i < Jarray.length(); i++)
            {
                JSONObject Jasonobject;
                Jasonobject = Jarray.getJSONObject(i);

                String name = Jasonobject.getString("login");

                if (name.equals(user))
                {
                    matches = true;
                }
            }
        }
        catch (Exception e)
        {
            // TODO: handle exception
            Log.e("log_tag", "Error! " + e.toString());
        }

        return matches;
    }
}
