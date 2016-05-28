package com.example.denis.remembereverything;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import org.apache.commons.codec.binary.Base64;
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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Gallery extends Activity
{
    //больше полей богу полей!
    String user_name;
    int _INT_definition_counter = 0;
    int _INT_date_counter = 0;
    int _INT_translate_counter = 0;

    int date_cells;
    int translate_cells;
    int definition_cells;

    //поля для определений
    TextView definition_counter;
    TextView definition_title;
    TextView definition_content;

    ImageButton definition_next;
    ImageButton definition_back;

    //поля для дат
    TextView date_counter;
    TextView date_title;
    TextView date_content_1;
    TextView date_content_2;

    ImageButton date_next;
    ImageButton date_back;

    //поля для перевода
    TextView translate_counter;
    TextView translate_title;
    TextView translate_content_1;
    ImageView flag_1;
    ImageView flag_2;

    ImageButton translate_next;
    ImageButton translate_back;

    Object[] flags = new Object[10];
    ImageView[] cells_array = new ImageView[12];


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        Intent intent = getIntent();
        user_name = intent.getStringExtra("name");

        //разметка для определений
        definition_counter = (TextView) findViewById(R.id.definition_counter);
        definition_title = (TextView) findViewById(R.id.definition_title);
        definition_content = (TextView) findViewById(R.id.definition_content);

        //разметка для дат
        date_counter = (TextView) findViewById(R.id.date_counter);
        date_title = (TextView) findViewById(R.id.date_title);
        date_content_1 = (TextView) findViewById(R.id.date_content_1);
        date_content_2 = (TextView) findViewById(R.id.date_content_2);

        //разметка для перевода
        translate_counter = (TextView) findViewById(R.id.translate_counter);
        translate_title = (TextView) findViewById(R.id.translate_title);
        translate_content_1 = (TextView) findViewById(R.id.translate_content_1);
        flag_1 = (ImageView) findViewById(R.id.flag_1);
        flag_2 = (ImageView) findViewById(R.id.flag_2);

        //кнопки первого экрана
        definition_next = (ImageButton) findViewById(R.id.definition_next);
        definition_back = (ImageButton) findViewById(R.id.definition_back);
        definition_back.setOnClickListener(new CounterInfo());
        definition_next.setOnClickListener(new CounterInfo());

        //кнопки второго экрана
        date_next = (ImageButton) findViewById(R.id.date_next);
        date_back = (ImageButton) findViewById(R.id.date_back);
        date_back.setOnClickListener(new CounterInfo());
        date_next.setOnClickListener(new CounterInfo());

        //кнопки третьего экрана
        translate_next = (ImageButton) findViewById(R.id.translate_next);
        translate_back = (ImageButton) findViewById(R.id.translate_back);
        translate_back.setOnClickListener(new CounterInfo());
        translate_next.setOnClickListener(new CounterInfo());

        flags[0] = R.drawable.spain;
        flags[1] = R.drawable.ukraine;
        flags[2] = R.drawable.italy;
        flags[3] = R.drawable.france;
        flags[4] = R.drawable.germany;
        flags[5] = R.drawable.russia;
        flags[6] = R.drawable.unitedstates;
        flags[7] = R.drawable.poland;
        flags[8] = R.drawable.sweden;
        flags[9] = R.drawable.czechrepublic;

        cells_array[0] = (ImageView) findViewById(R.id.cell_1);
        cells_array[1] = (ImageView) findViewById(R.id.cell_2);
        cells_array[2] = (ImageView) findViewById(R.id.cell_3);
        cells_array[3] = (ImageView) findViewById(R.id.cell_4);
        cells_array[4] = (ImageView) findViewById(R.id.cell_5);
        cells_array[5] = (ImageView) findViewById(R.id.cell_6);
        cells_array[6] = (ImageView) findViewById(R.id.cell_7);
        cells_array[7] = (ImageView) findViewById(R.id.cell_8);
        cells_array[8] = (ImageView) findViewById(R.id.cell_9);
        cells_array[9] = (ImageView) findViewById(R.id.cell_10);
        cells_array[10] = (ImageView) findViewById(R.id.cell_11);
        cells_array[11] = (ImageView) findViewById(R.id.cell_12);

        //вкладочки
        TabHost tabs = (TabHost) findViewById(android.R.id.tabhost);
        tabs.setup();
        TabHost.TabSpec spec;

        //их настройка
        spec = tabs.newTabSpec("tag1");
        spec.setContent(R.id.tab1);
        spec.setIndicator(getResources().getString(R.string.choice_3));
        tabs.addTab(spec);

        spec = tabs.newTabSpec("tag2");
        spec.setContent(R.id.tab2);
        spec.setIndicator(getResources().getString(R.string.choice_1));
        tabs.addTab(spec);

        spec = tabs.newTabSpec("tag3");
        spec.setContent(R.id.tab3);
        spec.setIndicator(getResources().getString(R.string.choice_2));
        tabs.addTab(spec);

        tabs.setCurrentTab(0);

        //инициализация первых записей
        new getDefinitions().execute();
        new getDates().execute();
        new getTranslates().execute();
    }

    //счетчик, показывающий, какую запись показывать
    public class CounterInfo implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                //определения
                case R.id.definition_next:
                {
                    _INT_definition_counter++;
                    new getDefinitions().execute();
                    definition_counter.setText(String.valueOf(_INT_definition_counter + 1));
                    break;
                }

                //определения
                case R.id.definition_back:
                {
                    if (_INT_definition_counter > 0)
                    {
                        _INT_definition_counter--;
                        new getDefinitions().execute();
                        definition_counter.setText(String.valueOf(_INT_definition_counter + 1));
                    }
                    break;
                }

                //дата
                case R.id.date_next:
                {
                    _INT_date_counter++;
                    new getDates().execute();
                    date_counter.setText(String.valueOf(_INT_date_counter + 1));
                    break;
                }

                //дата
                case R.id.date_back:
                {
                    if (_INT_date_counter > 0)
                    {
                        _INT_date_counter--;
                        new getDates().execute();
                        date_counter.setText(String.valueOf(_INT_date_counter + 1));
                    }
                    break;
                }

                //перевод
                case R.id.translate_next:
                {
                    _INT_translate_counter++;
                    new getTranslates().execute();
                    translate_counter.setText(String.valueOf(_INT_translate_counter + 1));
                    break;
                }

                //перевод
                case R.id.translate_back:
                {
                    if (_INT_translate_counter > 0)
                    {
                        _INT_translate_counter--;
                        new getTranslates().execute();
                        translate_counter.setText(String.valueOf(_INT_translate_counter + 1));
                    }
                    break;
                }
            }
        }
    }

    //отдельный поток для определений
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
                            definition_title.setText(fromBase64(Jasonobject.getString("term")));
                            definition_content.setText(fromBase64(Jasonobject.getString("definition")));
                            definition_cells = Integer.valueOf(Jasonobject.getString("check_"));
                            fillAllTheCells();
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

    //отдельный поток для дат
    class getDates extends AsyncTask<String, String, Void>
    {
        InputStream is = null;
        String result = "";

        //получение данных (если я правильно понимаю)
        @Override
        protected Void doInBackground(String... params)
        {
            String url_select = "http://remember-everything.ml/connections/get_dates.php";

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
                        if (local_counter == _INT_date_counter)
                        {
                            date_title.setText(fromBase64(Jasonobject.getString("term")));
                            date_content_1.setText(Jasonobject.getString("date_1"));
                            date_cells = Integer.valueOf(Jasonobject.getString("check_"));
                            fillAllTheCells();

                            if (Jasonobject.getString("period").equals("1"))
                            {
                                date_content_2.setVisibility(View.VISIBLE);
                                date_content_2.setText(Jasonobject.getString("date_2"));
                            } else
                            {
                                date_content_2.setVisibility(View.INVISIBLE);
                            }
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

    //отдельный поток для переводов
    class getTranslates extends AsyncTask<String, String, Void>
    {
        InputStream is = null;
        String result = "";

        //получение данных (если я правильно понимаю)
        @Override
        protected Void doInBackground(String... params)
        {
            String url_select = "http://remember-everything.ml/connections/get_translates.php";

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
                        if (local_counter == _INT_translate_counter)
                        {
                            translate_title.setText(fromBase64(Jasonobject.getString("word_original")));
                            translate_content_1.setText(fromBase64(Jasonobject.getString("word_translate")));

                            String index_1 = Jasonobject.getString("lang_original");
                            String index_2 = Jasonobject.getString("lang_translate");

                            flag_1.setBackground(getResources().getDrawable((Integer) flags[Integer.valueOf(index_1)]));
                            flag_2.setBackground(getResources().getDrawable((Integer) flags[Integer.valueOf(index_2)]));
                            translate_cells = Integer.valueOf(Jasonobject.getString("check_"));
                            fillAllTheCells();
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

    public void fillAllTheCells()
    {
        for (int i = 0; i < 12; i++)
            cells_array[i].setBackground(getResources().getDrawable(R.drawable.cell_empty));

        for (int k = 1; k <= definition_cells; k++)
        {
            if (k == 1)
                cells_array[8].setBackground(getResources().getDrawable(R.drawable.cell_full));
            if (k == 2)
                cells_array[9].setBackground(getResources().getDrawable(R.drawable.cell_full));
            if (k == 3)
                cells_array[10].setBackground(getResources().getDrawable(R.drawable.cell_full));
            if (k == 4)
                cells_array[11].setBackground(getResources().getDrawable(R.drawable.cell_full));
        }

        for (int k = 1; k <= translate_cells; k++)
        {
            if (k == 1)
                cells_array[0].setBackground(getResources().getDrawable(R.drawable.cell_full));
            if (k == 2)
                cells_array[1].setBackground(getResources().getDrawable(R.drawable.cell_full));
            if (k == 3)
                cells_array[2].setBackground(getResources().getDrawable(R.drawable.cell_full));
            if (k == 4)
                cells_array[3].setBackground(getResources().getDrawable(R.drawable.cell_full));
        }

        for (int k = 1; k <= date_cells; k++)
        {
            if (k == 1)
                cells_array[4].setBackground(getResources().getDrawable(R.drawable.cell_full));
            if (k == 2)
                cells_array[5].setBackground(getResources().getDrawable(R.drawable.cell_full));
            if (k == 3)
                cells_array[6].setBackground(getResources().getDrawable(R.drawable.cell_full));
            if (k == 4)
                cells_array[7].setBackground(getResources().getDrawable(R.drawable.cell_full));
        }
    }

    public String fromBase64(String text)
    {
        byte[] data = null;
        try
        {
            data = text.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        byte[] decodedBytes = Base64.decodeBase64(data);
        return new String(decodedBytes);
    }
}