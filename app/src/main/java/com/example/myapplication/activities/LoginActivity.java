package com.example.myapplication.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.dal.AppDatabase;
import com.example.myapplication.entities.User;

public class LoginActivity extends AppCompatActivity {
    EditText edtUser, edtPass;
    Button btnLogin;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUser = findViewById(R.id.edtUser);
        edtPass = findViewById(R.id.edtPass);
        btnLogin = findViewById(R.id.btnLogin);

        db = AppDatabase.getInstance(this);

        btnLogin.setOnClickListener(v -> {
            String u = edtUser.getText().toString();
            String p = edtPass.getText().toString();

            User user = db.userDao().login(u, p);

            if (user != null) {
                SharedPreferences sp = getSharedPreferences("LOGIN", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("userId", user.id);
                editor.putString("username", user.username);
                editor.putString("fullName", user.fullName);
                editor.putBoolean("isLogin", true);
                editor.apply();

                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                finish(); // Go back to previous screen
            } else {
                Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
