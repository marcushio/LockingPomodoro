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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

public class MainActivity extends AppCompatActivity implements SelectedTaskFragment.DeleteDialogListener {
    private ScheduleModel scheduleModel;
    private RecyclerView.LayoutManager layoutManager;
    private TaskListAdaptor adaptor;
    //public final TaskListAdaptor adaptor = new TaskListAdaptor(this);
    TextView setCounter;
    private int setCount;
    public static final int NEW_TASK_ACTIVITY_REQUEST_CODE = 1;
    public static final int TASK_FINISH_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        //all entries are the same size
        recyclerView.setHasFixedSize(true);
        //use linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //final TaskListAdaptor adaptor = new TaskListAdaptor(this);
        adaptor = new TaskListAdaptor(this);
        recyclerView.setAdapter(adaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        scheduleModel = new ViewModelProvider(this).get(ScheduleModel.class);
        scheduleModel.getAllTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                adaptor.setTasks(tasks);
                if (allTasksComplete(tasks)){ resetTaskCounts(); }
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

        setCounter = findViewById(R.id.set_count);
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
            int taskTally = data.getIntExtra("TASK_TALLY", 0);
            Task task = new Task(taskName, taskWeight, taskInterval, taskTally);
            //in the db you go little one
            scheduleModel.insert(task);
        }

        if(requestCode == TASK_FINISH_REQUEST_CODE && resultCode == RESULT_OK ){
            //smart way to do this would be to hold a data structure with full tasks so we don't have
            //to check the whole list every time, but I don't expect a massive list of tasks so we'll do the checkall way
            int taskTally = data.getIntExtra("TASK_TALLY", 0);
            String taskName = data.getStringExtra("TASK_NAME");
            scheduleModel.updateTally(taskName, taskTally);
            adaptor.setTasks(scheduleModel.getTaskList()); //prob don't need this
            adaptor.notifyDataSetChanged();
            startActivity(new Intent(MainActivity.this, BreakActivity.class));
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

    @Override
    public void onDialogPositiveClick(DialogFragment dialog){
        String taskname = adaptor.getSelected().getName();
        scheduleModel.deleteEntry( taskname );
        adaptor.notifyDataSetChanged();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog){
        //I don't think I have to actually do anything.
    }

    private boolean allTasksComplete(List<Task> tasks){
        for(Task task : tasks){
            if(task.getTally() != task.getWeight())
                return false;
        }
        return true;
    }

    private void resetTaskCounts(){
        scheduleModel.resetTasks();
        adaptor.notifyDataSetChanged();
        Toast toast = Toast.makeText(getApplicationContext(), "You finished a set! gj broh", Toast.LENGTH_SHORT);
        //probably add a Toast and a way to tell that a set was complete here
    }

}
