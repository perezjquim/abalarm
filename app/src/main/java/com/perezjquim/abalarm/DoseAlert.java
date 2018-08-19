package com.perezjquim.abalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.perezjquim.UIHelper;

public class DoseAlert extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        System.out.println("! Dose !");
        UIHelper.notify(context,"AB Alarm", "Time to take your anti-biotic!");
    }
}
