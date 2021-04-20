package com.example.test_grid_cards;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Score.class, version = 1)
public abstract class ScoreDatabase extends RoomDatabase {
    private static ScoreDatabase instance;

    public abstract ScoreDao scoreDao();

    public static synchronized ScoreDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ScoreDatabase.class, "score_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }
}
