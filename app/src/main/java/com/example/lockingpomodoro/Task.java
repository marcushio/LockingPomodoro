package com.example.lockingpomodoro;

import android.view.View;
@Entity
public class Task {
    @PrimaryKey
    public String name;

    private int weight;
    private int interval;
    private int tally;

    public Task(String name, int weight, int interval){
        this.name = name;
        this.weight = weight;
        this.interval = interval;
        tally = 0;
    }

    public Task(View view){//is a view what I need to get the information about making a task?

    }

    @Override
    public String toString(){
        return (name + "\n" + weight);
    }

    public String getName(){ return name; }
    public int getWeight(){ return weight; }
    public int getInterval(){ return interval; }
    public int getTally(){ return tally; }


}
