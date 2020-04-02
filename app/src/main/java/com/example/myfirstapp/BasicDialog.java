package com.example.myfirstapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BasicDialog extends Activity {

    public TextView titleTextView;
    public TextView messageTextView;
    public Button negativeButton;
    public Button positiveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_dialog);
        titleTextView = (TextView) findViewById(R.id.dialogTitleTextView);
        messageTextView = (TextView) findViewById(R.id.dialogSubtitleTextView);
        negativeButton = (Button) findViewById(R.id.cancelButtondialog);
        positiveButton = (Button) findViewById(R.id.submitButtonDialog);
        titleTextView.setText(getIntent().getStringExtra("Title"));
        messageTextView.setText(getIntent().getStringExtra("Message"));
        negativeButton.setText(getIntent().getStringExtra("NegativeText"));
        positiveButton.setText(getIntent().getStringExtra("PositiveText"));
    }
    public void submitData(View view) {
        setResult(RESULT_OK);
        finish();
    }
    public void cancelDialog(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }
}
