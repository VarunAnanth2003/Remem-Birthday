package com.example.myfirstapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class TextDialogAdapter extends ArrayAdapter {
    private final Activity context;
    private ArrayList<String> namesArray;
    private ArrayList<String> datesArray;
    private ArrayList<Integer> daysUntilArray;
    public TextDialogAdapter(Activity context, ArrayList<String> namesArray, ArrayList<String> datesArray, ArrayList<Integer> daysUntilArray) {
        super(context, R.layout.text_dialog_list_view_row, namesArray);

        this.namesArray = namesArray;
        this.datesArray = datesArray;
        this.daysUntilArray = daysUntilArray;
        this.context = context;
    }
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.text_dialog_list_view_row, null, true);

        TextView namesfield = rowView.findViewById(R.id.textNameView);
        TextView datesfield = rowView.findViewById(R.id.textDateView);
        TextView daysuntilfield = rowView.findViewById(R.id.textDaysUntilView);
        ImageView sideImage = rowView.findViewById(R.id.textSideImage);

        if(namesArray.size() > 0 && datesArray.size() > 0 && daysUntilArray.size() > 0) {
            namesfield.setText(namesArray.get(position));
            datesfield.setText(datesArray.get(position));
            daysuntilfield.setText("Days until Birthday: " + daysUntilArray.get(position).toString());
            if(namesArray.get(position).length() >= 18) namesfield.setTextSize(16);
        } else {
            namesfield.setText("Null");
            datesfield.setText("Null");
            daysuntilfield.setText("Null");
        }
        sideImage.setImageResource(R.drawable.shifted_btc);
        return rowView;
    }
}
