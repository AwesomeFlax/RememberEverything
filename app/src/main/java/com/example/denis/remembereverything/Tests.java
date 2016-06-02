package com.example.denis.remembereverything;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
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
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class Tests extends Activity
{
    int _INT_date_counter = 0;
    int _INT_translate_counter = 0;

    int date_cells;
    int translate_cells;

    ImageButton date_next;
    ImageButton date_back;

    Object[] monthes = new Object[12];

    String user_name;
    String eventText;
    TextView event;
    String correctDate1; // = "1996-09-15";
    String correctDate2; // = "2003-09-15";
    TextView question;

    boolean period;// = true;

    // массив для перемешивания дат
    String[] dates = new String[4];

    RadioGroup radioGr;
    RadioButton[] date = new RadioButton[4];

    Integer randDate;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests);

        date_next = (ImageButton) findViewById(R.id.date_next);
        date_back = (ImageButton) findViewById(R.id.date_back);
        date_back.setOnClickListener(new CounterInfo());
        date_next.setOnClickListener(new CounterInfo());

        //чтобы был доступ к сети
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //инициализация первых записей
        new getDates().execute();

        Intent intent = getIntent();
        user_name = intent.getStringExtra("name");

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
                    _INT_date_counter++;
                    new getDates().execute();
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

                //перевод
                /*case R.id.translate_next:
                {
                    _INT_translate_counter++;
                    new getTranslates().execute();
                    break;
                }

                //перевод
                case R.id.translate_back:
                {
                    if (_INT_translate_counter > 0)
                    {
                        _INT_translate_counter--;
                        new getTranslates().execute();
                    }
                    break;
                }*/
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

        question.setText("Укажите дату случившегося события.");
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

        if (month == 1)
        {
            day = rand.nextInt(32) + 1;
        }
        else if (month == 2)
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
        else if ((month == 3) || (month == 5 || (month == 7) || (month == 8) || (month == 10) || (month == 12)))
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
        StringBuilder strbuff = new StringBuilder(date);

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

        questions[0] = "Сколько лет длилось указанное событие?";
        questions[1] = "Когда началось указанное событие?";
        questions[2] = "Когда закончилось указанное событие?";
        questions[3] = "Укажите период, в котором заключено данное событие.";

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
                for (int i = 0; i < Jarray.length(); i++)
                {
                    JSONObject Jasonobject;
                    Jasonobject = Jarray.getJSONObject(i);
                    String name = Jasonobject.getString("user");
                    long k2 = Integer.valueOf(Jasonobject.getString("check_date"));
                    long k1 = getDate();

                    if (user_name.equalsIgnoreCase(name))
                    {
                        if (local_counter == _INT_date_counter)
                        {
                            if (k1 - k2 > 172800000)
                            {
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

    // БОГ, ПОСМОТРЕВ НА ЭТИ СТРОКИ, НАЧАЛ ПЛАКАТЬ.
    // СЕЙЧАС ТЫ ДУМАЕШЬ, ЧТО СО СМЕХУ, НО НА САМОМ ДЕЛЕ
    // ОН ТИХО СКАЗАЛ: "СЕГОДНЯ МЕНЯ ПРЕВЗОШЛИ".
    public void launchDateCheck(View v)
    {
        boolean check = false;

        for (int i = 0; i < 4; i++)
        {
            if (date[i].isChecked() && i == randDate)
            {
                check = true;
                Toast.makeText(getApplicationContext(), "Я босс", Toast.LENGTH_LONG).show();
            }
        }
        if (!check)
        {
            Toast.makeText(getApplicationContext(), "Лошара", Toast.LENGTH_LONG).show();
        }

    }

    public long getDate()
    {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTimeInMillis();
    }
}