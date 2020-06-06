package com.example.lockingpomodoro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ScheduleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        //Intent intent = getIntent(); //I don't think I explicitly need the intent here
    }

    public void onScheduleClick(View view){ //why does this have to take a view?
        EditText enterTaskField = (EditText)findViewById(R.id.enterTask);
        String task = enterTaskField.getText().toString();

    }
}
