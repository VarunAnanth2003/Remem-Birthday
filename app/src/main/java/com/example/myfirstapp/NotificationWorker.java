package com.example.myfirstapp;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.ArrayList;

public class NotificationWorker extends Worker {
    public final String CHANNEL_ID = "MainChannel";
    String birthdayName = "";
    int notificationType;
    private ArrayList<String> allDates = new ArrayList<>();
    private ArrayList<String> allNames = new ArrayList<>();
    private ArrayList<Integer> allAges = new ArrayList<>();

    private ArrayList<String> finalNames = new ArrayList<>();
    private ArrayList<Integer> allInts = new ArrayList<>();
    private ArrayList<String> allDuntil = new ArrayList<>();

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("USER_PREFERENCES", Context.MODE_PRIVATE);
        if(prefs.getBoolean("SetReminders", true)) executeCreate();
        return Result.success();
    }

    public void executeCreate() {
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("USER_PREFERENCES", Context.MODE_PRIVATE);
        String analyzeDateString = prefs.getString("SavedDates", "");
        processDate(analyzeDateString);
        String analyzeNameString = prefs.getString("SavedNames", "");
        processName(analyzeNameString);

        for (int i = 0; i < allDates.size(); i++) {
            birthdayName = getName(i);
            if (birthdayName != null) {
                DateConverter dateConverter = new DateConverter(allDates.get(i));
                allAges.add(dateConverter.getAge());
                finalNames.add(birthdayName);
                allInts.add(notificationType);
                allDuntil.add(getDaysUntil(i));
            }
        }

        for (int i = 0; i < finalNames.size(); i++) {
            buildNotification(finalNames.get(i), allAges.get(i), allInts.get(i), getApplicationContext(), i, allDuntil.get(i));
        }
    }

    public String getDaysUntil(int i) {
        DateConverter dateConverter = new DateConverter(allDates.get(i));
        if (allDates.get(i).equals("")) {
            return null;
        }
        return "" + dateConverter.getDaysUntil();
    }

    public String getName(int i) {
        String rawName;
        String refinedName;
        DateConverter dateConverter = new DateConverter(allDates.get(i));
        if (allDates.get(i).equals("")) {
            return null;
        }
        if (dateConverter.getDaysUntil() == 0) {
            rawName = allNames.get(i);
            notificationType = 0;
        } else if (dateConverter.getDaysUntil() == 1) {
            rawName = allNames.get(i);
            notificationType = 1;
        } else if (dateConverter.getDaysUntil() <= 7 && dateConverter.getDaysUntil() >= 2) {
            rawName = allNames.get(i);
            notificationType = 2;
        } else if(dateConverter.getDaysUntil() <= 30 && dateConverter.getDaysUntil() >= 8) {
            rawName = allNames.get(i);
            notificationType = 3;
        } else {
            return null;
        }

        if (rawName.contains(" ")) {
            refinedName = rawName.substring(0, rawName.indexOf(" "));
        } else {
            refinedName = rawName;
        }
        return refinedName;
    }

    private void processDate(String analyzeDateString) {
        String[] datesSplitUp = analyzeDateString.split("&");

        for (int i = 0; i < datesSplitUp.length; i++) {
            allDates.add(i, datesSplitUp[i]);
        }
    }

    private void processName(String analyzeNameString) {
        String[] namesSplitUp = analyzeNameString.split("&");

        for (int i = 0; i < namesSplitUp.length; i++) {
            allNames.add(i, namesSplitUp[i]);
        }
    }

    public void buildNotification(String name, int age, int notificationType, Context context, int notificationNumber, String daysUntil) {
        Intent openTap = new Intent(context, EnterActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, openTap, 0);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("USER_PREFERENCES", Context.MODE_PRIVATE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.pix_cake);
        if (notificationType == 0 && sharedPreferences.getBoolean("SetDReminders", true)) {
            builder.setContentTitle("Birthday Alert for today! (Swipe down to open)");
            builder.setStyle(new NotificationCompat.BigTextStyle()
                    .bigText("It's " + name + "'s birthday! " + name + " is " + age + " years old. Wish them a happy birthday!"));
            builder.setPriority(NotificationCompat.PRIORITY_HIGH);
            builder.setContentIntent(pendingIntent);
            builder.setAutoCancel(true);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(notificationNumber, builder.build());
        } else if (notificationType == 1 && sharedPreferences.getBoolean("SetDBReminders", true)) {
            builder.setContentTitle("Birthday Alert for tomorrow! (Swipe down to open)");
            builder.setStyle(new NotificationCompat.BigTextStyle()
                    .bigText("Tomorrow is " + name + "'s birthday! " + name + " will be " + age + " years old. Are you ready to wish them?"));
            builder.setPriority(NotificationCompat.PRIORITY_HIGH);
            builder.setContentIntent(pendingIntent);
            builder.setAutoCancel(true);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(notificationNumber, builder.build());
        } else if (notificationType == 2 && sharedPreferences.getBoolean("SetWReminders", true)) {
            builder.setContentTitle("Birthday Alert for this Week! (Swipe down to open)");
            builder.setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(name + "'s birthday is in " + daysUntil + " days! " + name + " will be " + age + " years old. Did you buy a present?"));
            builder.setPriority(NotificationCompat.PRIORITY_HIGH);
            builder.setContentIntent(pendingIntent);
            builder.setAutoCancel(true);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(notificationNumber, builder.build());
        } else if (notificationType == 3 && sharedPreferences.getBoolean("SetMReminders", true)) {
            builder.setContentTitle("Birthday Alert for this Month! (Swipe down to open)");
            builder.setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(name + "'s birthday is coming up in " + daysUntil + " days! " + name + " will be " + age + " years old."));
            builder.setPriority(NotificationCompat.PRIORITY_HIGH);
            builder.setContentIntent(pendingIntent);
            builder.setAutoCancel(true);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(notificationNumber, builder.build());
        }
    }
}
