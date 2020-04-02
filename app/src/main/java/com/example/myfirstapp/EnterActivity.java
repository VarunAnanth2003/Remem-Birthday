package com.example.myfirstapp;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;


public class EnterActivity extends AppCompatActivity {

    private ArrayList<String> allNames = new ArrayList<>();
    private ArrayList<String> allDates = new ArrayList<>();
    public final String CHANNEL_ID = "MainChannel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        startService(new Intent(this, AlarmSetterService.class));
    }

    public void checkBirthdays(View view) {
        startActivity(new Intent(this, CustomTextPopupDialog.class));
    }

    public void openAddNew(View view) {
        Intent addNew = new Intent (this, MainActivity.class);
        finish();
        startActivity(addNew);
    }

    public void openLists(View view) {
        Intent addNew = new Intent(this, ListOfDaysActivity.class);
        allNames.add("");
        allDates.add("");
        addNew.putExtra("namesArray", allNames);
        addNew.putExtra("datesArray", allDates);
        finish();
        startActivity(addNew);
    }

    public void openSettings(View view) {
        Intent intent = new Intent(EnterActivity.this, SettingsDialog.class);
        startActivity(intent);
    }

    public void quitApp(View view) {
        finish();
        //System.exit(0);
    }

}
