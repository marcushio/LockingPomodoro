package com.example.lockingpomodoro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FocusActivity extends AppCompatActivity {
    private int pomodoroCount;
    private int counter = 0;
    private long millisInFuture = 5000; //default value, it gets changed in the body
    private long countDownInterval = 1000;

    private TextView nameTextView;
    private Button addPomoButton;
    private Button dismissPomoButton;
    private Button abandonButton;
    private MediaPlayer player;
    private CountDownTimer timer;

    private String taskName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String taskName =(String)extras.get("taskName"); //again I think I need to not hardcode this
        counter = (int)extras.get("taskInterval");
        pomodoroCount = (int)extras.get("taskCount"); //I should unite these later
        nameTextView = findViewById(R.id.focus_taskname);

        millisInFuture = (counter * 1000); //*1k b/c this is in millis
        nameTextView.setText(taskName);


        final TextView timerText = findViewById(R.id.timer);
        EditText positiveMessageText = findViewById(R.id.positive_message);
        player = MediaPlayer.create(this, R.raw.invincibility);

        addPomoButton = findViewById(R.id.count_button);
        addPomoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = "C:\\Dev\\Android\\LockingPomodoro\\app\\src\\main\\res\\raw\\act_passed.mp3";
                startFinishSound();
                Intent addCountIntent = new Intent();
                addCountIntent.putExtra("TASK_TALLY", ++pomodoroCount);
                addCountIntent.putExtra("TASK_NAME", taskName);
                setResult(RESULT_OK, addCountIntent);
                finish();
            }
        });

        dismissPomoButton  = findViewById(R.id.dismiss_button);
        dismissPomoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = "C:/Dev/Android/LockingPomodoro/app/src/main/res/raw/temp_die.wav";
                startDieSound();
                Intent dismissIntent = new Intent();
                dismissIntent.putExtra("TASK_TALLY", pomodoroCount);
                setResult(RESULT_OK, dismissIntent);
                finish();
            }
        });

        abandonButton = findViewById(R.id.abandon_button);
        abandonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                Intent dismissIntent = new Intent();
                setResult(RESULT_OK, dismissIntent);
                finish();
            }
        });

        timer = new CountDownTimer(millisInFuture, countDownInterval){
            @Override
            public  void onTick(long millisUntilFinished){
                timerText.setText(String.valueOf(counter));
               --counter;
            }
            @Override
            public void onFinish(){
                player.start();
                String finish = "Pomodoro complete! \n Current Task Count: " + pomodoroCount;
                timerText.setText("Congrats! Finished"); //this needs to update tally and return to main activity
                positiveMessageText.setText(finish);
                abandonButton.setVisibility(View.INVISIBLE);
                addPomoButton.setVisibility(View.VISIBLE);
                dismissPomoButton.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    private void startDieSound(){
        if(player != null){
            player.stop();
            player.release();
            player = null;
        }
        player = MediaPlayer.create(this, R.raw.temp_die);
        player.start();
    }

    private void startFinishSound(){
        if(player != null){
            player.stop();
            player.release();
            player.release();
        }
        player = MediaPlayer.create(this, R.raw.act_passed);
        player.start();
    }


}
