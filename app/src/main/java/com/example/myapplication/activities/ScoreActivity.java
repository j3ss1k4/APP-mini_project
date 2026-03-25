package com.example.myapplication.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.dal.AppDB;
import com.example.myapplication.entities.ScoreView;

import java.util.List;

public class ScoreActivity extends AppCompatActivity {
    RecyclerView rvScores;
    AppDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        rvScores = findViewById(R.id.rvScores);
        db = AppDB.getInstance(this);

        SharedPreferences sp = getSharedPreferences("LOGIN", MODE_PRIVATE);
        String username = sp.getString("username", "Unknown");

        List<ScoreView> list = db.dao().getScores();
        ScoreAdapter adapter = new ScoreAdapter(list);
        rvScores.setLayoutManager(new LinearLayoutManager(this));
        rvScores.setAdapter(adapter);
    }
}
