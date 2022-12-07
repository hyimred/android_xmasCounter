package com.ptrkcsak.xmascounter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private TextView szamlalo;
    private Timer timer;
    private Date karacsony;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init() {
        szamlalo = findViewById(R.id.szamlalo);
        Calendar most = Calendar.getInstance();
        int ev = most.get(Calendar.YEAR);
        int honap = most.get(Calendar.MONTH);
        int nap = most.get(Calendar.DATE);

        if (honap == 11 && nap == 24) {
            ev++;
        }
        Calendar karacsony_calendar = Calendar.getInstance();
        karacsony_calendar.set(ev, 11, 24, 0, 0, 0);
        karacsony = karacsony_calendar.getTime();
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
    }

    @Override
    protected void onStart() {
        super.onStart();
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Date most = Calendar.getInstance().getTime();
                long hatralevoIdo = karacsony.getTime() - most.getTime();
                long masodpercmili = 1000;
                long percmili = masodpercmili * 60;
                long oramili = percmili * 60;
                long napmili = oramili * 24;

                long nap = hatralevoIdo / napmili;
                hatralevoIdo = hatralevoIdo % napmili;
                long ora = hatralevoIdo / oramili;
                hatralevoIdo = hatralevoIdo % oramili;
                long perc = hatralevoIdo / percmili;
                hatralevoIdo = hatralevoIdo % percmili;
                long masodperc = hatralevoIdo / masodpercmili;

                String hatralevoSzoveg = getString(R.string.szamlaloformatum, nap, ora, perc, masodperc);
                runOnUiThread(() -> szamlalo.setText(hatralevoSzoveg));
            }
        };
        timer.schedule(task,0,500);

    }
}