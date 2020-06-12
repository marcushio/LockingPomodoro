package com.example.lockingpomodoro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskListAdaptor extends RecyclerView.Adapter<TaskListAdaptor.TaskViewHolder> {
    //lets make an inner class to hold our view
    class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView taskItemView; //this used to be final might have to change back

        private TaskViewHolder(View itemView) {
            super(itemView);
            taskItemView = itemView.findViewById(R.id.textView);
        }
    }
    //rest of the members
    private final LayoutInflater inflater;
    private List<Task> taskList;

    TaskListAdaptor(Context context){ inflater = LayoutInflater.from(context); }

    @Override
    public  TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = inflater.inflate(R.layout.task_list_view, parent, false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position){
        if(taskList != null){
            Task currTask = taskList.get(position);
            holder.taskItemView.setText(currTask.toString());
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

    void setTasks(List<Task> tasks){
        taskList = tasks;
        notifyDataSetChanged();
    }
}
