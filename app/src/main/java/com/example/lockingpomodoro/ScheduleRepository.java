package com.example.lockingpomodoro;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ScheduleRepository {
    private TaskDao taskDao;
    private LiveData<List<Task>> allTasks;

    ScheduleRepository(Application application){
        TaskRoomDatabase db = TaskRoomDatabase.getDatabase(application);
        taskDao = db.taskDao();
        allTasks = taskDao.getTasks();
    }

    LiveData<List<Task>> getAllTasks(){
        return allTasks; }

    void insert(Task task){
        TaskRoomDatabase.databaseWriteExecutor.execute( ()->{taskDao.insert(task);} );
    }

    //add delete method later
}
