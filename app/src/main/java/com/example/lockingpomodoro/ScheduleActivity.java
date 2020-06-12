package com.example.lockingpomodoro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ScheduleActivity extends AppCompatActivity {
    private EditText enterTaskField;
    private EditText enterWeightField;
    private EditText enterIntervalField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        final FloatingActionButton button = findViewById(R.id.floatingActionButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent replyIntent = new Intent();
                //let's get our information from our EditTexts
                enterTaskField = (EditText)findViewById(R.id.enterTask);
                enterWeightField = (EditText)findViewById(R.id.enterWeight);
                enterIntervalField = (EditText)findViewById(R.id.enter_interval);
                String taskName = enterTaskField.getText().toString();
                int taskWeight = Integer.parseInt( enterWeightField.getText().toString() );
                int taskInterval = Integer.parseInt( enterIntervalField.getText().toString() );
                if( infoIsComplete(enterTaskField, enterWeightField, enterIntervalField) ){
                    //this task needs to get passed back so I can put it in the db
                    Task newTask = new Task(taskName, taskWeight, taskInterval);
                    //I think we need to put it in a bundle to pass it, i think putExtra accomplishes this
                    Bundle taskBundle = new Bundle();
                    taskBundle.putString("TASK_NAME", taskName); //maybe don't hard code keys later idk what's best practice
                    taskBundle.putInt("TASK_WEIGHT", taskWeight);
                    taskBundle.putInt("TASK_INTERVAL", taskInterval);
                    replyIntent.putExtras(taskBundle);
                    setResult(RESULT_OK, replyIntent);
                } else {
                    setResult(RESULT_CANCELED, replyIntent);
                }
                finish();
            }
        });
    }

    private boolean infoIsComplete(EditText task, EditText weight, EditText interval){
        boolean hasAllInfo = true;
        if( TextUtils.isEmpty(task.getText()) ) { hasAllInfo = false; }
        if( TextUtils.isEmpty(weight.getText()) ) { hasAllInfo = false; }
        if( TextUtils.isEmpty(interval.getText()) ) { hasAllInfo = false; }
        return hasAllInfo;
    }

    /* I'm keeping this around for reference for now
    public void onScheduleClick(View view){ //why does this have to take a view?
        EditText enterTaskField = (EditText)findViewById(R.id.enterTask);
        EditText enterWeightField = (EditText)findViewById(R.id.enterWeight);
        EditText enterIntervalField = (EditText)findViewById(R.id.enter_interval);
        String taskName = enterTaskField.getText().toString();
        int taskWeight = Integer.parseInt( enterWeightField.getText().toString() );
        int taskInterval = Integer.parseInt( enterIntervalField.getText().toString() );
        Task newTask = new Task(taskName, taskWeight, taskInterval);
        //store the task into the db

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
   */
}
