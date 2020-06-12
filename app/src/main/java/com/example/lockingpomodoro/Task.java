package com.example.lockingpomodoro;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "task_table")
public class Task {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "weight")
    private int weight;
    @ColumnInfo(name = "interval")
    private int interval;
    @ColumnInfo(name = "tally")
    private int tally;

    public Task(String name, int weight, int interval){
        this.name = name;
        this.weight = weight;
        this.interval = interval;
        tally = 0;
    }

    @Override
    public String toString(){ return (name + " " + weight + " Count: " + tally); }

    public String getName(){ return name; }
    public int getWeight(){ return weight; }
    public int getInterval(){ return interval; }
    public int getTally(){ return tally; }

    public void incrementTally(){ tally++; }
    public void setTally(int tally){ this.tally = tally; }
    public void setWeight(int newWeight){ this.weight = newWeight; }
    public void setInterval(int newInterval){ this.interval = newInterval; }


}
