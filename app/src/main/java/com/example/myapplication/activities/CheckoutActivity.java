package com.example.myapplication.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.dal.AppDatabase;
import com.example.myapplication.entities.Order;
import com.example.myapplication.entities.OrderDetail;

import java.util.List;

public class CheckoutActivity extends AppCompatActivity {

    private RecyclerView rvCartItems;
    private TextView tvTotal;
    private Button btnPay;
    private AppDatabase db;
    private SharedPreferences sp;
    private Order currentOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        rvCartItems = findViewById(R.id.rvCartItems);
        tvTotal = findViewById(R.id.tvTotal);
        btnPay = findViewById(R.id.btnPay);

        db = AppDatabase.getInstance(this);
        sp = getSharedPreferences("LOGIN", MODE_PRIVATE);

        loadCart();

        btnPay.setOnClickListener(v -> {
            if (currentOrder != null) {
                currentOrder.status = "Paid";
                db.orderDao().update(currentOrder);
                showReceipt();
            }
        });
    }

    private void loadCart() {
        int userId = sp.getInt("userId", -1);
        currentOrder = db.orderDao().getPendingOrderByUserId(userId);

        if (currentOrder != null) {
            List<OrderDetail> details = db.orderDetailDao().getByOrderId(currentOrder.id);
            CheckoutAdapter adapter = new CheckoutAdapter(details, db);
            rvCartItems.setLayoutManager(new LinearLayoutManager(this));
            rvCartItems.setAdapter(adapter);

            double total = 0;
            for (OrderDetail d : details) {
                total += d.unitPrice * d.quantity;
            }
            tvTotal.setText("Total: $" + total);
        } else {
            tvTotal.setText("Total: $0");
            btnPay.setEnabled(false);
            Toast.makeText(this, "Your cart is empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void showReceipt() {
        StringBuilder receipt = new StringBuilder();
        receipt.append("Order ID: ").append(currentOrder.id).append("\n");
        receipt.append("Date: ").append(currentOrder.orderDate).append("\n\n");
        
        List<OrderDetail> details = db.orderDetailDao().getByOrderId(currentOrder.id);
        double total = 0;
        for (OrderDetail d : details) {
            String prodName = db.productDao().getById(d.productId).name;
            receipt.append(prodName).append(" x").append(d.quantity)
                    .append(" : $").append(d.unitPrice * d.quantity).append("\n");
            total += d.unitPrice * d.quantity;
        }
        receipt.append("\nTotal: $").append(total);

        new AlertDialog.Builder(this)
                .setTitle("Invoice / Receipt")
                .setMessage(receipt.toString())
                .setPositiveButton("OK", (dialog, which) -> finish())
                .setCancelable(false)
                .show();
    }
}
