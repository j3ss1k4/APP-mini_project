package com.example.myapplication.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Students")
public class Student {
    @PrimaryKey
    @NonNull
    public String code;
    public String name;
    public String className;
}
