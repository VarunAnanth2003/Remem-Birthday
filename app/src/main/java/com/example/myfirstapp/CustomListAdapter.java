package com.example.myfirstapp;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter {

    private final Activity context;
    private ArrayList<String> namesArray;
    private ArrayList<String> datesArray;
    private final Integer[] imageIDArray;

    public CustomListAdapter(Activity context, Integer[] imageIDArray, ArrayList<String> namesArray, ArrayList<String> datesArray) {
        super(context, R.layout.content_list_view_row, namesArray);

        this.context = context;

        this.imageIDArray = imageIDArray;
        this.namesArray = namesArray;
        this.datesArray = datesArray;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.content_list_view_row, null, true);

        TextView namesfield = rowView.findViewById(R.id.nameView);
        TextView datesfield = rowView.findViewById(R.id.dateVIew);
        ImageView sideimage = rowView.findViewById(R.id.sideImage);
        if(namesArray.size() > 0 && datesArray.size() > 0) {
                namesfield.setText(namesArray.get(position));
                datesfield.setText(datesArray.get(position));
            if(namesArray.get(position).length() >= 18) namesfield.setTextSize(16);
        } else {
            namesfield.setText("Null");
            datesfield.setText("Null");
        }
        sideimage.setImageResource(R.drawable.shifted_btc);
        return rowView;
    }
}
