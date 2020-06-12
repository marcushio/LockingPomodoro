package com.example.lockingpomodoro;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ScheduleModel scheduleModel;
    public static final int NEW_TASK_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final TaskListAdaptor adaptor = new TaskListAdaptor(this);
        recyclerView.setAdapter(adaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        scheduleModel = new ViewModelProvider(this).get(ScheduleModel.class);
        scheduleModel.getAllTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                adaptor.setTasks(tasks);
            }
        });

        scheduleModel.getAllTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable final List<Task> tasks) {
                adaptor.setTasks(tasks);
            }
        });

        FloatingActionButton fab = findViewById(R.id.addtaskButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "make a task", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                //addTask(view);
                Intent intent = new Intent(MainActivity.this, ScheduleActivity.class);
                startActivityForResult(intent, NEW_TASK_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == NEW_TASK_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            //Bundle taskInfo = data.getExtras(); this is one option, but I think i prefer the following
            //pull the pieces of info from the bundle and make a task to add to db
            String taskName = data.getStringExtra("TASK_NAME");
            int taskWeight = data.getIntExtra("TASK_WEIGHT", 0);
            int taskInterval = data.getIntExtra("TASK_INTERVAL", 0);
            Task task = new Task(taskName, taskWeight, taskInterval);
            //in the db you go little one
            scheduleModel.insert(task);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "empty task, not saved", //need to add this to strings.xml
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
