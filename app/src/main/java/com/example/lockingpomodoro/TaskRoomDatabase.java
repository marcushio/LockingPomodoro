package com.example.lockingpomodoro;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Task.class}, version = 1, exportSchema = false)
public abstract class TaskRoomDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();

    private static volatile TaskRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db){
        super.onOpen(db);
        // If you want to keep data through app restarts,
        // comment out the following block
        /*
            databaseWriteExecutor.execute(() -> {
            // Populate the database in the background.
            // If you want to start with more words, just add them.
            TaskDao dao = INSTANCE.taskDao();
            dao.deleteAll();

            Task task = new Task();
            dao.insert(word);
            word = new Word("World");
            dao.insert(word);
        });
           */
        }
    };

    static TaskRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (TaskRoomDatabase.class){
                if (INSTANCE == null) { //I guess we double wrap for safety b/c threads
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TaskRoomDatabase.class, "word_database").addCallback(roomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

}
