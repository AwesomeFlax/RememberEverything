package com.example.denis.remembereverything;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TextView;

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

public class Gallery extends Activity
{
    //больше полей богу полей!
    String user_name;
    int _INT_definition_counter = 0;

    TextView definition_counter;
    TextView title;
    TextView content;

    ImageButton next;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        Intent intent = getIntent();
        user_name = intent.getStringExtra("name");

        definition_counter = (TextView) findViewById(R.id.definition_counter);
        title = (TextView) findViewById(R.id.title);
        content = (TextView) findViewById(R.id.content);

        //кнопки первого экрана
        next = (ImageButton) findViewById(R.id.next);
        back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(new CounterInfo());
        next.setOnClickListener(new CounterInfo());


        //вкладочки
        TabHost tabs = (TabHost) findViewById(android.R.id.tabhost);
        tabs.setup();
        TabHost.TabSpec spec;

        //прогрузка первых записей
        new getDefinitions().execute();

        //их настройка
        spec = tabs.newTabSpec("tag2");
        spec.setContent(R.id.tab2);
        spec.setIndicator(getResources().getString(R.string.choice_1));
        tabs.addTab(spec);

        spec = tabs.newTabSpec("tag1");
        spec.setContent(R.id.tab1);
        spec.setIndicator(getResources().getString(R.string.choice_2));
        tabs.addTab(spec);

        spec = tabs.newTabSpec("tag3");
        spec.setContent(R.id.tab3);
        spec.setIndicator(getResources().getString(R.string.choice_3));
        tabs.addTab(spec);

        tabs.setCurrentTab(0);
    }

    //счетчик, показывающий, какую запись показывать
    public class CounterInfo implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.next:
                {
                    _INT_definition_counter++;
                    new getDefinitions().execute();
                    break;
                }
            }

            switch (v.getId())
            {
                case R.id.back:
                {
                    if (_INT_definition_counter > 0)
                    {
                        _INT_definition_counter--;
                        new getDefinitions().execute();
                        break;
                    }
                }
            }

            definition_counter.setText(String.valueOf(_INT_definition_counter + 1));
        }
    }

    //отдельный поток для проверки пары логин-пароль
    class getDefinitions extends AsyncTask<String, String, Void>
    {
        InputStream is = null;
        String result = "";

        //получение данных (если я правильно понимаю)
        @Override
        protected Void doInBackground(String... params)
        {
            String url_select = "http://remember-everything.ml/connections/get_definitions.php";

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
            int local_counter = 0;

            try
            {
                JSONArray Jarray = new JSONArray(result);
                for (int i = 0; i < Jarray.length(); i++)
                {
                    JSONObject Jasonobject;
                    Jasonobject = Jarray.getJSONObject(i);
                    String name = Jasonobject.getString("user");

                    if (user_name.equalsIgnoreCase(name))
                    {
                        if (local_counter == _INT_definition_counter)
                        {
                            title.setText(Jasonobject.getString("term"));
                            content.setText(Jasonobject.getString("definition"));
                        }

                        local_counter++;
                    }
                }
            } catch (Exception e)
            {
                // TODO: handle exception
                Log.e("log_tag", "Error! " + e.toString());
            }
        }
    }
}
