package com.example.lockingpomodoro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskListAdaptor extends RecyclerView.Adapter<TaskListAdaptor.TaskViewHolder> {
    //lets make an inner class to hold our view
    class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView taskItemView; //this used to be final might have to change back
        private ImageView imageView;

        private TaskViewHolder(View itemView) {
            super(itemView);
            taskItemView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView); //as of now this is empty for a value
        }

        void bind(final Task task){
            if(selectedPosition == -1){
                imageView.setVisibility(View.GONE);
            } else {
                if(selectedPosition == getAdapterPosition()){
                    imageView.setVisibility(View.VISIBLE);
                } else {
                    imageView.setVisibility(View.GONE);
                }
            }
            taskItemView.setText(task.getName());

            // Handle item click and set the selection
            taskItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Redraw the old selection and the new
                    taskItemView.setVisibility(View.VISIBLE);
                    if(selectedPosition != getAdapterPosition()) {
                        notifyItemChanged(selectedPosition);
                        //selectedItem = getLayoutPosition();
                        selectedPosition = getAdapterPosition();
                    }
                }
            });
        }
    }

    //rest of the members
    private final LayoutInflater inflater;
    private List<Task> taskList;
    private int selectedPosition = -1; //-1 for nothing selected

    public TaskListAdaptor(Context context){ inflater = LayoutInflater.from(context); }


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

    public void setTasks(List<Task> tasks){
        taskList = tasks;
        notifyDataSetChanged();
    }

    public Task getSelected(){
        if (selectedPosition != -1){
            return taskList.get(selectedPosition);
        }
        return null;
    }

}
