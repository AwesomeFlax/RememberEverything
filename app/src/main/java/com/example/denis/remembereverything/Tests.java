package com.example.denis.remembereverything;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
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
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class Tests extends Activity
{
    int NotesQuantity = 0;
    int _INT_date_counter = 0;
    int date_cells;
    int randDate;

    Button send_button_Date;
    Button send_button_Translate;
    ImageButton date_next;
    ImageButton date_previous;

    Object[] monthes = new Object[12];

    String date_ID;
    String translate_ID;
    String user_name;
    String correctDate1;                // = "1996-09-15";
    String correctDate2;                // = "2003-09-15";
    String[] dates = new String[4];     // массив для перемешивания дат

    TextView question;
    TextView event;

    boolean period;                     // = true;

    RadioGroup radioGr;
    RadioButton[] date = new RadioButton[4];

    //Переменные с другого экрана
    int _INT_translate_counter = 0;
    int NotesQuantity_2 = 0;
    int translate_cells;

    ImageButton translate_next;
    ImageButton translate_previous;

    String words;//"My name is Alya";
    String trans;//"Меня зовут Аля";

    TextView test;
    TextView tv_words;
    TextView tv_trans;
    TextView goodJob;

    boolean[] press = new boolean[8];

    String tmp_trans;
    String lett;
    int position = 0;
    int[] pos = new int[8];
    char[] letters;
    int[] indexAll;
    int[] indexFour = new int[4];
    int[] indexSix = new int[6];
    int[] indexEight = new int[8];

    int length;

    TextView[] buttons = new TextView[8];

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests);

        date_next = (ImageButton) findViewById(R.id.date_next);
        date_previous = (ImageButton) findViewById(R.id.date_back);
        date_previous.setOnClickListener(new CounterInfo());
        date_next.setOnClickListener(new CounterInfo());

        //чтобы был доступ к сети
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent intent = getIntent();
        user_name = intent.getStringExtra("name");

        //инициализация первых записей (или нет)
        new checkTestsAvailable().execute();

        //месяца в формате архива
        monthes[0] = getResources().getString(R.string.january);
        monthes[1] = getResources().getString(R.string.february);
        monthes[2] = getResources().getString(R.string.march);
        monthes[3] = getResources().getString(R.string.april);
        monthes[4] = getResources().getString(R.string.may);
        monthes[5] = getResources().getString(R.string.june);
        monthes[6] = getResources().getString(R.string.july);
        monthes[7] = getResources().getString(R.string.august);
        monthes[8] = getResources().getString(R.string.september);
        monthes[9] = getResources().getString(R.string.october);
        monthes[10] = getResources().getString(R.string.november);
        monthes[11] = getResources().getString(R.string.december);

        event = (TextView) findViewById(R.id.eventView);
        question = (TextView) findViewById(R.id.question);

        radioGr = (RadioGroup) findViewById(R.id.radioGr);
        date[0] = (RadioButton) findViewById((R.id.date1));
        date[1] = (RadioButton) findViewById((R.id.date2));
        date[2] = (RadioButton) findViewById((R.id.date3));
        date[3] = (RadioButton) findViewById((R.id.date4));

        send_button_Date = (Button) findViewById(R.id.ok);
        send_button_Translate = (Button) findViewById(R.id.send_button_Translate);

        //вкладочки
        TabHost tabs = (TabHost) findViewById(android.R.id.tabhost);
        tabs.setup();
        TabHost.TabSpec spec;

        spec = tabs.newTabSpec("tag2");
        spec.setContent(R.id.tab2);
        spec.setIndicator(getResources().getString(R.string.choice_2));
        tabs.addTab(spec);

        spec = tabs.newTabSpec("tag3");
        spec.setContent(R.id.tab3);
        spec.setIndicator(getResources().getString(R.string.choice_1));
        tabs.addTab(spec);

        tabs.setCurrentTab(0);
        //event.setText(eventText);


        //Другой экран
        translate_next = (ImageButton) findViewById(R.id.translate_next);
        translate_previous = (ImageButton) findViewById(R.id.translate_back);
        translate_previous.setOnClickListener(new CounterInfo());
        translate_next.setOnClickListener(new CounterInfo());


        test = (TextView) findViewById(R.id.textView);
        tv_words = (TextView) findViewById(R.id.textView2);
        tv_trans = (TextView) findViewById(R.id.textView3);
        goodJob = (TextView) findViewById(R.id.textView4);


        buttons[0] = (TextView) findViewById(R.id.tv_1);
        buttons[1] = (TextView) findViewById(R.id.tv_2);
        buttons[2] = (TextView) findViewById(R.id.tv_3);
        buttons[3] = (TextView) findViewById(R.id.tv_4);
        buttons[4] = (TextView) findViewById(R.id.tv_5);
        buttons[5] = (TextView) findViewById(R.id.tv_6);
        buttons[6] = (TextView) findViewById(R.id.tv_7);
        buttons[7] = (TextView) findViewById(R.id.tv_8);

        buttons[0].setOnClickListener(new ButtonListener());
        buttons[1].setOnClickListener(new ButtonListener());
        buttons[2].setOnClickListener(new ButtonListener());
        buttons[3].setOnClickListener(new ButtonListener());
        buttons[4].setOnClickListener(new ButtonListener());
        buttons[5].setOnClickListener(new ButtonListener());
        buttons[6].setOnClickListener(new ButtonListener());
        buttons[7].setOnClickListener(new ButtonListener());

        //инициализация первых записей (или нет)
        new checkTestsAvailable_2().execute();

        send_button_Date.setOnClickListener(new View.OnClickListener()
        {
            InputStream is = null;

            @Override
            public void onClick(View arg0)
            {
                List<NameValuePair> nameValuePairs = new ArrayList<>(1);

                if (date[randDate].isChecked())
                {
                    nameValuePairs.add(new BasicNameValuePair("id", date_ID));
                    nameValuePairs.add(new BasicNameValuePair("mark", String.valueOf(date_cells + 1)));
                }
                else
                {
                    nameValuePairs.add(new BasicNameValuePair("id", date_ID));
                    nameValuePairs.add(new BasicNameValuePair("mark", String.valueOf(date_cells)));
                }
                try
                {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://remember-everything.ml/connections/update_progress_date.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

                //if (NotesQuantity != 1)
                new checkTestsAvailable().execute();
                //else
                //globalCleanDate();
            }
        });

        send_button_Translate.setOnClickListener(new View.OnClickListener()
        {
            InputStream is = null;

            @Override
            public void onClick(View arg0)
            {
                List<NameValuePair> nameValuePairs = new ArrayList<>(1);

                String tmp = (String) tv_trans.getText();

                if (tmp.equals(trans))
                {
                    nameValuePairs.add(new BasicNameValuePair("id", translate_ID));
                    nameValuePairs.add(new BasicNameValuePair("mark", String.valueOf(translate_cells + 1)));

                    try
                    {
                        HttpClient httpClient = new DefaultHttpClient();
                        HttpPost httpPost = new HttpPost("http://remember-everything.ml/connections/update_progress_translate.php");
                        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                        HttpResponse response = httpClient.execute(httpPost);
                        HttpEntity entity = response.getEntity();
                        is = entity.getContent();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }

                    new checkTestsAvailable_2().execute();

                }
                else
                {
                    goodJob.setText("Try again or complete another one test");
                }
            }
        });
    }

    void globalCleanDate()
    {
        date_next.setVisibility(View.INVISIBLE);
        date_previous.setVisibility(View.INVISIBLE);
        FrameLayout main_part_date = (FrameLayout) findViewById(R.id.main_part_date);
        main_part_date.setVisibility(View.INVISIBLE);

        LinearLayout tab3 = (LinearLayout) findViewById(R.id.tab3);
        tab3.setBackground(getResources().getDrawable(R.drawable.relax));
    }

    //счетчик, показывающий, какую запись показывать
    public class CounterInfo implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                //дата
                case R.id.date_next:
                {
                    if (_INT_date_counter < NotesQuantity - 1)
                    {
                        _INT_date_counter++;
                        new getDates().execute();
                    }
                    break;
                }

                //дата
                case R.id.date_back:
                {
                    if (_INT_date_counter > 0)
                    {
                        _INT_date_counter--;
                        new getDates().execute();
                    }
                    break;
                }

                //дата
                case R.id.translate_next:
                {
                    if (_INT_translate_counter < NotesQuantity_2 - 1)
                    {
                        _INT_translate_counter++;
                        new getTranslates().execute();
                    }
                    break;
                }

                //дата
                case R.id.translate_back:
                {
                    if (_INT_translate_counter > 0)
                    {
                        _INT_translate_counter--;
                        new getTranslates().execute();
                    }
                    break;
                }
            }
        }
    }

    void onSimpleDate()
    {
        // конвертирование даты с 1500-01-01 в 1 Января 1500
        String corDate = convertDate(correctDate1);

        // местоположение правильного ответа
        Random rand = new Random();
        randDate = rand.nextInt(4);

        dates[randDate] = corDate;

        // заполнение остальных генерируемых дат
        for (int i = 0; i < 4; i++)
        {
            if (i != randDate)
            {
                dates[i] = dateGenerator(correctDate1);
            }
        }

        question.setText(getResources().getString(R.string.question_single));

        date[0].setText(dates[0]);
        date[1].setText(dates[1]);
        date[2].setText(dates[2]);
        date[3].setText(dates[3]);
    }

    void onSimplePeriod(int randDate)
    {
        // конвертирование даты с 1500-01-01 в 1 Января 1500
        String corDate1 = convertDate(correctDate1);
        String corDate2 = convertDate(correctDate2);

        dates[randDate] = corDate1 + " - " + corDate2;

        // заполнение остальных генерируемых дат
        for (int i = 0; i < 4; i++)
        {
            if (i != randDate)
            {
                dates[i] = dateGenerator(correctDate1) + " - " + dateGenerator(correctDate2);
            }
        }
    }

    void onSimpleYear(int randDate)
    {
        StringBuilder strbuff1 = new StringBuilder(correctDate1);
        StringBuilder strbuff2 = new StringBuilder(correctDate2);
        Integer year3 = Integer.valueOf(strbuff2.substring(0, 4)) - Integer.valueOf(strbuff1.substring(0, 4));

        dates[randDate] = Integer.toString(year3);

        for (int i = 0; i < 4; i++)
        {
            if (i != randDate)
            {
                dates[i] = dateGeneratorYear(year3);
            }
        }
    }

    protected String dateGenerator(String corDate)
    {
        // рандомим вариант генерации новой даты
        Random rand = new Random();
        Integer randDate = rand.nextInt(4);

        // переводим дату стринга в интежеры
        StringBuilder strbuff = new StringBuilder(corDate);

        String sday = strbuff.substring(8, 10);
        String smonth = strbuff.substring(5, 7);
        String syear = strbuff.substring(0, 4);

        Integer day = Integer.valueOf(sday);
        Integer month = Integer.valueOf(smonth);
        Integer year = Integer.valueOf(syear);
        // это длинный процесс

        // варианты генерации дат
        switch (randDate)
        {
            case 0:
                month = mixMonth(month);
                day = mixDay(day, month, year);
                break;
            case 1:
                year = mixYear(year);
                day = mixDay(day, month, year);
                break;
            case 2:
                month = mixMonth(month);
                year = mixYear(year);
                break;
            case 3:
                year = mixYear(year);
                month = mixMonth(month);
                day = mixDay(day, month, year);
                break;
        }

        // конвертируем дату с чисел в 1 Января 1500

        return convertDateSimple(day, month, year);
    }

    int mixYear(int year)
    {
        Random rand = new Random();

        year = year + (rand.nextInt(11) - 10);

        return year;
    }

    int mixMonth(int month)
    {
        Random rand = new Random();

        month = rand.nextInt(12) + 1;

        return month;
    }

    int mixDay(int day, int month, int year)
    {
        Random rand = new Random();

        if (month == 2)
        {
            if (year % 4 == 0)
            {
                day = rand.nextInt(29) + 1;
            }
            else
            {
                day = rand.nextInt(28) + 1;
            }
        }
        else if ((month == 1) || (month == 3) || (month == 5 || (month == 7) || (month == 8) || (month == 10) || (month == 12)))
        {
            day = rand.nextInt(31) + 1;
        }
        else
        {
            day = rand.nextInt(30) + 1;
        }

        return day;
    }

    String convertDateSimple(int day, int month, int year)
    {
        String monthString = monthes[month - 1].toString();
        return day + " " + monthString + " " + year;
    }

    String convertDate(String date)
    {
        // конвертируем со стринга в интежеры
        StringBuffer strbuff = new StringBuffer(date);

        String sday = strbuff.substring(8, 10);
        String smonth = strbuff.substring(5, 7);
        String syear = strbuff.substring(0, 4);

        Integer day = Integer.valueOf(sday);
        Integer month = Integer.valueOf(smonth);
        Integer year = Integer.valueOf(syear);
        // такой же длинный процесс

        String monthString = monthes[month - 1].toString();
        date = day + " " + monthString + " " + year;
        return date;
    }

    protected int questionPeriod()
    {
        String[] questions = new String[4];

        Random rand = new Random();
        int randQuest = rand.nextInt(4);

        questions[0] = getResources().getString(R.string.question_1);
        questions[1] = getResources().getString(R.string.question_2);
        questions[2] = getResources().getString(R.string.question_3);
        questions[3] = getResources().getString(R.string.question_4);

        question.setText(questions[randQuest]);

        return randQuest;
    }

    String dateGeneratorYear(int year)
    {
        Random rand = new Random();
        int randDate = year + (rand.nextInt(10) - 10);
        return String.valueOf(Math.abs(randDate));
    }

    public String fromBase64(String text)
    {
        byte[] data = null;
        try
        {
            data = text.getBytes("UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        byte[] decodedBytes = Base64.decodeBase64(data);
        return new String(decodedBytes);
    }

    //проверяем, остались ли ещё даты
    class checkTestsAvailable extends AsyncTask<String, String, Void>
    {
        InputStream is = null;
        String result = "";

        //получение данных
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
            try
            {
                JSONArray Jarray = new JSONArray(result);

                NotesQuantity = 0;

                //подсчет количества записей
                for (int i = 0; i < Jarray.length(); i++)
                {
                    JSONObject Jasonobject;
                    Jasonobject = Jarray.getJSONObject(i);
                    String name = Jasonobject.getString("user");

                    int k3 = Integer.valueOf(Jasonobject.getString("check_"));
                    long k2 = Integer.valueOf(Jasonobject.getString("check_date"));
                    k2 = k2 * 1000;
                    long k1 = getDate();

                    if (user_name.equalsIgnoreCase(name))
                    {
                        if (((k1 - k2) > 172800000) && (k3 < 4))
                        {
                            NotesQuantity++;
                        }
                    }
                }

                if (NotesQuantity > 0)
                {
                    new getDates().execute();
                    radioGr.clearCheck();
                }
                else
                {
                    globalCleanDate();
                }
            }

            catch (
                    Exception e
                    )

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

        //получение данных
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
            int local_counter = 0;

            try
            {
                JSONArray Jarray = new JSONArray(result);

                NotesQuantity = 0;

                //подсчет количества записей
                for (int i = 0; i < Jarray.length(); i++)
                {
                    JSONObject Jasonobject;
                    Jasonobject = Jarray.getJSONObject(i);
                    String name = Jasonobject.getString("user");

                    int k3 = Integer.valueOf(Jasonobject.getString("check_"));
                    long k2 = Integer.valueOf(Jasonobject.getString("check_date"));
                    k2 = k2 * 1000;
                    long k1 = getDate();

                    if (user_name.equalsIgnoreCase(name))
                    {
                        if (((k1 - k2) > 172800000) && (k3 < 4))
                        {
                            NotesQuantity++;
                        }
                    }
                }

                for (int i = 0; i < Jarray.length(); i++)
                {
                    JSONObject Jasonobject;
                    Jasonobject = Jarray.getJSONObject(i);
                    String name = Jasonobject.getString("user");

                    int k3 = Integer.valueOf(Jasonobject.getString("check_"));
                    long k2 = Integer.valueOf(Jasonobject.getString("check_date"));
                    k2 = k2 * 1000;
                    long k1 = getDate();

                    if (user_name.equalsIgnoreCase(name))
                    {
                        if (((k1 - k2) > 172800000) && (k3 < 4))
                        {
                            if (local_counter == _INT_date_counter)
                            {
                                date_ID = Jasonobject.getString("id");
                                event.setText(fromBase64(Jasonobject.getString("term")));
                                correctDate1 = Jasonobject.getString("date_1");
                                //date[0].setText(correctDate1);
                                date_cells = Integer.valueOf(Jasonobject.getString("check_"));
                                //не трогай дурко //fillAllTheCells();

                                if (Jasonobject.getString("period").equals("1"))
                                {
                                    period = true;
                                    correctDate2 = Jasonobject.getString("date_2");
                                }
                                else
                                {
                                    period = false;
                                }
                            }

                            local_counter++;
                        }
                    }
                }
            }
            catch (Exception e)
            {
                // TODO: handle exception
                Log.e("log_tag", "Error! " + e.toString());
            }

            if (period)
            {
                int quest = questionPeriod();

                // местоположение правильного ответа
                Random rand = new Random();
                Integer randDate = rand.nextInt(4);
                String corDate;

                switch (quest)
                {
                    case 0:
                    {
                        onSimpleYear(randDate);
                        break;
                    }
                    case 1:
                    {
                        // конвертирование даты с 1500-01-01 в 1 Января 1500
                        corDate = convertDate(correctDate1);

                        dates[randDate] = corDate;

                        // заполнение остальных генерируемых дат
                        for (int i = 0; i < 4; i++)
                        {
                            if (i != randDate)
                            {
                                dates[i] = dateGenerator(correctDate1);
                            }
                        }
                        break;
                    }
                    case 2:
                    {
                        // конвертирование даты с 1500-01-01 в 1 Января 1500
                        corDate = convertDate(correctDate2);

                        dates[randDate] = corDate;


                        // заполнение остальных генерируемых дат
                        for (int i = 0; i < 4; i++)
                        {
                            if (i != randDate)
                            {
                                dates[i] = dateGenerator(correctDate2);
                            }
                        }
                        break;
                    }
                    case 3:
                        onSimplePeriod(randDate);
                        break;
                }
                date[0].setText(dates[0]);
                date[1].setText(dates[1]);
                date[2].setText(dates[2]);
                date[3].setText(dates[3]);
                //dateGeneratorPeriod();
            }
            else
            {
                onSimpleDate();
            }
        }

    }

    public long getDate()
    {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTimeInMillis();
    }

    public static String replaceCharAt(String s, int pos, char c)
    {

        return s.substring(0, pos) + c + s.substring(pos + 1);

    }

    int FindPosition(char[] letters)
    {
        for (int i = 0; i < letters.length; i++)
            if (letters[i] == '_')
            {
                return i;
            }
        return -1;
    }

    void bubbleSort(int[] arr)
    {
        for (int i = arr.length - 1; i >= 0; i--)
        {
            for (int j = 0; j < i; j++)
            {
                if (arr[j] > arr[j + 1])
                {
                    int t = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = t;
                }
            }
        }
    }

    void RemoveAll(char[] tmp, int length)
    {
        Random rand = new Random();

        indexAll = new int[length];

        for (int i = 0; i < length; i++)
            indexAll[i] = rand.nextInt(length);

        for (int k = 0; k < length; k++)
            for (int j = k + 1; j < length; j++)
                if (indexAll[k] == indexAll[j] || tmp[indexAll[k]] == ' ')
                {
                    do
                    {
                        indexAll[k] = rand.nextInt(length);
                    }
                    while (indexAll[k] == indexAll[j] || tmp[indexAll[k]] == ' ');
                }

        for (int i = 0; i < length; i++)
            buttons[i].setText(String.valueOf(tmp[indexAll[i]]));


        tmp_trans = replaceCharAt(trans, indexAll[0], '_');

        for (int i = 1; i < length; i++)
            tmp_trans = replaceCharAt(tmp_trans, indexAll[i], '_');

        bubbleSort(indexAll);
        position = indexAll[0];

        tv_trans.setText(tmp_trans);
    }

    void RemoveFour(char[] tmp, int length)
    {
        Random rand = new Random();

        for (int i = 0; i < 4; i++)
            indexFour[i] = rand.nextInt(length);

        for (int k = 0; k < 4; k++)
            for (int j = k + 1; j < 4; j++)
                if (indexFour[k] == indexFour[j] || tmp[indexFour[k]] == ' ')
                {
                    do
                    {
                        indexFour[k] = rand.nextInt(length);
                    }
                    while (indexFour[k] == indexFour[j] || tmp[indexFour[k]] == ' ');
                }

        for (int i = 0; i < 4; i++)
            buttons[i].setText(String.valueOf(tmp[indexFour[i]]));

        tmp_trans = replaceCharAt(trans, indexFour[0], '_');

        for (int i = 1; i < 4; i++)
            tmp_trans = replaceCharAt(tmp_trans, indexFour[i], '_');

        bubbleSort(indexFour);
        position = indexFour[0];

        tv_trans.setText(tmp_trans);
    }

    void RemoveSix(char[] tmp, int length)
    {
        Random rand = new Random();

        for (int i = 0; i < 6; i++)
            indexSix[i] = rand.nextInt(length);

        for (int k = 0; k < 6; k++)
            for (int j = k + 1; j < 6; j++)
                if (indexSix[k] == indexSix[j] || tmp[indexSix[k]] == ' ')
                {
                    do
                    {
                        indexSix[k] = rand.nextInt(length);
                    }
                    while (indexSix[k] == indexSix[j] || tmp[indexSix[k]] == ' ');
                }

        for (int i = 0; i < 6; i++)
            buttons[i].setText(String.valueOf(tmp[indexSix[i]]));

        tmp_trans = replaceCharAt(trans, indexSix[0], '_');

        for (int i = 1; i < 6; i++)
            tmp_trans = replaceCharAt(tmp_trans, indexSix[i], '_');

        bubbleSort(indexSix);
        position = indexSix[0];

        tv_trans.setText(tmp_trans);
    }

    void RemoveEight(char[] tmp, int length)
    {
        Random rand = new Random();

        for (int i = 0; i < 8; i++)
            indexEight[i] = rand.nextInt(length);

        for (int k = 0; k < 8; k++)
            for (int j = k + 1; j < 8; j++)
                if (indexEight[k] == indexEight[j] || tmp[indexEight[k]] == ' ')
                {
                    do
                    {
                        indexEight[k] = rand.nextInt(length);
                    }
                    while (indexEight[k] == indexEight[j] || tmp[indexEight[k]] == ' ');
                }

        for (int i = 0; i < 8; i++)
            buttons[i].setText(String.valueOf(tmp[indexEight[i]]));

        tmp_trans = replaceCharAt(trans, indexEight[0], '_');

        for (int i = 1; i < 8; i++)
            tmp_trans = replaceCharAt(tmp_trans, indexEight[i], '_');

        bubbleSort(indexEight);
        position = indexEight[0];

        tv_trans.setText(tmp_trans);
    }

    void Check(String tmp)
    {
        boolean check = true;
        char[] char_tmp = tmp.toCharArray();
        for (int i = 0; i < length; i++)
            if (char_tmp[i] == '_')
            {
                check = false;
            }

        if (check)
        {
            if (tmp.equals(trans))
            {
                //goodJob.setText("Good Job!");
            }
            else
            {
                //goodJob.setText("Looser");
            }
        }
        else
        {
            goodJob.setText("");
        }

    }

    public class ButtonListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.tv_1:
                    if (press[0])
                    {
                        tmp_trans = replaceCharAt(tmp_trans, pos[0], '_');
                        press[0] = false;
                    }
                    else
                    {
                        letters = tmp_trans.toCharArray();
                        position = FindPosition(letters);
                        lett = String.valueOf(buttons[0].getText());
                        tmp_trans = replaceCharAt(tmp_trans, position, lett.charAt(0));
                        if (tmp_trans.length() == length)
                        {
                            Check(tmp_trans);
                        }
                        press[0] = true;
                        pos[0] = position;
                    }
                    tv_trans.setText(tmp_trans);
                    break;
                case R.id.tv_2:
                    if (press[1])
                    {
                        tmp_trans = replaceCharAt(tmp_trans, pos[1], '_');
                        press[1] = false;
                    }
                    else
                    {
                        letters = tmp_trans.toCharArray();
                        position = FindPosition(letters);
                        lett = String.valueOf(buttons[1].getText());
                        tmp_trans = replaceCharAt(tmp_trans, position, lett.charAt(0));
                        if (tmp_trans.length() == length)
                        {
                            Check(tmp_trans);
                        }
                        press[1] = true;
                        pos[1] = position;
                    }
                    tv_trans.setText(tmp_trans);
                    break;
                case R.id.tv_3:
                    if (press[2])
                    {
                        tmp_trans = replaceCharAt(tmp_trans, pos[2], '_');
                        press[2] = false;
                    }
                    else
                    {
                        letters = tmp_trans.toCharArray();
                        position = FindPosition(letters);
                        lett = String.valueOf(buttons[2].getText());
                        tmp_trans = replaceCharAt(tmp_trans, position, lett.charAt(0));
                        if (tmp_trans.length() == length)
                        {
                            Check(tmp_trans);
                        }
                        press[2] = true;
                        pos[2] = position;
                    }
                    tv_trans.setText(tmp_trans);
                    break;
                case R.id.tv_4:
                    if (press[3])
                    {
                        tmp_trans = replaceCharAt(tmp_trans, pos[3], '_');
                        press[3] = false;
                    }
                    else
                    {
                        letters = tmp_trans.toCharArray();
                        position = FindPosition(letters);
                        lett = String.valueOf(buttons[3].getText());
                        tmp_trans = replaceCharAt(tmp_trans, position, lett.charAt(0));
                        if (tmp_trans.length() == length)
                        {
                            Check(tmp_trans);
                        }
                        press[3] = true;
                        pos[3] = position;
                    }
                    tv_trans.setText(tmp_trans);
                    break;
                case R.id.tv_5:
                    if (press[4])
                    {
                        tmp_trans = replaceCharAt(tmp_trans, pos[4], '_');
                        press[4] = false;
                    }
                    else
                    {
                        letters = tmp_trans.toCharArray();
                        position = FindPosition(letters);
                        lett = String.valueOf(buttons[4].getText());
                        tmp_trans = replaceCharAt(tmp_trans, position, lett.charAt(0));
                        if (tmp_trans.length() == length)
                        {
                            Check(tmp_trans);
                        }
                        press[4] = true;
                        pos[4] = position;
                    }
                    tv_trans.setText(tmp_trans);
                    break;
                case R.id.tv_6:
                    if (press[5])
                    {
                        tmp_trans = replaceCharAt(tmp_trans, pos[5], '_');
                        press[5] = false;
                    }
                    else
                    {
                        letters = tmp_trans.toCharArray();
                        position = FindPosition(letters);
                        lett = String.valueOf(buttons[5].getText());
                        tmp_trans = replaceCharAt(tmp_trans, position, lett.charAt(0));
                        if (tmp_trans.length() == length)
                        {
                            Check(tmp_trans);
                        }
                        press[5] = true;
                        pos[5] = position;
                    }
                    tv_trans.setText(tmp_trans);
                    break;
                case R.id.tv_7:
                    if (press[6])
                    {
                        tmp_trans = replaceCharAt(tmp_trans, pos[6], '_');
                        press[6] = false;
                    }
                    else
                    {
                        letters = tmp_trans.toCharArray();
                        position = FindPosition(letters);
                        lett = String.valueOf(buttons[6].getText());
                        tmp_trans = replaceCharAt(tmp_trans, position, lett.charAt(0));
                        if (tmp_trans.length() == length)
                        {
                            Check(tmp_trans);
                        }
                        press[6] = true;
                        pos[6] = position;
                    }
                    tv_trans.setText(tmp_trans);
                    break;
                case R.id.tv_8:
                    if (press[7])
                    {
                        tmp_trans = replaceCharAt(tmp_trans, pos[7], '_');
                        press[7] = false;
                    }
                    else
                    {
                        letters = tmp_trans.toCharArray();
                        position = FindPosition(letters);
                        lett = String.valueOf(buttons[7].getText());
                        tmp_trans = replaceCharAt(tmp_trans, position, lett.charAt(0));
                        if (tmp_trans.length() == length)
                        {
                            Check(tmp_trans);
                        }
                        press[7] = true;
                        pos[7] = position;
                    }
                    tv_trans.setText(tmp_trans);
                    break;
            }
        }
    }

    void globalCleanTranslate()
    {
        translate_next.setVisibility(View.INVISIBLE);
        translate_previous.setVisibility(View.INVISIBLE);
        FrameLayout main_part_date = (FrameLayout) findViewById(R.id.main_part_date);
        main_part_date.setVisibility(View.INVISIBLE);

        LinearLayout tab3 = (LinearLayout) findViewById(R.id.tab3);
        tab3.setBackground(getResources().getDrawable(R.drawable.relax));
    }

    class checkTestsAvailable_2 extends AsyncTask<String, String, Void>
    {
        InputStream is = null;
        String result = "";

        //получение данных
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
            try
            {
                JSONArray Jarray = new JSONArray(result);

                NotesQuantity_2 = 0;

                //подсчет количества записей
                for (int i = 0; i < Jarray.length(); i++)
                {
                    JSONObject Jasonobject;
                    Jasonobject = Jarray.getJSONObject(i);
                    String name = Jasonobject.getString("user");

                    int k3 = Integer.valueOf(Jasonobject.getString("check_"));
                    long k2 = Integer.valueOf(Jasonobject.getString("check_date"));
                    k2 = k2 * 1000;
                    long k1 = getDate();

                    if (user_name.equalsIgnoreCase(name))
                    {
                        if (((k1 - k2) > 172800000) && (k3 < 4))
                        {
                            NotesQuantity_2++;
                        }
                    }
                }

                if (NotesQuantity_2 > 0)
                {
                    new getTranslates().execute();

                }
                else
                {
                    globalCleanTranslate();
                }
            }
            catch (
                    Exception e
                    )

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
            int local_counter = 0;

            try
            {
                JSONArray Jarray = new JSONArray(result);

                NotesQuantity_2 = 0;

                //подсчет количества записей
                for (int i = 0; i < Jarray.length(); i++)
                {
                    //_INT_translate_quantity = 0;

                    JSONObject Jasonobject_translate;
                    Jasonobject_translate = Jarray.getJSONObject(i);
                    String name = Jasonobject_translate.getString("user");

                    if (user_name.equalsIgnoreCase(name))
                    {
                        NotesQuantity_2++;
                    }
                }

                for (int i = 0; i < Jarray.length(); i++)
                {
                    JSONObject Jasonobject_translate;
                    Jasonobject_translate = Jarray.getJSONObject(i);
                    String name = Jasonobject_translate.getString("user");

                    int k3 = Integer.valueOf(Jasonobject_translate.getString("check_"));
                    long k2 = Integer.valueOf(Jasonobject_translate.getString("check_date"));
                    k2 = k2 * 1000;
                    long k1 = getDate();

                    if (user_name.equalsIgnoreCase(name))
                    {
                        if (((k1 - k2) > 172800000) && (k3 < 4))
                        {
                            if (local_counter == _INT_translate_counter)
                            {
                                words = fromBase64(Jasonobject_translate.getString("word_original"));
                                trans = fromBase64(Jasonobject_translate.getString("word_translate"));

                                translate_ID = Jasonobject_translate.getString("id");

                                String index_1 = Jasonobject_translate.getString("lang_original");
                                String index_2 = Jasonobject_translate.getString("lang_translate");

                                //flag_1.setBackground(getResources().getDrawable((Integer) flags[Integer.valueOf(index_1)]));
                                //flag_2.setBackground(getResources().getDrawable((Integer) flags[Integer.valueOf(index_2)]));
                                translate_cells = Integer.valueOf(Jasonobject_translate.getString("check_"));
                            }

                            local_counter++;
                        }
                    }
                }
            }
            catch (Exception e)
            {
                // TODO: handle exception
                Log.e("log_tag", "Error! " + e.toString());
            }

            //Toast.makeText(getApplicationContext(), trans, Toast.LENGTH_SHORT).show();
            //Toast.makeText(getApplicationContext(), words, Toast.LENGTH_SHORT).show();

            trans = trans.toLowerCase();
            words = words.toLowerCase();

            length = trans.length();

            letters = trans.toCharArray();

            tv_words.setText(words);
            tv_trans.setText(trans);

            if (length < 9)
            {
                RemoveAll(letters, length);

                for (int i = 0; i < length; i++)
                    buttons[i].setVisibility(View.VISIBLE);
            }


            if (length > 8 && length < 13)
            {
                RemoveFour(letters, length);
                for (int i = 0; i < 4; i++)
                    buttons[i].setVisibility(View.VISIBLE);
            }


            if (length > 12 && length < 21)
            {
                RemoveSix(letters, length);
                for (int i = 0; i < 6; i++)
                    buttons[i].setVisibility(View.VISIBLE);
            }

            if (length > 20)
            {
                RemoveEight(letters, length);
                for (int i = 0; i < 8; i++)
                    buttons[i].setVisibility(View.VISIBLE);
            }

        }
    }
}