package com.example.myapplication.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
    private Button btnAddToCart, btnViewCartDetail;
    private AppDatabase db;
    private Product product;
    private SharedPreferences sp;

    // QUAN TRỌNG: Lắng nghe kết quả từ màn hình Login
    private final ActivityResultLauncher<Intent> loginLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    // Login thành công -> Tự động nhặt hàng luôn
                    addToCart();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        tvName = findViewById(R.id.tvDetailName);
        tvPrice = findViewById(R.id.tvDetailPrice);
        tvDescription = findViewById(R.id.tvDetailDescription);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        btnViewCartDetail = findViewById(R.id.btnViewCartDetail);

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
                // Nếu chưa đăng nhập -> Chuyển sang màn hình Login
                Toast.makeText(this, "Vui lòng đăng nhập để mua hàng", Toast.LENGTH_SHORT).show();
                loginLauncher.launch(new Intent(this, LoginActivity.class));
            } else {
                // Đã đăng nhập -> Nhặt hàng luôn
                addToCart();
            }
        });

        btnViewCartDetail.setOnClickListener(v -> {
            Intent intent = new Intent(this, CheckoutActivity.class);
            startActivity(intent);
        });
    }

    private void addToCart() {
        int userId = sp.getInt("userId", -1);
        
        // 1. Tạo Order nếu chưa có (Trạng thái Pending)
        Order order = db.orderDao().getPendingOrderByUserId(userId);
        if (order == null) {
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());
            order = new Order(userId, date, "Pending");
            long orderId = db.orderDao().insert(order);
            order.id = (int) orderId;
        }

        // 2. Tạo OrderDetails (Thêm sản phẩm)
        OrderDetail detail = db.orderDetailDao().getByOrderAndProduct(order.id, product.id);
        if (detail == null) {
            db.orderDetailDao().insert(new OrderDetail(order.id, product.id, 1, product.price));
        } else {
            db.orderDetailDao().updateQuantity(detail.id, 1);
        }

        // 3. Hỏi người dùng có tiếp tục chọn hàng hay không?
        showChoiceDialog();
    }

    private void showChoiceDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Đã thêm vào giỏ hàng")
                .setMessage("Bạn có muốn tiếp tục chọn sản phẩm khác không?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // Quay lại danh sách Product (Kết thúc màn hình chi tiết)
                    finish(); 
                })
                .setNegativeButton("No", (dialog, which) -> {
                    // Chuyển sang màn hình Thanh toán (Checkout)
                    Intent intent = new Intent(this, CheckoutActivity.class);
                    startActivity(intent);
                    finish();
                })
                .setCancelable(false)
                .show();
    }
}
