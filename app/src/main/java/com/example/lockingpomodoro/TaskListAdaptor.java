package com.example.lockingpomodoro;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskListAdaptor extends RecyclerView.Adapter<TaskListAdaptor.TaskViewHolder> {
    private Context context; //this is the context of the main Activity
    private final LayoutInflater inflater;
    private List<Task> taskList;
    private int selectedPosition = -1; //-1 for nothing selected
    //lets make an inner class to hold our view
    class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView taskItemView; //this used to be final might have to change back
        private ImageView imageView;
        //layout params


        public TaskViewHolder(View itemView) {
            super(itemView);
            taskItemView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView); //as of now this is empty for a value
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, FocusActivity.class); //I'm going to have to add more to this later to include all necessary info
            selectedPosition = getLayoutPosition();
            Task selectedTask = taskList.get(selectedPosition);
            String taskName = selectedTask.getName();
            int taskCount = selectedTask.getTally();
            int taskInterval = selectedTask.getInterval();
            intent.putExtra("taskName", taskName);
            intent.putExtra("taskCount", taskCount);
            intent.putExtra("taskInterval", taskInterval);
            context.startActivity(intent);
            selectedTask.incrementTally();
            notifyDataSetChanged();
        }
    }

    public TaskListAdaptor(Context context){
        inflater = LayoutInflater.from(context);
        this.context = context;
    }


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
            holder.taskItemView.setSelected(selectedPosition == position);
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
