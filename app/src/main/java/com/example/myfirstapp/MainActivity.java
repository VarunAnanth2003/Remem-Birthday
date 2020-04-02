package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView nameTextView;
    private TextView dateTextView;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private AlertDialog.Builder nameDialog;

    public String sendName = "";
    public String sendDate = "";
    public String editName = "";
    public String editDate = "";

    public int resetYear = Calendar.getInstance().get(Calendar.YEAR);
    public int resetMonth = Calendar.getInstance().get(Calendar.MONTH);
    public int resetDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

    private ArrayList<String> namesArray = new ArrayList<String>();
    private ArrayList<String> datesArray = new ArrayList<String>();

    public void submitData(View view) {
        view = findViewById(android.R.id.content);
        if(nameTextView.getText().toString().equals("Name: ") || dateTextView.getText().toString().equals("Birthday: ")) {
            Snackbar snackbar = Snackbar.make(view, "Add values to ALL fields", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {
            sendName = nameTextView.getText().toString().replace("Name:", "").trim();
            sendDate = dateTextView.getText().toString().replace("Birthday:", "").trim();
            namesArray.add(namesArray.size(), sendName);
            datesArray.add(datesArray.size(), sendDate);

            Intent openListIntent = new Intent(this, ListOfDaysActivity.class);
            openListIntent.putExtra("namesArray", namesArray);
            openListIntent.putExtra("datesArray", datesArray);

            finish();
            startActivity(openListIntent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameTextView = findViewById(R.id.nameView);
        dateTextView = findViewById(R.id.dateView);
        editName = getIntent().getStringExtra("editName");
        if(editName == null) {
            nameTextView.setText("Name: ");
            editName = "";
        } else {
            nameTextView.setText("Name: " + editName);
        }
        editDate = getIntent().getStringExtra("editDate");
        if(editDate == null) {
            dateTextView.setText("Birthday: ");
        } else {
            dateTextView.setText("Birthday: " + editDate);
        }

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                resetYear = year;
                resetMonth = month;
                resetDay = dayOfMonth;
                month++;
                sendDate = month + "/" + dayOfMonth + "/" + year;
                dateTextView.setText("Birthday: " + sendDate);
            }
        };
    }

    public void goHome(View view) {
        Intent homeIntent = new Intent(this, EnterActivity.class);
        finish();
        startActivity(homeIntent);
    }

    public void editName(View view) {
        openNameDialog();
    }
    public void editDate(View view) {
       openCalendarDialog();
    }

    public void openNameDialog() {
        int RequestCode = 1;
        Intent intent = new Intent(this, DialogActivity.class);
        intent.putExtra("textField" , editName);
        startActivityForResult(intent, RequestCode);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1) {
            if(resultCode == RESULT_OK) {
               editName = data.getStringExtra("editName");
                if(editName == null) {
                    nameTextView.setText("Name: ");
                    editName = "";
                } else {
                    nameTextView.setText("Name: " + editName);
                }
                sendName = editName;
            }
        }
    }
    public void openCalendarDialog() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog calendarDialog = new DatePickerDialog(MainActivity.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth, dateSetListener, year, month, day);

        if(editDate == null) {
            calendarDialog.updateDate(resetYear, resetMonth, resetDay);
        } else {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            Date savedDate = new Date();
            try {
                savedDate = dateFormatter.parse(editDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar editCal = Calendar.getInstance();
            editCal.setTime(savedDate);
            calendarDialog.updateDate(editCal.get(Calendar.YEAR), editCal.get(Calendar.MONTH), editCal.get(Calendar.DAY_OF_MONTH));
        }
        calendarDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        calendarDialog.show();
    }
}
