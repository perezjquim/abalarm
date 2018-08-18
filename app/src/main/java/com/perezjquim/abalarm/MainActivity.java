package com.perezjquim.abalarm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TimePicker;

import com.perezjquim.AlarmHelper;
import com.perezjquim.SharedPreferencesHelper;
import com.perezjquim.UIHelper;

import java.util.Calendar;
import java.util.Date;

import static com.perezjquim.UIHelper.toast;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void setAlarm(View v)
    {
        SeekBar seekBar = findViewById(R.id.interval);
        int interval = seekBar.getProgress() + 1;
        //System.out.println("Interval: " + interval + " hours");

        SharedPreferencesHelper prefs = new SharedPreferencesHelper(this);
        prefs.setInt("config","interval", interval);

        Calendar now = Calendar.getInstance();
        //System.out.println("Now: "+now.getTime().toString());

        TimePicker picker = findViewById(R.id.begintime);

        Calendar firstDose = (Calendar) now.clone();
        firstDose.set(Calendar.HOUR_OF_DAY, picker.getCurrentHour());
        firstDose.set(Calendar.MINUTE, picker.getCurrentMinute());
        firstDose.set(Calendar.SECOND, 0);
        //System.out.println("First dose: "+firstDose.getTime().toString());

        long remainingTime;

        if(now.before(firstDose))
        {
            remainingTime = firstDose.getTimeInMillis() - now.getTimeInMillis();

            toast(this,"Alarm scheduled!");

            toast(this,"First dose in " + remainingTime / (1000 * 60) + " minutes");
            //System.out.println("First dose in " + remainingTime / (1000 * 60) + " minutes");
        }
        else
        {
            Calendar nextDose = (Calendar) firstDose.clone();
            nextDose.add(Calendar.HOUR_OF_DAY, interval);

            remainingTime = nextDose.getTimeInMillis() - now.getTimeInMillis();

            toast(this,"Alarm scheduled!");

            toast(this,"Next dose in " + remainingTime / (1000 * 60) + " minutes");
            //System.out.println("Next dose in " + remainingTime / (1000 * 60) + " minutes");
        }

        AlarmHelper.scheduleStandardAlarm(this,"FirstDoseAlert", remainingTime);
    }
}
