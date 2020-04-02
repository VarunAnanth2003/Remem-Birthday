package com.example.myfirstapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;

public class CustomTextPopupDialog extends Activity {

    private ArrayList<String> finalNames = new ArrayList<>();
    private ArrayList<String> finalDates = new ArrayList<>();
    private ArrayList<String> refinedNames = new ArrayList<>();
    private ArrayList<String> refinedDates = new ArrayList<>();
    private ArrayList<Integer> refinedDaysLeft = new ArrayList<>();
    private ArrayList<Integer> finalDaysLeft = new ArrayList<>();

    private DateConverter dateConverter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_dialog);
        ListView mainView = findViewById(R.id.mainListView);
        convertNames();
        TextDialogAdapter mainAdapter = new TextDialogAdapter(this, finalNames, finalDates, finalDaysLeft);
        mainView.setAdapter(mainAdapter);
        mainAdapter.notifyDataSetChanged();
        mainView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DateConverter dateConverter = new DateConverter(finalDates.get(position));
                String personName;
                if(finalNames.get(position).contains(" ")) {
                    personName = finalNames.get(position).substring(0, finalNames.get(position).indexOf(" "));
                } else {
                    personName = finalNames.get(position);
                }
                if(dateConverter.getDaysUntil() == 0) {
                    String message = personName + " turns " + dateConverter.getAge() + " years old today!";
                    Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                    toast.show();
                } else if (dateConverter.getDaysUntil() == 1) {
                    String message = personName + " will turn " + dateConverter.getAge() + " years old tomorrow!";
                    Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    String message = personName + " will turn " + dateConverter.getAge() + " years old in " + dateConverter.getDaysUntil() + " days!";
                    Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }
    private void convertNames() {
        SharedPreferences savedSharedPreferences = getSharedPreferences("USER_PREFERENCES", Context.MODE_PRIVATE);
        String[] rawNames = savedSharedPreferences.getString("SavedNames", "").split("&");
        String[] rawDates = savedSharedPreferences.getString("SavedDates", "").split("&");
        for (int i = 0; i < rawNames.length; i++) {
            refinedNames.add(i, rawNames[i]);
        }
        for (int i = 0; i < rawDates.length; i++) {
            refinedDates.add(i, rawDates[i]);
        }
        deleteBlanks();
        for(String m : refinedDates) {
            dateConverter = new DateConverter(m);
            refinedDaysLeft.add(dateConverter.getDaysUntil());
        }
        for(int x : refinedDaysLeft) {
            SharedPreferences sp = getApplicationContext().getSharedPreferences("USER_PREFERENCES", Context.MODE_PRIVATE);
            if(x == 0 && sp.getBoolean("SetDReminders", true)) {
                finalNames.add(refinedNames.get(refinedDaysLeft.indexOf(x)));
                finalDates.add(refinedDates.get(refinedDaysLeft.indexOf(x)));
                finalDaysLeft.add(refinedDaysLeft.get(refinedDaysLeft.indexOf(x)));
            }
            if(x == 1 && sp.getBoolean("SetDBReminders", true)) {
                finalNames.add(refinedNames.get(refinedDaysLeft.indexOf(x)));
                finalDates.add(refinedDates.get(refinedDaysLeft.indexOf(x)));
                finalDaysLeft.add(refinedDaysLeft.get(refinedDaysLeft.indexOf(x)));
            }
            if(x <= 7 && x >= 2 && sp.getBoolean("SetWReminders", true)) {
                finalNames.add(refinedNames.get(refinedDaysLeft.indexOf(x)));
                finalDates.add(refinedDates.get(refinedDaysLeft.indexOf(x)));
                finalDaysLeft.add(refinedDaysLeft.get(refinedDaysLeft.indexOf(x)));
            }
            if(x <= 30 && x >= 8 && sp.getBoolean("SetMReminders", true)) {
                finalNames.add(refinedNames.get(refinedDaysLeft.indexOf(x)));
                finalDates.add(refinedDates.get(refinedDaysLeft.indexOf(x)));
                finalDaysLeft.add(refinedDaysLeft.get(refinedDaysLeft.indexOf(x)));
            }
        }
    }
    public void deleteBlanks() {
        for(int i = 0; i < refinedNames.size(); i++) {
            if(refinedNames.get(i).equals("")) {
                refinedNames.remove(i);
                i--;
            }
        }
        for(int i = 0; i < refinedDates.size(); i++) {
            if(refinedDates.get(i).equals("")) {
                refinedDates.remove(i);
                i--;
            }
        }
    }
    public void quitDialog(View view) {
        finish();
    }
}
