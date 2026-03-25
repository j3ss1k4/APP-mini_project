package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.dal.AppDatabase;
import com.example.myapplication.entities.Product;

import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    private RecyclerView rvProducts;
    private AppDatabase db;
    private ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        rvProducts = findViewById(R.id.rvProducts);
        rvProducts.setLayoutManager(new LinearLayoutManager(this));

        db = AppDatabase.getInstance(this);

        int categoryId = getIntent().getIntExtra("categoryId", -1);
        List<Product> products;
        if (categoryId != -1) {
            products = db.productDao().getByCategoryId(categoryId);
        } else {
            products = db.productDao().getAll();
        }

        adapter = new ProductAdapter(products, product -> {
            Intent intent = new Intent(ProductListActivity.this, ProductDetailActivity.class);
            intent.putExtra("productId", product.id);
            startActivity(intent);
        });
        rvProducts.setAdapter(adapter);
    }
}
