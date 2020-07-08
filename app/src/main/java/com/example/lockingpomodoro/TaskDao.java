package com.example.lockingpomodoro;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Task task);

    @Query("DELETE FROM task_table")
    void deleteAll();

    @Query("DELETE FROM task_table WHERE name= :name")
    void deleteEntry(String name);

    //let's get our live data currently by asc name of task
    @Query("SELECT * from task_table ORDER BY name ASC")
    LiveData<List<Task>> getTasks();

    @Query("UPDATE task_table SET tally = :tally WHERE name = :name")
    void setTally(String name, int tally);

    @Query("UPDATE task_table SET tally = 0")
    void resetCounts();

}
