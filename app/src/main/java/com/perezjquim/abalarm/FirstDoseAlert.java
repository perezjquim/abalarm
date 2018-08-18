package com.perezjquim.abalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.perezjquim.AlarmHelper;
import com.perezjquim.SharedPreferencesHelper;
import com.perezjquim.UIHelper;

public class FirstDoseAlert extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        System.out.println("! First dose !");

        UIHelper.notify(context, "AB Alarm", "Time to take your anti-biotic!");

        SharedPreferencesHelper prefs = new SharedPreferencesHelper(context);

        AlarmHelper.scheduleRepeatingAlarm(context,"DoseAlert", prefs.getInt("config","interval") * 1000  * 3600);
    }
}
