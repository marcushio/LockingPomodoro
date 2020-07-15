package com.example.lockingpomodoro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FocusActivity extends AppCompatActivity {
    private int pomodoroCount;
    private int counter = 0;
    private long millisInFuture = 5000; //default value, it gets changed in the body
    private long countDownInterval = 1000; //1000 = 1 second


    private TextView nameTextView;
    private Button addPomoButton;
    private Button dismissPomoButton;
    private Button abandonButton;

    private AudioManager audioManager;
    private MediaPlayer player;
    private CountDownTimer timer;
    private Vibrator vibe;

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
        //this comes as minutes
        millisInFuture = (counter * 60000); //*60k b/c 60 turns to mins to secs and 1k turns secs to millis
        nameTextView.setText(taskName);
        final TextView timerText = findViewById(R.id.timer);
        TextView positiveMessageText = findViewById(R.id.positive_message);
        positiveMessageText.setVisibility(View.INVISIBLE);
        /*begin all stuff for audio focus
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();
        AudioFocusRequest focusRequest = new AudioFocusRequest().Builder(AudioManager.AUDIOFOCUS_GAIN)
                .setAudioAttributes(audioAttributes)
                .setAcceptsDelayedFocusGain(true)
                .setOnAudioFocusChangeListener(afChangeListener, handler)
                .build();
        final Object focusLock = new Object(); // this was on android but it confuses me
        boolean playbackDelayed = false;
        boolean playbackNowAuthorized = false;
        */
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
                endVibrate();
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
                setResult(RESULT_CANCELED, dismissIntent);
                endVibrate();
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
                //timerText.setText(String.valueOf(counter)); old straightforward seconds disp
                timerText.setText(new SimpleDateFormat("mm:ss").format(new Date( millisUntilFinished)));
               --counter;
            }
            @Override
            public void onFinish(){
                timerText.setText(new SimpleDateFormat("mm:ss").format(new Date( 0)));
                //get the audio focus here I think I need a builder for that... requestAudioFocus()
                player.start();
                vibratePhone();
                String finish = "Pomodoro complete! \n Current Task Count: " + pomodoroCount;
                positiveMessageText.setText(finish);
                abandonButton.setVisibility(View.INVISIBLE);
                addPomoButton.setVisibility(View.VISIBLE);
                dismissPomoButton.setVisibility(View.VISIBLE);
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

    private void vibratePhone(){
        /* this is apparently old shit I'm using the one below for newer than sdk 26
        Vibrator v = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }
         */ //I wonder which piece of code is getting used for me?
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
