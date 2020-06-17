package com.example.lockingpomodoro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemKeyProvider;

import java.util.List;

public class MyItemKeyProvider extends ItemKeyProvider {
    private final List<Task> taskList;

    public MyItemKeyProvider(int scope, List<Task> tasks){
        super(scope);
        this.taskList = tasks;
    }

    @Nullable
    @Override
    public Task getKey(int position){ //I have this returning a Task right now, but it might have to be an object
        return taskList.get(position);
    }

    @Override
    public int getPosition(@NonNull Object key){
        return taskList.indexOf(key);
    }

}
