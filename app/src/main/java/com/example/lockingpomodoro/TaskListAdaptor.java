package com.example.lockingpomodoro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskListAdaptor extends RecyclerView.Adapter<TaskListAdaptor.TaskViewHolder> {
    public Context context; //this is the context of the main Activity
    private final LayoutInflater inflater;
    private List<Task> taskList;
    private int selectedPosition = -1; //-1 for nothing selected
    public static final int TASK_FINISH_CODE = 2;
    private DialogFragment fragment; //I don't know if I'm using a fragment here.... this might now be what I need


    //lets make an inner class to hold our view
    class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView taskItemView; //this used to be final might have to change back
        private ImageView imageView;
        //layout params


        public TaskViewHolder(View itemView) {
            super(itemView);
            taskItemView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView); //as of now this is empty for a value
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, FocusActivity.class); //I'm going to have to add more to this later to include all necessary info
            selectedPosition = getLayoutPosition();
            Task selectedTask = taskList.get(selectedPosition);
            if(selectedTask.getTally() == selectedTask.getWeight()){
                Toast toast = Toast.makeText(context, R.string.locked_task, Toast.LENGTH_SHORT);
                toast.show(); 
            } else {
                String taskName = selectedTask.getName();
                int taskCount = selectedTask.getTally();
                int taskInterval = selectedTask.getInterval();
                intent.putExtra("taskName", taskName);
                intent.putExtra("taskCount", taskCount);
                intent.putExtra("taskInterval", taskInterval);
                ((AppCompatActivity) context).startActivityForResult(intent, TASK_FINISH_CODE);
                notifyDataSetChanged();
            }
        }

        @Override
        public boolean onLongClick(View v) {
            selectedPosition = getLayoutPosition();
            fragment = new SelectedTaskFragment();
            FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
            fragment.show(fm, "delete");
            return false;
        }
    }


    public TaskListAdaptor(Context context){
        inflater = LayoutInflater.from(context);
        this.context = context;
    }


    @Override
    public  TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = inflater.inflate(R.layout.task_list_view, parent, false);
        context = parent.getContext();
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position){
        if(taskList != null){
            Task currTask = taskList.get(position);
            holder.taskItemView.setText(currTask.toString());
            holder.taskItemView.setSelected(selectedPosition == position);
            //holder.v.set
            //setup layout params so our viewholders fit right
            //holder.itemView.setLayoutParams();
        } else{
            holder.taskItemView.setText("NO TASKS");
        }
    }

    @Override
    public int getItemCount(){
        if(taskList != null){
            return taskList.size();
        }
        else return 0;
    }

    public void setTasks(List<Task> tasks){
        taskList = tasks;
        notifyDataSetChanged();
    }

    public Task getSelected(){ //I only have single selection so I don't think I needed this.
        if (selectedPosition != -1){
            return taskList.get(selectedPosition);
        }
        return null;
    }

    public void setContext(Context context){ this.context = context; }

}
