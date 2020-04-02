package com.example.myfirstapp;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class ListOfDaysActivity extends AppCompatActivity {

    public final String CHANNEL_ID = "MainChannel";

    public final Integer[] IconHolder = new Integer[1];
    String namesString = "&";
    public ArrayList<String> allNames = new ArrayList<>();
    String datesString = "&";
    public ArrayList<String> allDates = new ArrayList<>();
    CustomListAdapter mainAdapter;

    public View passView;

    private AlertDialog.Builder promptDialog;

    ListView mainListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNotificationChannel();

        setContentView(R.layout.birthday_list_view);
        mainListView = findViewById(R.id.contentList);

        processData();
        deleteBlanks();
        mainAdapter = new CustomListAdapter(this, IconHolder, allNames, allDates);
        deleteBlanks();
        mainListView.setAdapter(mainAdapter);
        deleteBlanks();
        mainAdapter.notifyDataSetChanged();

        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DateConverter dateConverter = new DateConverter(allDates.get(position));
                String personName;
                if(allNames.get(position).contains(" ")) {
                    personName = allNames.get(position).substring(0, allNames.get(position).indexOf(" "));
                } else {
                    personName = allNames.get(position);
                }
                if(dateConverter.getDaysUntil() == 0) {
                    String message = "Today is " + personName + "'s birthday!";
                    Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    String message = "Days until " + personName + "'s birthday: " + dateConverter.getDaysUntil();
                    Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
    }

    private void createNotificationChannel() {
        String name = "Birthday Notifications";
        String description = "Reminds you when your friends birthday approaches";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    public void processData() {
        IconHolder[0] = 0;

        SharedPreferences savedSharedPreferences = getSharedPreferences("USER_PREFERENCES", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = savedSharedPreferences.edit();

        ArrayList<String> namesArray = (ArrayList<String>) getIntent().getSerializableExtra("namesArray");
        ArrayList<String> datesArray = (ArrayList<String>)getIntent().getSerializableExtra("datesArray");

        for(int i = 0; i < namesArray.size(); i++) {
            namesString = savedSharedPreferences.getString("SavedNames", "") + namesArray.get(i) + "&";
        }

        for(int i = 0; i < datesArray.size(); i++) {
            datesString = savedSharedPreferences.getString("SavedDates", "") + datesArray.get(i) + "&";
        }

        editor.putString("SavedNames", namesString);
        editor.putString("SavedDates", datesString);
        editor.apply();

        String[] nameSplitUp = namesString.split("&");
        String[] dateSplitUp = datesString.split("&");

        for(int i = 0; i < nameSplitUp.length; i++) {
            allNames.add(i, nameSplitUp[i]);
        }
        for(int i = 0; i < dateSplitUp.length; i++) {
            allDates.add(i, dateSplitUp[i]);
        }

    }

    public void clearData(View view) {
        int RequestCode = 3;
        Intent intent = new Intent(this, BasicDialog.class);
        intent.putExtra("Title", "Are you sure about this?");
        intent.putExtra("Message", "All of your data will be cleared");
        intent.putExtra("NegativeText", "Nevermind");
        intent.putExtra("PositiveText", "Clear All");
        startActivityForResult(intent, RequestCode);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 2) {
            if(resultCode == RESULT_OK) {
                SharedPreferences savedSharedPreferences = getSharedPreferences("USER_PREFERENCES", Context.MODE_PRIVATE);
                final SharedPreferences.Editor editor = savedSharedPreferences.edit();
                String savedNames = savedSharedPreferences.getString("SavedNames", "");
                String savedDates = savedSharedPreferences.getString("SavedDates", "");
                String[] nameSplitUp = savedNames.split("&");
                String[] dateSplitUp = savedDates.split("&");
                allNames.clear();
                allDates.clear();
                for (int i = 0; i < nameSplitUp.length; i++) {
                    allNames.add(i, nameSplitUp[i]);
                }
                for (int i = 0; i < dateSplitUp.length; i++) {
                    allDates.add(i, dateSplitUp[i]);
                }
                deleteBlanks();
                allNames.remove(allNames.get(mainListView.getPositionForView(passView)));
                allDates.remove(allDates.get(mainListView.getPositionForView(passView)));
                savedNames = "&";
                savedDates = "&";
                for (int i = 0; i < allNames.size(); i++) {
                    savedNames = savedNames + allNames.get(i) + "&";
                }
                for (int i = 0; i < allDates.size(); i++) {
                    savedDates = savedDates + allDates.get(i) + "&";
                }
                editor.putString("SavedNames", savedNames);
                editor.putString("SavedDates", savedDates);
                editor.apply();
                mainAdapter.notifyDataSetChanged();
            }
        }
        if(requestCode == 3) {
            if(resultCode == RESULT_OK) {
                SharedPreferences savedSharedPreferences = getSharedPreferences("USER_PREFERENCES", Context.MODE_PRIVATE);
                final SharedPreferences.Editor editor = savedSharedPreferences.edit();
                editor.remove("SavedNames");
                editor.remove("SavedDates");
                editor.commit();
                goBack();
            }
        }
        if(requestCode == 4) {
            if(resultCode == RESULT_OK) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("editName", allNames.get(mainListView.getPositionForView(passView)));
                intent.putExtra("editDate", allDates.get(mainListView.getPositionForView(passView)));

                SharedPreferences savedSharedPreferences = getSharedPreferences("USER_PREFERENCES", Context.MODE_PRIVATE);
                final SharedPreferences.Editor editor = savedSharedPreferences.edit();
                String savedNames = savedSharedPreferences.getString("SavedNames", "");
                String savedDates = savedSharedPreferences.getString("SavedDates", "");
                String[] nameSplitUp = savedNames.split("&");
                String[] dateSplitUp = savedDates.split("&");
                allNames.clear();
                allDates.clear();
                for (int i = 0; i < nameSplitUp.length; i++) {
                    allNames.add(i, nameSplitUp[i]);
                }
                for (int i = 0; i < dateSplitUp.length; i++) {
                    allDates.add(i, dateSplitUp[i]);
                }
                deleteBlanks();
                allNames.remove(allNames.get(mainListView.getPositionForView(passView)));
                allDates.remove(allDates.get(mainListView.getPositionForView(passView)));
                savedNames = "&";
                savedDates = "&";
                for (int i = 0; i < allNames.size(); i++) {
                    savedNames = savedNames + allNames.get(i) + "&";
                }
                for (int i = 0; i < allDates.size(); i++) {
                    savedDates = savedDates + allDates.get(i) + "&";
                }
                editor.putString("SavedNames", savedNames);
                editor.putString("SavedDates", savedDates);
                editor.apply();
                mainAdapter.notifyDataSetChanged();
                finish();
                startActivity(intent);
            }
        }
    }

    public void onDelete (View view) {
        int RequestCode = 2;
        Intent intent = new Intent(this, BasicDialog.class);
        intent.putExtra("Title", "Delete this item?");
        String sep = System.lineSeparator();
        intent.putExtra("Message", "Name: " + allNames.get(mainListView.getPositionForView(view)) + sep + "Date: " + allDates.get(mainListView.getPositionForView(view)));
        intent.putExtra("NegativeText", "Keep");
        intent.putExtra("PositiveText", "Delete");
        startActivityForResult(intent, RequestCode);
        final View myView = view;
        passView = myView;
    }

    public void onEdit(View view) {
        passView = view;
        int RequestCode = 4;
        Intent intent = new Intent(this, BasicDialog.class);
        intent.putExtra("Title", "Edit this item?");
        String sep = System.lineSeparator();
        intent.putExtra("Message", "Name: " + allNames.get(mainListView.getPositionForView(view)) + sep + "Date: " + allDates.get(mainListView.getPositionForView(view)));
        intent.putExtra("NegativeText", "Keep");
        intent.putExtra("PositiveText", "Edit");
        startActivityForResult(intent, RequestCode);
    }

    //This one is internally called
    public void goBack() {
        Intent goBackIntent = new Intent(this, EnterActivity.class);
        finish();
        startActivity(goBackIntent);
    }

    public void goBack(View view) {
        Intent goBackIntent = new Intent(this, MainActivity.class);
        finish();
        startActivity(goBackIntent);
    }

    public void goHome(View view) {
        Intent homeIntent = new Intent(this, EnterActivity.class);
        finish();
        startActivity(homeIntent);
    }

    public void deleteBlanks() {
        for(int i = 0; i < allNames.size(); i++) {
            if(allNames.get(i).equals("")) {
                allNames.remove(i);
                i--;
            }
        }
        for(int i = 0; i < allDates.size(); i++) {
            if(allDates.get(i).equals("")) {
                allDates.remove(i);
                i--;
            }
        }
    }
}
