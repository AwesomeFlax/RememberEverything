package com.example.denis.remembereverything;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.codec.binary.Base64;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class AddActivity_Translate extends Activity implements Serializable
{
    //для сериализации
    public byte version = 100;
    public byte count = 0;

    String user_name;
    String l_source = "";
    String l_target = "";
    String source;
    String target;

    TextView indicator;
    TextView _result;
    TextView indic;
    EditText myText;
    ImageButton mainButton;

    int id_source;
    int id_target;
    boolean toAddInDB = false;

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

        myText = (EditText) findViewById(R.id.wordText);
        _result = (TextView) findViewById(R.id.result);
        indicator = (TextView) findViewById(R.id.translate_info);
        indic = (TextView) findViewById(R.id.indic);
        mainButton = (ImageButton) findViewById(R.id.send);

        source = getResources().getString(R.string.language);
        target = getResources().getString(R.string._language);
        String temp = getResources().getString(R.string.from) + " " + source + " " + getResources().getString(R.string.to) + " " + target;

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

        //new task().execute();
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
                    l_source = "es";
                    id_source = 0;
                    toAddInDB = false;
                    indic.setText(getResources().getString(R.string.translate));
                    break;
                }
                case R.id.Ukraine:
                {
                    source = getResources().getString(R.string.ukrainian);
                    l_source = "uk";
                    id_source = 1;
                    toAddInDB = false;
                    indic.setText(getResources().getString(R.string.translate));
                    break;
                }
                case R.id.Italy:
                {
                    source = getResources().getString(R.string.italian);
                    l_source = "it";
                    id_source = 2;
                    toAddInDB = false;
                    indic.setText(getResources().getString(R.string.translate));
                    break;
                }
                case R.id.France:
                {
                    source = getResources().getString(R.string.french);
                    l_source = "fr";
                    id_source = 3;
                    toAddInDB = false;
                    indic.setText(getResources().getString(R.string.translate));
                    break;
                }
                case R.id.Germany:
                {
                    source = getResources().getString(R.string.german);
                    l_source = "de";
                    id_source = 4;
                    toAddInDB = false;
                    indic.setText(getResources().getString(R.string.translate));
                    break;
                }
                case R.id.Russia:
                {
                    source = getResources().getString(R.string.russian);
                    l_source = "ru";
                    id_source = 5;
                    toAddInDB = false;
                    indic.setText(getResources().getString(R.string.translate));
                    break;
                }
                case R.id.USA:
                {
                    source = getResources().getString(R.string.english);
                    l_source = "en";
                    id_source = 6;
                    toAddInDB = false;
                    indic.setText(getResources().getString(R.string.translate));
                    break;
                }
                case R.id.Poland:
                {
                    source = getResources().getString(R.string.polish);
                    l_source = "pl";
                    id_source = 7;
                    toAddInDB = false;
                    indic.setText(getResources().getString(R.string.translate));
                    break;
                }
                case R.id.Sweden:
                {
                    source = getResources().getString(R.string.swedish);
                    l_source = "sv";
                    id_source = 8;
                    toAddInDB = false;
                    indic.setText(getResources().getString(R.string.translate));
                    break;
                }
                case R.id.Czech:
                {
                    source = getResources().getString(R.string.czech);
                    l_source = "cs";
                    id_source = 9;
                    toAddInDB = false;
                    indic.setText(getResources().getString(R.string.translate));
                    break;
                }

                case R.id._Spain:
                {
                    target = getResources().getString(R.string._spanish);
                    l_target = "es";
                    id_target = 0;
                    toAddInDB = false;
                    indic.setText(getResources().getString(R.string.translate));
                    break;
                }
                case R.id._Ukraine:
                {
                    target = getResources().getString(R.string._ukrainian);
                    l_target = "uk";
                    id_target = 1;
                    toAddInDB = false;
                    indic.setText(getResources().getString(R.string.translate));
                    break;
                }
                case R.id._Italy:
                {
                    target = getResources().getString(R.string._italian);
                    l_target = "it";
                    id_target = 2;
                    toAddInDB = false;
                    indic.setText(getResources().getString(R.string.translate));
                    break;
                }
                case R.id._France:
                {
                    target = getResources().getString(R.string._french);
                    l_target = "fr";
                    id_target = 3;
                    toAddInDB = false;
                    indic.setText(getResources().getString(R.string.translate));
                    break;
                }
                case R.id._Germany:
                {
                    target = getResources().getString(R.string._german);
                    l_target = "de";
                    id_target = 4;
                    toAddInDB = false;
                    indic.setText(getResources().getString(R.string.translate));
                    break;
                }
                case R.id._Russia:
                {
                    target = getResources().getString(R.string._russian);
                    l_target = "ru";
                    id_target = 5;
                    toAddInDB = false;
                    indic.setText(getResources().getString(R.string.translate));
                    break;
                }
                case R.id._USA:
                {
                    target = getResources().getString(R.string._english);
                    l_target = "en";
                    id_target = 6;
                    toAddInDB = false;
                    indic.setText(getResources().getString(R.string.translate));
                    break;
                }
                case R.id._Poland:
                {
                    target = getResources().getString(R.string._polish);
                    l_target = "pl";
                    id_target = 7;
                    toAddInDB = false;
                    indic.setText(getResources().getString(R.string.translate));
                    break;
                }
                case R.id._Sweden:
                {
                    target = getResources().getString(R.string._swedish);
                    l_target = "sv";
                    id_target = 8;
                    toAddInDB = false;
                    indic.setText(getResources().getString(R.string.translate));
                    break;
                }
                case R.id._Czech:
                {
                    target = getResources().getString(R.string._czech);
                    l_target = "cs";
                    id_target = 9;
                    toAddInDB = false;
                    indic.setText(getResources().getString(R.string.translate));
                    break;
                }
            }

            String temp = getResources().getString(R.string.from) + " " + source + " " + getResources().getString(R.string.to) + " " + target;
            indicator.setText(temp);
        }
    }

    //отдельный поток для проверки пары логин-пароль
    class task extends AsyncTask<String, String, Void>
    {
        InputStream is = null;
        String result = "";
        String temp = myText.getText().toString();
        String _temp = reformatText(temp);

        String reformatText(String _temp)
        {
            String convertedString = "";
            if (_temp.contains(" "))
            {
                String[] array = _temp.split(" ");

                for (String anArray : array)
                    convertedString = convertedString + anArray + "%20";
            }
            else
            {
                convertedString = _temp;
            }

            return convertedString;
        }

        //получение данных (если я правильно понимаю)
        @Override
        protected Void doInBackground(String... params)
        {

            String server_and_key = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20160515T212140Z.5a05f8d69526a13a.c2f585124910cd649f36911e1422f10d8e7b1c40";
            String phrase = "&text=" + _temp;
            String lang = "&lang=" + l_source + "-" + l_target;
            String url_select = server_and_key + phrase + lang;


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
                {
                    sb.append(line).append("\n");
                }
                is.close();

                result = sb.toString();

            }
            catch (Exception e)
            {
                // TODO: handle exception
                Log.e("log_tag", "Error parsing data " + e.toString());
            }

            return null;
        }

        //обработка данных
        protected void onPostExecute(Void v)
        {
            JSONObject dataJsonObj = null;
            try
            {
                String temp;
                dataJsonObj = new JSONObject(result);
                JSONArray text = dataJsonObj.getJSONArray("text");
                temp = text.getString(0);

                _result.setText(temp);
                toAddInDB = true;
                indic.setText(getResources().getString(R.string.add_to_DB));

            }
            catch (Exception e)
            {
                // TODO: handle exception
                Log.e("log_tag", "Error! " + e.toString());
            }
        }
    }

    public void SaveInDB()
    {
        InputStream is = null;

        List<NameValuePair> nameValuePairs = new ArrayList<>(1);
        nameValuePairs.add(new BasicNameValuePair("user", user_name));
        nameValuePairs.add(new BasicNameValuePair("word_original", toBase64(myText.getText().toString())));
        nameValuePairs.add(new BasicNameValuePair("word_translate", toBase64(_result.getText().toString())));
        nameValuePairs.add(new BasicNameValuePair("lang_original", String.valueOf(id_source)));
        nameValuePairs.add(new BasicNameValuePair("lang_translate", String.valueOf(id_target)));

        try
        {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://remember-everything.ml/connections/add_translate.php");
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();

            String msg = getResources().getString(R.string.translate_add);
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void onWordChange(View v)
    {
        toAddInDB = false;
        indic.setText(getResources().getString(R.string.translate));
    }

    public String toBase64(String data)
    {
        byte[] encodedBytes = Base64.encodeBase64(data.getBytes());
        return new String(encodedBytes);
    }

    //кнопка "Вход"
    public void toTranslate(View v)
    {
        if (!toAddInDB)
        {
            new task().execute();
        }
        else
        {
            SaveInDB();
            toAddInDB = false;
            indic.setText(getResources().getString(R.string.translate));
        }
    }
}