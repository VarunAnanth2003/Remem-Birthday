package com.example.myfirstapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;
import java.util.TimeZone;

import static android.content.Context.ALARM_SERVICE;

public class BootReceiver extends BroadcastReceiver {

    AlarmManager am;

    @Override
    public void onReceive(Context context, Intent intent) {
        setAlarm(context);
    }

    public void setAlarm(Context context) {
        am = (AlarmManager)context.getSystemService(ALARM_SERVICE);
        Intent i = new Intent(context, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        if(calendar.get(Calendar.HOUR_OF_DAY) > 6) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 6);
        } else {
            calendar.set(Calendar.HOUR_OF_DAY, 6);
        }
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),86400000, pi);
        context.startService(new Intent(context, AlarmSetterService.class));
    }
}
