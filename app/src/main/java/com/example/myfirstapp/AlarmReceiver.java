package com.example.myfirstapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class AlarmReceiver extends BroadcastReceiver {
    OneTimeWorkRequest oneTimeWorkRequest;

    @Override
    public void onReceive(Context context, Intent intent) {
        oneTimeWorkRequest = new OneTimeWorkRequest.Builder(NotificationWorker.class).build();
        WorkManager.getInstance().enqueueUniqueWork("NotificationWorker", ExistingWorkPolicy.KEEP, oneTimeWorkRequest);
    }
}
