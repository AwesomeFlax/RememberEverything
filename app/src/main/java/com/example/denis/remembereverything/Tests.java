package com.example.denis.remembereverything;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Random;

public class Tests extends Activity
{
    String user_name;
    String eventText;
    TextView event;
    String correctDate1 = "1996-09-15";
    String correctDate2 = "2003-09-15";
    TextView question;

    boolean period = true;

    // массив для перемешивания дат
    String[] dates = new String[4];

    RadioButton[] date = new RadioButton[4];

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests);

        Intent intent = getIntent();
        user_name = intent.getStringExtra("name");

        event = (TextView) findViewById(R.id.eventView);
        question = (TextView) findViewById(R.id.question);

        date[0] = (RadioButton) findViewById((R.id.date1));
        date[1] = (RadioButton) findViewById((R.id.date2));
        date[2] = (RadioButton) findViewById((R.id.date3));
        date[3] = (RadioButton) findViewById((R.id.date4));


        if (period)
        {
            int quest = 3; //questionPeriod();

            // местоположение правильного ответа
            Random rand = new Random();
            Integer randDate = rand.nextInt(4);
            String corDate;

            switch (quest)
            {
                case 0:
                {
                    StringBuilder strbuff1 = new StringBuilder(correctDate1);
                    StringBuilder strbuff2 = new StringBuilder(correctDate2);
                    Integer year3 = Integer.valueOf(strbuff2.substring(0, 4)) - Integer.valueOf(strbuff1.substring(0, 4));

                    dates[randDate] = Integer.toString(year3);

                    for (int i = 0; i < 4; i++)
                    {
                        if (i != randDate)
                            dates[i] = dateGeneratorYear(year3);
                    }
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
                            dates[i] = dateGenerator(correctDate1);
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
                            dates[i] = dateGenerator(correctDate2);
                    }
                    break;
                }
                case 3:
                    // конвертирование даты с 1500-01-01 в 1 Января 1500
                    String corDate1 = convertDate(correctDate1);
                    String corDate2 = convertDate(correctDate2);

                    dates[randDate] = corDate1 + " - " + corDate2;

                    // заполнение остальных генерируемых дат
                    for (int i = 0; i < 4; i++)
                    {
                        if (i != randDate)
                            dates[i] = dateGenerator(correctDate1) + " - " + dateGenerator(correctDate2);
                    }
                    break;
            }
            date[0].setText(dates[0]);
            date[1].setText(dates[1]);
            date[2].setText(dates[2]);
            date[3].setText(dates[3]);
            //dateGeneratorPeriod();
        } else
        {
            // конвертирование даты с 1500-01-01 в 1 Января 1500
            String corDate = convertDate(correctDate1);

            // местоположение правильного ответа
            Random rand = new Random();
            Integer randDate = rand.nextInt(4);

            dates[randDate] = corDate;

            // заполнение остальных генерируемых дат
            for (int i = 0; i < 4; i++)
            {
                if (i != randDate)
                    dates[i] = dateGenerator(correctDate1);
            }

            question.setText("Укажите дату случившегося события.");
            date[0].setText(dates[0]);
            date[1].setText(dates[1]);
            date[2].setText(dates[2]);
            date[3].setText(dates[3]);
        }

        event.setText(eventText);
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

        switch (month)
        {
            case 1:
                day = rand.nextInt(32) + 1;
                break;
            case 2:
                if (year % 4 == 0)
                    day = rand.nextInt(29) + 1;
                else
                    day = rand.nextInt(28) + 1;
                break;
            case 3:
                day = rand.nextInt(31) + 1;
                break;
            case 4:
                day = rand.nextInt(30) + 1;
                break;
            case 5:
                day = rand.nextInt(31) + 1;
                break;
            case 6:
                day = rand.nextInt(30) + 1;
                break;
            case 7:
                day = rand.nextInt(31) + 1;
                break;
            case 8:
                day = rand.nextInt(31) + 1;
                break;
            case 9:
                day = rand.nextInt(30) + 1;
                break;
            case 10:
                day = rand.nextInt(31) + 1;
                break;
            case 11:
                day = rand.nextInt(30) + 1;
                break;
            case 12:
                day = rand.nextInt(31) + 1;
                break;
        }
        return day;
    }

    String convertDateSimple(int day, int month, int year)
    {
        String monthString = " ";

        switch (month)
        {
            case 1:
                monthString = "Янв";
                break;
            case 2:
                monthString = "Февр";
                break;
            case 3:
                monthString = "Март";
                break;
            case 4:
                monthString = "Апр";
                break;
            case 5:
                monthString = "Мая";
                break;
            case 6:
                monthString = "Июня";
                break;
            case 7:
                monthString = "Июля";
                break;
            case 8:
                monthString = "Авг";
                break;
            case 9:
                monthString = "Сент";
                break;
            case 10:
                monthString = "Окт";
                break;
            case 11:
                monthString = "Нояб";
                break;
            case 12:
                monthString = "Дек";
                break;
        }

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

        String monthString = " ";

        switch (month)
        {
            case 1:
                monthString = "Янв";
                break;
            case 2:
                monthString = "Февр";
                break;
            case 3:
                monthString = "Март";
                break;
            case 4:
                monthString = "Апр";
                break;
            case 5:
                monthString = "Мая";
                break;
            case 6:
                monthString = "Июня";
                break;
            case 7:
                monthString = "Июля";
                break;
            case 8:
                monthString = "Авг";
                break;
            case 9:
                monthString = "Сент";
                break;
            case 10:
                monthString = "Окт";
                break;
            case 11:
                monthString = "Нояб";
                break;
            case 12:
                monthString = "Дек";
                break;
        }

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
}