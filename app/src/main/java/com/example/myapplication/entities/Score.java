package com.example.myapplication.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Scores")
public class Score {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String codeStudent;
    public String subject;
    public double score;
}
