package com.perezjquim.abalarm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.perezjquim.AlarmHelper;

import java.util.Calendar;

import static com.perezjquim.UIHelper.toast;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TimePicker timePicker = findViewById(R.id.begintime);
        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));

        SeekBar seekBar = findViewById(R.id.interval);

        TextView label = findViewById(R.id.lbl_progress);
        label.setText((seekBar.getProgress() + 1) + "");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                label.setText((progress + 1) + "");
            }
        });
    }

    public void setAlarm(View v)
    {
        SeekBar seekBar = findViewById(R.id.interval);
        int interval = seekBar.getProgress() + 1;
        //System.out.println("Interval: " + interval + " hours");

        Calendar now = Calendar.getInstance();
        //System.out.println("Now: "+now.getTime().toString());

        TimePicker picker = findViewById(R.id.begintime);

        Calendar nextDose = (Calendar) now.clone();
        nextDose.set(Calendar.HOUR_OF_DAY, picker.getCurrentHour());
        nextDose.set(Calendar.MINUTE, picker.getCurrentMinute());
        nextDose.set(Calendar.SECOND, 0);
        //System.out.println("Next dose: "+nextDose.getTime().toString());

        if(nextDose.before(now))
        {
            nextDose.add(Calendar.DAY_OF_YEAR,1);
        }

        AlarmHelper.scheduleRepeatingAlarm(this,DoseAlert.class, nextDose, interval * 1000 * 3600);

        long minutesRemaining = (nextDose.getTimeInMillis() - now.getTimeInMillis()) / (1000 * 60);

        toast(this,"Alarm scheduled!");

        toast(this,"Next dose in " + minutesRemaining + " minutes");

        this.finish();
    }
}
