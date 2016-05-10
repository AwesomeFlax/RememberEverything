package com.example.denis.remembereverything;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CheckedInputStream;


public class LoginActivity extends Activity
{
    EditText login;
    EditText password;
    Intent intent;

    SharedPreferences sPref;
    CheckBox checkBox;

    boolean to_load = false;
    String user_data = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkBox = (CheckBox) findViewById(R.id.checkBox);
        login = (EditText) findViewById(R.id.loginEdit);
        password = (EditText) findViewById(R.id.passEdit);

        intent = new Intent(this, AddActivity_Date.class);

        //подгрузка shared preferences
        loadText();
    }

    //сохранение данных пользователя
    void saveText()
    {
        if (checkBox.isChecked())
        {
            sPref = getSharedPreferences("MyPref", MODE_PRIVATE);

            String temp = login.getText().toString() + ":" + password.getText().toString();

            SharedPreferences.Editor ed = sPref.edit();

            ed.putString("user_data", temp);

            if (checkBox.isChecked())
                ed.putBoolean("to_load", true);
            else
                ed.putBoolean("to_load", false);
            ed.apply();
        }
    }

    //загрузка данных пользователя
    void loadText()
    {
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);

        user_data = sPref.getString("user_data", ":");

        String[] temp = user_data.split(":");

        if (user_data.isEmpty())
        {
            temp[0] = "";
            temp[1] = "";
        }

        to_load = sPref.getBoolean("to_load", false);

        if (to_load)
        {
            checkBox.setChecked(true);
            login.setText(temp[0]);
            password.setText(temp[1]);
        }
    }


    //отдельный поток для проверки пары логин-пароль
    class task extends AsyncTask<String, String, Void>
    {
        InputStream is = null;
        String result = "";

        //получение данных (если я правильно понимаю)
        @Override
        protected Void doInBackground(String... params)
        {
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

            } catch (Exception e)
            {
                Log.e("log_tag", "Connection error " + e.toString());
            }
            try
            {
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null)
                {
                    sb.append(line).append("\n");
                }
                is.close();
                result = sb.toString();

            } catch (Exception e)
            {
                // TODO: handle exception
                Log.e("log_tag", "Error parsing data " + e.toString());
            }

            return null;
        }

        //обработка данных
        protected void onPostExecute(Void v)
        {
            try
            {
                JSONArray Jarray = new JSONArray(result);
                for (int i = 0; i < Jarray.length(); i++)
                {
                    JSONObject Jasonobject;
                    Jasonobject = Jarray.getJSONObject(i);

                    String name = Jasonobject.getString("login");
                    String db_detail;

                    if (login.getText().toString().equalsIgnoreCase(name))
                    {
                        String pass = password.getText().toString();
                        MD5 md5 = new MD5();
                        pass = md5.getHash(pass);

                        //login.setText(pass.toString());
                        db_detail = Jasonobject.getString("password");

                        if (pass.equals(db_detail))
                        {
                            saveText();
                            String msg = getResources().getString(R.string.success_login);
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                            intent.putExtra("name", login.getText().toString());
                            startActivity(intent);
                        }
                        break;
                    }
                }
            } catch (Exception e)
            {
                // TODO: handle exception
                Log.e("log_tag", "Error! " + e.toString());
            }
        }
    }

    //класс для MD5 хэширования пароля
    public class MD5
    {
        public String getHash(String str) throws NoSuchAlgorithmException
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());

            byte byteData[] = md.digest();

            //convert the byte to hex format
            StringBuilder hexString = new StringBuilder();
            for (byte aByteData : byteData)
            {
                String hex = Integer.toHexString(0xff & aByteData);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        }
    }

    //кнопка "Вход"
    public void toLogin(View v)
    {
        new task().execute();
    }

    //кнопка "Регистрация"
    public void toRegister(View v)
    {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}