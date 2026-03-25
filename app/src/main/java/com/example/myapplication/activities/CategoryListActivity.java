package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.dal.AppDatabase;
import com.example.myapplication.entities.Category;

import java.util.List;

public class CategoryListActivity extends AppCompatActivity {

    private RecyclerView rvCategories;
    private Button btnViewCartCat;
    private AppDatabase db;
    private CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        rvCategories = findViewById(R.id.rvCategories);
        btnViewCartCat = findViewById(R.id.btnViewCartCat);
        rvCategories.setLayoutManager(new LinearLayoutManager(this));

        db = AppDatabase.getInstance(this);

        List<Category> categories = db.categoryDao().getAll();

        adapter = new CategoryAdapter(categories, category -> {
            Intent intent = new Intent(CategoryListActivity.this, ProductListActivity.class);
            intent.putExtra("categoryId", category.id);
            startActivity(intent);
        });
        rvCategories.setAdapter(adapter);

        btnViewCartCat.setOnClickListener(v -> {
            Intent intent = new Intent(this, CheckoutActivity.class);
            startActivity(intent);
        });
    }
}
