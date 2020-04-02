package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

public class SettingsDialog extends AppCompatActivity {

    public Switch reminderSwitch;
    public Switch MreminderSwitch;
    public Switch WreminderSwitch;
    public Switch DBreminderSwitch;
    public Switch DreminderSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_dialog);
        checkRemSwitch();
        checkMRemSwitch();
        checkWRemSwitch();
        checkDBRemSwitch();
        checkDRemSwitch();
        setRemPref(reminderSwitch);
        setMRemPref(MreminderSwitch);
        setWRemPref(WreminderSwitch);
        setDBRemPref(DBreminderSwitch);
        setDRemPref(DreminderSwitch);
    }

    public void checkRemSwitch() {
        reminderSwitch = findViewById(R.id.reminderSwitch);
        SharedPreferences sharedPreferences = getSharedPreferences("USER_PREFERENCES", Context.MODE_PRIVATE);
        if(sharedPreferences.getBoolean("SetReminders", true)) {
            reminderSwitch.setChecked(true);
        } else {
            reminderSwitch.setChecked(false);
        }
    }

    public void checkMRemSwitch() {
        MreminderSwitch = findViewById(R.id.monthlyR);
        SharedPreferences sharedPreferences = getSharedPreferences("USER_PREFERENCES", Context.MODE_PRIVATE);
        if(sharedPreferences.getBoolean("SetMReminders", false)) {
            MreminderSwitch.setChecked(true);
        } else {
            MreminderSwitch.setChecked(false);
        }
    }

    public void checkWRemSwitch() {
        WreminderSwitch = findViewById(R.id.weeklyR);
        SharedPreferences sharedPreferences = getSharedPreferences("USER_PREFERENCES", Context.MODE_PRIVATE);
        if(sharedPreferences.getBoolean("SetWReminders", true)) {
            WreminderSwitch.setChecked(true);
        } else {
            WreminderSwitch.setChecked(false);
        }
    }

    public void checkDBRemSwitch() {
        DBreminderSwitch = findViewById(R.id.dayBeforeR);
        SharedPreferences sharedPreferences = getSharedPreferences("USER_PREFERENCES", Context.MODE_PRIVATE);
        if(sharedPreferences.getBoolean("SetDBReminders", true)) {
            DBreminderSwitch.setChecked(true);
        } else {
            DBreminderSwitch.setChecked(false);
        }
    }

    public void checkDRemSwitch() {
        DreminderSwitch = findViewById(R.id.dayR);
        SharedPreferences sharedPreferences = getSharedPreferences("USER_PREFERENCES", Context.MODE_PRIVATE);
        if(sharedPreferences.getBoolean("SetDReminders", true)) {
            DreminderSwitch.setChecked(true);
        } else {
            DreminderSwitch.setChecked(false);
        }
    }

    public void setRemPref(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("USER_PREFERENCES", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (reminderSwitch.isChecked()) {
            editor.putBoolean("SetReminders", true);
            editor.commit();
            reminderSwitch.setText("Reminders (ON):");
        } else {
            editor.putBoolean("SetReminders", false);
            editor.commit();
            reminderSwitch.setText("Reminders (OFF):");
        }
    }

    public void setMRemPref(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("USER_PREFERENCES", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (MreminderSwitch.isChecked()) {
            editor.putBoolean("SetMReminders", true);
            editor.commit();
            MreminderSwitch.setText("30 Days Before (ON):");
        } else {
            editor.putBoolean("SetMReminders", false);
            editor.commit();
            MreminderSwitch.setText("30 Days Before (OFF):");
        }
    }

    public void setWRemPref(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("USER_PREFERENCES", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (WreminderSwitch.isChecked()) {
            editor.putBoolean("SetWReminders", true);
            editor.commit();
            WreminderSwitch.setText("7 Days Before (ON):");
        } else {
            editor.putBoolean("SetWReminders", false);
            editor.commit();
            WreminderSwitch.setText("7 Days Before (OFF):");
        }
    }

    public void setDBRemPref(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("USER_PREFERENCES", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (DBreminderSwitch.isChecked()) {
            editor.putBoolean("SetDBReminders", true);
            editor.commit();
            DBreminderSwitch.setText("1 Day Before (ON):");
        } else {
            editor.putBoolean("SetDBReminders", false);
            editor.commit();
            DBreminderSwitch.setText("1 Day Before (OFF):");
        }
    }

    public void setDRemPref(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("USER_PREFERENCES", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (DreminderSwitch.isChecked()) {
            editor.putBoolean("SetDReminders", true);
            editor.commit();
            DreminderSwitch.setText("On Birthday (ON):");
        } else {
            editor.putBoolean("SetDReminders", false);
            editor.commit();
            DreminderSwitch.setText("On Birthday (OFF):");
        }
    }

    public void goBack (View view) {
        Intent intent = new Intent(SettingsDialog.this, EnterActivity.class);
        startActivity(intent);
        finish();
    }
}
