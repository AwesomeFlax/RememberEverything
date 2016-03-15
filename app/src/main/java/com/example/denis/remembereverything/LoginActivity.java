package com.example.denis.remembereverything;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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


public class LoginActivity extends Activity
{
    EditText login;
    EditText password;
    boolean enterAllowed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (EditText) findViewById(R.id.loginEdit);
        password = (EditText) findViewById(R.id.passEdit);
    }

    //отдельный поток для проверки пары логин-пароль
    class task extends AsyncTask<String, String, Void>
    {
        private ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        InputStream is = null ;
        String result = "";


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
                is =  httpEntity.getContent();

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
                while((line = br.readLine()) != null)
                {
                    sb.append(line).append("\n");
                }
                is.close();
                result = sb.toString();

            }
            catch (Exception e)
            {
                // TODO: handle exception
                Log.e("log_tag", "Error parsing data "+e.toString());
            }

            return null;
        }

        protected void onPostExecute(Void v)
        {
            try
            {
                JSONArray Jarray = new JSONArray(result);
                for(int i = 0; i < Jarray.length(); i++)
                {
                    JSONObject Jasonobject;
                    Jasonobject = Jarray.getJSONObject(i);

                    //get an output on the screen
                    String name = Jasonobject.getString("login");
                    String db_detail;

                    if(login.getText().toString().equalsIgnoreCase(name))
                    {
                        String pass = password.getText().toString();
                        MD5 md5 = new MD5();
                        pass = md5.getHash(pass);

                        db_detail = Jasonobject.getString("password");

                        if (pass.equals(db_detail))
                            enterAllowed = true;

                        break;
                    }
                }
                this.progressDialog.dismiss();

            }
            catch (Exception e)
            {
                // TODO: handle exception
                Log.e("log_tag", "Error! "+e.toString());
            }
        }
    }

    public class MD5
    {
        public String getHash(String str)
        {
            MessageDigest md5;
            StringBuilder hexString = new StringBuilder();

            try
            {
                md5 = MessageDigest.getInstance("md5");

                md5.reset();
                md5.update(str.getBytes());

                byte messageDigest[] = md5.digest();

                for (byte aMessageDigest : messageDigest)
                    hexString.append(Integer.toHexString(0xFF & aMessageDigest));

            }
            catch (NoSuchAlgorithmException e)
            {
                return e.toString();
            }

            return hexString.toString();
        }
    }

    //кнопка "Вход"
    public void toLogin(View v)
    {
        new task().execute();

        if (enterAllowed)
        {
            Intent intent = new Intent(this, AddActivity.class);
            startActivity(intent);
        }
    }

    //кнопка "Регистрация"
    public void toRegister(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}