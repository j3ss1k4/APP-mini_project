package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.activities.CategoryListActivity;
import com.example.myapplication.activities.LoginActivity;
import com.example.myapplication.activities.ProductListActivity;
import com.example.myapplication.dal.DbInitializer;

public class MainActivity extends AppCompatActivity {

    private Button btnViewProducts, btnViewCategories, btnLogin, btnLogout;
    private TextView tvWelcome;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize data if needed
        DbInitializer.initialize(this);

        tvWelcome = findViewById(R.id.tvWelcome);
        btnViewProducts = findViewById(R.id.btnViewProducts);
        btnViewCategories = findViewById(R.id.btnViewCategories);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogout = findViewById(R.id.btnLogout);

        sp = getSharedPreferences("LOGIN", MODE_PRIVATE);

        updateUI();

        btnViewProducts.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProductListActivity.class);
            startActivity(intent);
        });

        btnViewCategories.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CategoryListActivity.class);
            startActivity(intent);
        });

        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sp.edit();
            editor.clear();
            editor.apply();
            updateUI();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        boolean isLogin = sp.getBoolean("isLogin", false);
        String fullName = sp.getString("fullName", "");

        if (isLogin) {
            tvWelcome.setText("Welcome, " + fullName);
            btnLogin.setVisibility(View.GONE);
            btnLogout.setVisibility(View.VISIBLE);
        } else {
            tvWelcome.setText("Welcome to Shopping App");
            btnLogin.setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.GONE);
        }
    }
}
