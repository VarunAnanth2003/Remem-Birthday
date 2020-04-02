package com.example.myfirstapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class DialogActivity extends Activity {

    EditText nameBox;
    private String editName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        nameBox = findViewById(R.id.primaryInput);
        editName = getIntent().getStringExtra("textField");
        nameBox.setText(editName);
    }
    public void submitData(View view) {
        String nameString = nameBox.getText().toString();
        Intent textIntent = new Intent();
        textIntent.putExtra("editName", nameString);
        setResult(RESULT_OK, textIntent);
        finish();
    }
    public void cancelDialog(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }
}
