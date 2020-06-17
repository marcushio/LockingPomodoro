package com.example.lockingpomodoro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.EditText;
import android.widget.TextView;

public class FocusActivity extends AppCompatActivity {
    private int counter = 0; //I wonder about this one
    private long millisInFuture = 50000; //these are hardcoded but you'll have to change to reflect interval later
    private long countDownInterval = 1000;
    private TextView taskName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus);

        taskName = findViewById(R.id.focus_taskname);
        //probably have to pull out task info from Bundle here
        //taskName.setText();
        final TextView timerText = findViewById(R.id.timer);
        new CountDownTimer(millisInFuture, countDownInterval){
            @Override
            public  void onTick(long millisUntilFinished){
                timerText.setText(String.valueOf(counter));
                counter++;
            }
            @Override
            public void onFinish(){
                timerText.setText("Finished");
            }
        }.start();
    }

    private void onTimerFinish(){ //I might need a view for this I def need some way to access tally
        //add
    }


}
