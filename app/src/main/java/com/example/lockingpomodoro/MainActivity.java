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

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView taskList;
    List<Task> tasks = new ArrayList<>();
    //private ArrayAdapter<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.addtaskButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "make a task", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                addTask(view);
            }
        });

        taskList = (ListView) findViewById(R.id.task_list_view);
        tasks.add(new Task("install bidet", 1, 45));
        tasks.add(new Task("beat it", 2, 45));
        ArrayAdapter<Task> arrayAdapter = new ArrayAdapter<Task>(this, R.layout.task_list_view, R.id.textView, tasks);
        taskList.setAdapter(arrayAdapter);
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

    public void addTask(View view){//I think that's actually really all I need
        Intent intent = new Intent(this, ScheduleActivity.class);
        startActivity(intent);
    }
}
