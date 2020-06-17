package com.example.lockingpomodoro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

public class FocusActiviity extends AppCompatActivity {
    private int counter = 0; //I wonder about this one
    private long millisInFuture;
    private long countDownInterval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus);

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
