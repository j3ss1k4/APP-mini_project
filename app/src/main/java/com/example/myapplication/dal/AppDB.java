package com.example.myapplication.dal;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.entities.Account;
import com.example.myapplication.entities.Score;
import com.example.myapplication.entities.Student;

@Database(entities = {Account.class, Student.class, Score.class}, version = 1)
public abstract class AppDB extends RoomDatabase {
    public abstract DAO dao();
    private static AppDB INSTANCE;

    public static AppDB getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDB.class,
                    "StudentDB"
            ).allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
}
