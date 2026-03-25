package com.example.myapplication.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.dal.AppDatabase;
import com.example.myapplication.entities.Order;
import com.example.myapplication.entities.OrderDetail;
import com.example.myapplication.entities.Product;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ProductDetailActivity extends AppCompatActivity {

    private TextView tvName, tvPrice, tvDescription;
    private Button btnAddToCart;
    private AppDatabase db;
    private Product product;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        tvName = findViewById(R.id.tvDetailName);
        tvPrice = findViewById(R.id.tvDetailPrice);
        tvDescription = findViewById(R.id.tvDetailDescription);
        btnAddToCart = findViewById(R.id.btnAddToCart);

        db = AppDatabase.getInstance(this);
        sp = getSharedPreferences("LOGIN", MODE_PRIVATE);

        int productId = getIntent().getIntExtra("productId", -1);
        product = db.productDao().getById(productId);

        if (product != null) {
            tvName.setText(product.name);
            tvPrice.setText("$" + product.price);
            tvDescription.setText(product.description);
        }

        btnAddToCart.setOnClickListener(v -> {
            if (!sp.getBoolean("isLogin", false)) {
                Toast.makeText(this, "Please login first", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
            } else {
                addToCart();
            }
        });
    }

    private void addToCart() {
        int userId = sp.getInt("userId", -1);
        Order order = db.orderDao().getPendingOrderByUserId(userId);

        if (order == null) {
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
            order = new Order(userId, date, "Pending");
            long orderId = db.orderDao().insert(order);
            order.id = (int) orderId;
        }

        OrderDetail detail = db.orderDetailDao().getByOrderAndProduct(order.id, product.id);
        if (detail == null) {
            db.orderDetailDao().insert(new OrderDetail(order.id, product.id, 1, product.price));
        } else {
            db.orderDetailDao().updateQuantity(detail.id, 1);
        }

        showPostAddDialog();
    }

    private void showPostAddDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Added to Cart")
                .setMessage("Do you want to continue shopping or go to checkout?")
                .setPositiveButton("Checkout", (dialog, which) -> {
                    startActivity(new Intent(ProductDetailActivity.this, CheckoutActivity.class));
                    finish();
                })
                .setNegativeButton("Continue Shopping", (dialog, which) -> finish())
                .show();
    }
}
