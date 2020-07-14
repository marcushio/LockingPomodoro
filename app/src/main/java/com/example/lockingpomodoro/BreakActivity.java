package com.example.lockingpomodoro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BreakActivity extends AppCompatActivity {
    private long millisInFuture = 480000;
    private long countDownInterval = 1000;
    private int counter = 0;
    private CountDownTimer timer;

    private MediaPlayer player;
    private Vibrator vibe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_break);

        final TextView timerText = findViewById(R.id.break_timer);
        final TextView breakText = findViewById(R.id.break_title);

        Button endBreakButton = findViewById(R.id.end_break_button);
        endBreakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this is the end of the activity
                player.stop();
                player.release();
                endVibrate();
                //setResult isn't necessary. This activity bares no result been handled.
                setResult(RESULT_OK, new Intent());
                finish();
            }
        });

        player = MediaPlayer.create(this, R.raw.chaos_emerald);

        timer = new CountDownTimer(millisInFuture, countDownInterval){
            @Override
            public  void onTick(long millisUntilFinished){
                //timerText.setText(String.valueOf(counter)); old straightforward seconds disp
                timerText.setText(new SimpleDateFormat("mm:ss").format(new Date( millisUntilFinished)));
                --counter;
            }
            @Override
            public void onFinish(){
                timerText.setText(new SimpleDateFormat("mm:ss").format(new Date( 0)));
                player.start();
                vibratePhone();
                breakText.setText("Breaks Over, Back to Work!");
            }
        }.start();
    }

    @Override
    public void onBackPressed(){
        timer.cancel();
        player.stop();
        player.release();
        finish();
    }

    private void vibratePhone(){
        vibe = ((Vibrator) getSystemService(VIBRATOR_SERVICE));
        if (Build.VERSION.SDK_INT >= 26) {
            vibe.vibrate(VibrationEffect.createOneShot(10000000, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibe.vibrate(10000000);
        }
    }

    private void endVibrate(){
        vibe.cancel();
    }


}
