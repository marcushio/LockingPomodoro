package com.example.lockingpomodoro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.EditText;
import android.widget.TextView;

public class FocusActivity extends AppCompatActivity {
    private int counter = 0; //I wonder about this one
    private long millisInFuture = 5000; //these are hardcoded but you'll have to change to reflect interval later
    private long countDownInterval = 1000;
    private TextView nameTextView;
    private int pomodoroCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String taskName =(String)extras.get("taskName"); //again I think I need to not hardcode this
        counter = (int)extras.get("taskInterval");
        pomodoroCount = (int)extras.get("taskCount"); //I should unite these later
        millisInFuture = (counter * 1000); //x1k b/c this is in millis
        nameTextView = findViewById(R.id.focus_taskname);
        nameTextView.setText(taskName);
        final TextView timerText = findViewById(R.id.timer);
        EditText positiveMessageText = findViewById(R.id.positive_message);

        new CountDownTimer(millisInFuture, countDownInterval){
            @Override
            public  void onTick(long millisUntilFinished){
                timerText.setText(String.valueOf(counter));
               --counter;
            }
            @Override
            public void onFinish(){
                pomodoroCount++;
                String finish = "Congrats, Pomodoro complete! \n New Task Count: " + pomodoroCount;
                timerText.setText("finished"); //this needs to update tally and return to main activity
                positiveMessageText.setText(finish);
            }
        }.start();
    }
}
